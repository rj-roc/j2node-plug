package com.j2node.socket.config;

import com.j2node.socket.accept.SocketAccept;
import com.j2node.socket.msg.MsgManage;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
public abstract class SocketConfig {

    /**
     * 消息管理器的存储
     */
    private final Map<String, MsgManage> msgManageMap = new ConcurrentHashMap<>();

    /**
     * 连接管理器的存储
     */
    private final Map<String, SocketAccept> socketAcceptMap = new ConcurrentHashMap<>();

    public Map<String, MsgManage> getMsgManageMap() {
        return this.msgManageMap;
    }

    public Map<String, SocketAccept> getSocketAcceptMap() {
        return socketAcceptMap;
    }

    /**
     * 连接的key
     * @return
     */
    public abstract String getAcceptKey();

    /**
     * 消息的key
     * @return
     */
    public abstract String getMsgKey();
}
