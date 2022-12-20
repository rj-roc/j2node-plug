package com.j2node.socket.msg;

import com.alibaba.fastjson.JSONObject;
import com.j2node.exception.NodeException;
import com.j2node.pojo.bo.MsgChannel;
import com.j2node.pojo.bo.SocketMsg;
import com.j2node.socket.ChannelType;
import com.j2node.socket.accept.SocketAccept;
import com.j2node.socket.config.SocketConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author 61337
 */
@Slf4j
@Data
public abstract class MsgManage {


    private Socket socket;

    private SocketConfig config;

    private SocketAccept socketAccept;

    private Map<String, MsgChannel> msgMap = new ConcurrentHashMap<>();

    public MsgManage(Socket socket, SocketConfig config) {
        this.socket = socket;
        this.config = config;
    }

    private String key;

    /**
     * 监听消息
     *
     * @param msg 接收消息
     */
    public void listenerMsg(String msg) {
        log.info("接收消息：{}", msg);
        SocketMsg socketMsg = JSONObject.parseObject(msg, SocketMsg.class);
        if (SocketMsg.INIT.equals(socketMsg.getType())) {
            initSocketMap(socketMsg);
            return;
        }
        callMsg(socketMsg);
    }

    public void initSocketMap(SocketMsg socketMsg) {

        String key = socketMsg.getData();
        String msgKey = config.getMsgKey();
        String acceptKey = config.getAcceptKey();
        msgKey += key;
        acceptKey += key;
        //存储对象
        config.getMsgManageMap().put(msgKey, this);
        this.setKey(msgKey);
        socketAccept.setKey(acceptKey);
        config.getSocketAcceptMap().put(acceptKey, socketAccept);
        log.info("连接初始化成功:" + msgKey);
    }

    /**
     * 客户端回消息
     *
     * @param socketMsg 客户端内容
     */
    private void callMsg(SocketMsg socketMsg) {
        MsgChannel msgChannel = msgMap.get(socketMsg.getId());
        msgChannel.setType(ChannelType.ACCEPT);
        msgChannel.setRep(socketMsg.getData());
        if ("0".equals(socketMsg.getCode())) {
            msgChannel.setRep(socketMsg.getMsg());
        }
        msgChannel.setComplete(true);
        msgChannel.getCountDownLatch().countDown();
    }


    /**
     * 发送信息
     *
     * @param msg 返回消息
     * @return 调用返回
     */
    public String sendMsg(String msg, String type) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
            SocketMsg socketMsg = new SocketMsg();
            String id = UUID.randomUUID().toString();
            socketMsg.setId(id);
            socketMsg.setType(type);
            socketMsg.setData(msg);
            String obj = JSONObject.toJSONString(socketMsg);
            log.info("发送消息：{}", obj);
            bw.write(obj);
            bw.flush();
            MsgChannel msgChannel = createMsgChannel(socketMsg);
            return getCall(msgChannel);
        } catch (IOException e) {
            log.error("发信失败", e);
            this.socketAccept.verifyIsClose();
            throw new NodeException("发送信息失败");
        }
    }


    private MsgChannel createMsgChannel(SocketMsg socketMsg) {
        MsgChannel msgChannel = new MsgChannel();
        msgChannel.setId(socketMsg.getId());
        msgChannel.setRep(socketMsg.getData());
        msgChannel.setType(ChannelType.SEND);
        CountDownLatch latch = new CountDownLatch(2);
        msgChannel.setCountDownLatch(latch);
        msgMap.put(socketMsg.getId(), msgChannel);

        latch.countDown();
        return msgChannel;
    }


    public String getCall(MsgChannel msgChannel) {
        if (Objects.isNull(msgChannel)) {
            throw new RuntimeException("消息丢失");
        }
        CountDownLatch countDownLatch = msgChannel.getCountDownLatch();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("线程等待异常");
        }
        String rep = msgChannel.getRep();
        msgMap.remove(msgChannel.getId());
        return rep;
    }

}
