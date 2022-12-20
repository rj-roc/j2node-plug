package com.j2node.socket;

import com.alibaba.fastjson.JSON;
import com.j2node.exception.NodeException;
import com.j2node.socket.accept.SocketAccept;
import com.j2node.socket.manage.SocketManage;
import com.j2node.pojo.bo.NodeScript;
import com.j2node.pojo.bo.SocketMsg;
import com.j2node.socket.config.SocketConfig;
import com.j2node.socket.accept.SocketAcceptNode;
import com.j2node.socket.msg.MsgManage;
import com.j2node.socket.msg.MsgManageNode;
import com.j2nodejs.NodeJS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;

/**
 * @author 61337
 */
@Slf4j
@Component
public class SocketNodeService {


    private ServerSocket serverSocket;

    private SocketManage socketManage;

    private SocketConfig socketConfig;

    private final String localhost = "localhost";

    private NodeJS nodeJS;

    @PostConstruct
    public void start() {
        try {
            this.init();
        } catch (Exception e) {
            log.error("socket初始化失败", e);
        }
    }


    public void init() throws Exception {
        serverSocket = new ServerSocket(1428);
        socketConfig = new SocketNodeConfig();
        socketManage = new SocketManage(socketConfig, SocketAcceptNode.class, MsgManageNode.class);
        log.info("Socket启动服务器....");
        ExecutorConfig.getTaskExecutor().execute(() -> socketManage.initListener(serverSocket));
    }


    public void close() throws IOException {
        serverSocket.close();
        log.info("Socket关闭服务器....");
        socketManage.setClose(true);
    }

    public void loadLocalNode(String js) {
        if (Objects.isNull(nodeJS)) {
            nodeJS = new NodeJS();
        }
        //加载js
        ExecutorConfig.getTaskExecutor().execute(() -> nodeJS.nodeStart(js));
    }

    public void restartLocalNode(String js) {
        String msgKey = socketConfig.getMsgKey();
        MsgManage msgManage = socketConfig.getMsgManageMap().get(msgKey + localhost);

        //发送关闭
        msgManage.sendMsg("关闭服务", SocketMsg.CLOSE);

        //关闭连接
        String acceptKey = socketConfig.getAcceptKey();
        SocketAccept socketAccept = socketConfig.getSocketAcceptMap().get(acceptKey + localhost);
        socketAccept.closeSocket();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new NodeException(e.getMessage());
        }
        //重启服务
        this.loadLocalNode(js);
    }


    public String send(NodeScript nodeScript) {
        String msgKey = socketConfig.getMsgKey();
        String redirect = nodeScript.getRedirect();

        if (ObjectUtils.isEmpty(redirect)) {
            redirect = localhost;
        }
        msgKey = msgKey + redirect;
        MsgManage msgManage = this.socketConfig.getMsgManageMap().get(msgKey);
        if (Objects.isNull(msgManage)) {
            throw new RuntimeException("未找到连接器");
        }
        return msgManage.sendMsg(JSON.toJSONString(nodeScript), SocketMsg.SCRIPT);
    }

}
