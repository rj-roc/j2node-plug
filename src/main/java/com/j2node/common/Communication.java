package com.j2node.common;

/**
 * @author 61337
 * 通信接口
 */
public interface Communication {
    /**
     * 发送数据
     *
     * @param msg
     * @return
     */
    Object send(Object msg);

    /**
     * 回调消息接口
     *
     * @param msg
     * @return
     */
    Object call(Object msg);

    /**
     * 发送数据
     *
     * @param msg
     * @return
     */
    void asyncSend(Object msg);

    /**
     * 回调消息接口
     *
     * @param msg
     * @return
     */
    void asyncCall(Object msg);
}
