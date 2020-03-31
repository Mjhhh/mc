package com.edu.message.actionFactory.impl;


import com.edu.framework.domain.message.DataContent;
import com.edu.message.actionFactory.Action;
import com.edu.message.netty.ChatHandler;
import io.netty.channel.Channel;

public class ConnectAction implements Action {

    /**
     * 2.1  当websocket 第一次open的时候，初始化channel，把channel和userid关联起来
     */
    @Override
    public void doAction(Channel currentChannel, DataContent dataContent) {
        //获取用户ID
        String sendUserId = dataContent.getUserChatMsg().getSendUserId();

        //查找是否存在重复的channel
        /*
        Channel removeChannel = UserChannelRel.get(sendUserId);
        if (removeChannel != null) {
            System.out.println("找到重复的channel，客户端被移除，channelId为：" + removeChannel.id().asShortText());
            //移除重复的channel
            users.remove(removeChannel);
            removeChannel.close();
            System.out.println("当前客户端的数量：" + users.size());
        }
        */

        //将用户ID与channel绑定
        ChatHandler.bindUserChannelRel(sendUserId, currentChannel);
    }
}
