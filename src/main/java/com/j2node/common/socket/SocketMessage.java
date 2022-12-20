package com.j2node.common.socket;

import com.j2node.socket.ChannelType;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;


/**
 * @author 61337
 */
@Data
public class SocketMessage implements Serializable {

    private final static  int COUNT_SIZE = 2;

    private static final long serialVersionUID = 7906660265647131613L;

    /**
     * 消息管道标识
     */
    private String id = UUID.randomUUID().toString();

    /**
     * 请求数据
     */
    private String req;

    /**
     * 返回数据
     */
    private String rep;

    /**
     * 当前状态
     */
    private ChannelType type;

    /**
     * 是否完成
     */
    public boolean isComplete;

    /**
     * 同步线程
     */
    public CountDownLatch countDownLatch = new CountDownLatch(COUNT_SIZE);
}
