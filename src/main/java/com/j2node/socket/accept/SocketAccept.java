package com.j2node.socket.accept;

import com.j2node.socket.ExecutorConfig;
import com.j2node.socket.config.SocketConfig;
import com.j2node.socket.msg.MsgManage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;

/**
 * @author 61337
 */
@Slf4j
@Data
public abstract class SocketAccept {

    /**
     * 消息管理器
     */
    private MsgManage msgMange;

    /**
     * socket
     */
    private Socket socket;

    private SocketConfig config;

    private InputStream inputStream = null;
    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;

    private String key;

    /**
     * socket是否关闭
     */
    private boolean close = false;

    public SocketAccept(Socket socket, MsgManage msgMange, SocketConfig config) {
        this.socket = socket;
        this.msgMange = msgMange;
        this.config = config;
    }

    public void initListener() {
        ExecutorConfig.getTaskExecutor().execute(this::listenerMsg);
    }


    public void listenerMsg() {
        try {
            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            log.error("读取socket失败");
            closeSocket();
            return;
        }

        while (!isClose()) {
            try {
                //读取客户端发送来的消息
                String mess = bufferedReader.readLine();
                //如果接收为null 验证是否断开链接
                if(Objects.isNull(mess)){
                    this.verifyIsClose();
                }
                ExecutorConfig.getTaskExecutor().execute(() -> msgMange.listenerMsg(mess));
            } catch (IOException e) {
                this.verifyIsClose();
                log.error("接收信息失败");
            }

        }

    }


    public void closeSocket() {
        if (this.socket != null) {
            try {
                this.socket.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        if (this.inputStreamReader != null) {
            try {
                this.inputStreamReader.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        if (this.bufferedReader != null) {
            try {
                this.bufferedReader.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        this.close = true;
    }

    public void verifyIsClose() {
        try {
            socket.sendUrgentData(0);
            setClose(false);
        } catch (IOException e) {
            log.info("客户端已断开.....");
            config.getSocketAcceptMap().remove(this.getKey());
            config.getMsgManageMap().remove(msgMange.getKey());
            closeSocket();
        }
    }


}
