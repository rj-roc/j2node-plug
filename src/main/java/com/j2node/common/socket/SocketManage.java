package com.j2node.common.socket;

import lombok.Data;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;


@Data
public class SocketManage {

    private String prefix = "node-";

    private String defaultIp = "localhost";

    private Map<String, Socket> acceptMap;

    public Socket getAccept(String key) {
        return acceptMap.get(key);
    }

    public void setAccept(Socket socket) {
        InetAddress inetAddress = socket.getInetAddress();
        String key = prefix + inetAddress.getHostAddress();
        acceptMap.put(key, socket);
    }

}
