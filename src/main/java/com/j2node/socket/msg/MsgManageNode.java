package com.j2node.socket.msg;

import com.j2node.socket.config.SocketConfig;
import lombok.extern.slf4j.Slf4j;

import java.net.Socket;

@Slf4j
public class MsgManageNode extends MsgManage {


    public MsgManageNode(Socket socket, SocketConfig config) {
        super(socket, config);
    }

}
