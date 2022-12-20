package com.j2node.common.socket;

import com.j2node.common.CommunicationAbstract;

import java.net.ServerSocket;

/**
 * @author 61337
 * 通信接口
 */
public class CommunicationSocket extends CommunicationAbstract {

    private ServerSocket serverSocket;

    private SocketManage socketManage;


    @Override
    public Object send(Object msg) {

        return null;
    }

    @Override
    public Object call(Object msg) {
        return null;
    }

    @Override
    public void asyncSend(Object msg) {

    }

    @Override
    public void asyncCall(Object msg) {

    }
}
