package com.edu.message.actionFactory.impl;


import com.edu.framework.domain.message.DataContent;
import com.edu.message.actionFactory.Action;
import com.edu.message.netty.ChatHandler;
import io.netty.channel.Channel;

public class PushMessageAction implements Action {

    /**
     * 2.6 系统推送消息
     * 3.2 消息转发给所有在线的用户
     */
    @Override
    public void doAction(Channel currentChannel, DataContent dataContent) {
        ChatHandler.pushSysMessage(dataContent);
    }
}
