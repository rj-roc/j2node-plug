package com.j2node.common.socket;

import lombok.Data;

import java.util.Map;

@Data
public class SocketMessageManage {


    private Map<String, SocketMessage> messageMap;

    public SocketMessage getAccept(String key) {
        return messageMap.get(key);
    }

    public void setAccept(SocketMessage socket) {
        String key = socket.getId();
        messageMap.put(key, socket);
    }

    public SocketMessage createSocketMessage(String data){
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.countDownLatch.countDown();
        socketMessage.setReq(data);
        return socketMessage;
    }
}
