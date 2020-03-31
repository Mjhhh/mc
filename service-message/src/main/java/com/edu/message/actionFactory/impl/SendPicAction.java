package com.edu.message.actionFactory.impl;


import com.edu.framework.domain.message.DataContent;
import com.edu.framework.domain.message.UserChannelRel;
import com.edu.framework.domain.message.UserChatMsg;
import com.edu.message.actionFactory.Action;
import com.edu.message.netty.ChatHandler;
import com.edu.message.service.ChatMsgService;
import com.edu.message.utils.FileUtils;
import com.edu.message.utils.SpringUtil;
import io.netty.channel.Channel;

import java.io.File;

public class SendPicAction implements Action {

    static ChatMsgService chatMsgService = (ChatMsgService) SpringUtil.getBean("chatMsgService");

    /**
     * 2.5  发送图片类型的消息，接收发送过来的base64格式图片数据
     */
    @Override
    public void doAction(Channel currentChannel, DataContent dataContent) {
        UserChatMsg userChatMsg = dataContent.getUserChatMsg();
        //图片资源
        String pictureUrl = userChatMsg.getPicture();
        String acceptUserId = userChatMsg.getAcceptUserId();
        //将base64转换为图片文件
        try {
            chatMsgService.saveMsg(userChatMsg);
            Channel receiverChannel = UserChannelRel.get(acceptUserId);
            //发送消息
            ChatHandler.sendMsgAndNotSave(receiverChannel, dataContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
