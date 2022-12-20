package com.j2node.socket.manage;

import com.j2node.socket.ExecutorConfig;
import com.j2node.socket.config.SocketConfig;
import com.j2node.socket.accept.SocketAccept;
import com.j2node.socket.msg.MsgManage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.*;

/**
 * @author 61337
 */
@Slf4j
@Data
public class SocketManage {

    private ServerSocket serverSocket;

    private boolean close = false;

    private Class<? extends SocketAccept> acceptClass;

    private Class<? extends MsgManage> msgManageClass;

    private SocketConfig socketConfig;

    public SocketManage(SocketConfig socketConfig, Class<? extends SocketAccept> acceptClass, Class<? extends MsgManage> msgManageClass) {
        this.socketConfig = socketConfig;
        this.acceptClass = acceptClass;
        this.msgManageClass = msgManageClass;
    }

    public void initListener(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        //循环监听
        while (true) {
            try {
                Socket accept = serverSocket.accept();

                createSocketAccept(accept);
            } catch (Exception e) {
                log.error("连接失败", e);
                if (isClose()) {
                    break;
                }
            }
        }
    }

    private void createSocketAccept(Socket accept) throws Exception {
        InetSocketAddress socketAddress = (InetSocketAddress) accept.getRemoteSocketAddress();
        log.info("客户端已连接 {}:{}", socketAddress.getHostName(), socketAddress.getPort());
        accept.setKeepAlive(true);
        MsgManage msgManage = this.msgManageClass.getConstructor(Socket.class, SocketConfig.class).newInstance(accept, socketConfig);
        SocketAccept socketAccept = this.acceptClass.getConstructor(Socket.class, MsgManage.class, SocketConfig.class).newInstance(accept, msgManage, socketConfig);
        msgManage.setSocketAccept(socketAccept);
        ExecutorConfig.getTaskExecutor().execute(socketAccept::initListener);
    }


}
