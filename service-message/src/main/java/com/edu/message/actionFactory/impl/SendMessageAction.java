package com.edu.message.actionFactory.impl;

import com.edu.framework.domain.message.DataContent;
import com.edu.framework.domain.message.UserChannelRel;
import com.edu.message.actionFactory.Action;
import com.edu.message.netty.ChatHandler;
import io.netty.channel.Channel;

public class SendMessageAction implements Action {

    /**
     * 2.2  聊天类型的消息，把聊天记录保存到数据库，同时标记消息的签收状态[未签收]
     * 2.8  后台检查订单同步
     */
    @Override
    public void doAction(Channel currentChannel, DataContent dataContent) {
        String receiverId = dataContent.getUserChatMsg().getAcceptUserId();
        Channel receiverChannel = UserChannelRel.get(receiverId);
        //发送聊天记录
        ChatHandler.sendMsg(receiverChannel, dataContent);
    }
}
