package com.edu.message.actionFactory.impl;


import com.edu.framework.domain.message.DataContent;
import com.edu.message.actionFactory.Action;
import io.netty.channel.Channel;

public class KeepAliveAction implements Action {

    /**
     * 2.4  心跳类型的消息
     */
    @Override
    public void doAction(Channel currentChannel, DataContent dataContent) {
        System.out.println("收到来自channel为[" + currentChannel + "]的心跳包...");
    }
}
