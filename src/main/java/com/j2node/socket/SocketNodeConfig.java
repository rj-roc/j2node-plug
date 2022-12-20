package com.j2node.socket;

import com.j2node.socket.config.SocketConfig;

public class SocketNodeConfig extends SocketConfig {

    public final static String REQ_NODE_KEY = "node-key";

    /**
     * node 连接key
     */
    public final String acceptKey = "node-plus-accept";

    /**
     * node 消息key
     */
    public final String msgKey = "node-plus-msg:";

    @Override
    public String getAcceptKey() {
        return acceptKey;
    }

    @Override
    public String getMsgKey() {
        return msgKey;
    }

}
