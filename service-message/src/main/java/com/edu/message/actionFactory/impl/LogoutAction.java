package com.edu.message.actionFactory.impl;


import com.edu.framework.domain.message.DataContent;
import com.edu.framework.domain.message.UserChannelRel;
import com.edu.message.actionFactory.Action;
import com.edu.message.netty.ChatHandler;
import io.netty.channel.Channel;

public class LogoutAction implements Action {

    /**
     * 2.7 强制用户登出
     */
    @Override
    public void doAction(Channel currentChannel, DataContent dataContent) {
        String receiverId = dataContent.getUserChatMsg().getAcceptUserId();
        Channel receiverChannel = UserChannelRel.get(receiverId);
        ChatHandler.sendMsgAndNotSave(receiverChannel, dataContent);
    }
}
