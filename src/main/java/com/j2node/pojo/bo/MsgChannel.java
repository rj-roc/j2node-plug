package com.j2node.pojo.bo;

import com.j2node.socket.ChannelType;
import lombok.Data;

import java.util.concurrent.CountDownLatch;

@Data
public class MsgChannel {

    /**
     * 消息管道标识
     */
    private String id;

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
    public CountDownLatch countDownLatch;
}
