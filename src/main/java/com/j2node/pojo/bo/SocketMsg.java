package com.j2node.pojo.bo;

import lombok.Data;

import java.util.UUID;

@Data
public class SocketMsg {

    /**
     * 消息管道标识
     */
    private String id;

    /**
     * 类型 data 默认 init 初始化 close 关闭
     */
    private String type;

    /**
     * 状态值
     */
    private String code;

    /**
     * 数据
     */
    private String data;

    /**
     * 消息
     */
    private String msg;

    /**
     * 默认 script
     */
    public final static  String DEFAULT = "";

    /**
     * 数据
     */
    public final static String SCRIPT = "script";

    /**
     * 初始化
     */
    public final static  String INIT = "init";

    /**
     * 关闭
     */
    public final static  String CLOSE = "close";

    /**
     * 重启
     */
    public final static  String RESTART = "restart";

    /**
     * 重加载
     */
    public final static  String RELOAD = "reload";
}
