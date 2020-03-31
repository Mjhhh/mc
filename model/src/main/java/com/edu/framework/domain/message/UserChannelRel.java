package com.edu.framework.domain.message;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * 用户id与channel的关联关系处理
 */
public class UserChannelRel {

    private static HashMap<String, Channel> manager = new HashMap<String, Channel>();

    public static void put(String sendUserId, Channel channel) {
        manager.put(sendUserId, channel);
    }

    public static Channel get(String sendUserId) {
        return manager.get(sendUserId);
    }

    public static void output() {
        for (HashMap.Entry<String, Channel> entry : manager.entrySet()) {
            System.out.println("userId:" + entry.getKey()  + ",ChannelId:" + entry.getValue().id().asShortText());
        }
    }
}
