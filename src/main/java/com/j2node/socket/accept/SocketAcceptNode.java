package com.j2node.socket.accept;

import com.j2node.socket.config.SocketConfig;
import com.j2node.socket.msg.MsgManage;

import java.net.Socket;


public class SocketAcceptNode extends SocketAccept {


    public SocketAcceptNode(Socket socket, MsgManage msgManage, SocketConfig config) {
        super(socket, msgManage, config);
    }

}
