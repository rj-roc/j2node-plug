package com.j2node.socket.msg;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 61337
 */
public class MsgManageFactory {

    public static Map<String, MsgManage> manageMap = new HashMap<>();

    public static MsgManage getManageMap(String key) {
        if(manageMap.containsKey(key)){
            return manageMap.get(key);
        }
        return manageMap.get(key);
    }
}
