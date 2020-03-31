package com.edu.message.actionFactory;

import com.edu.framework.domain.message.DataContent;
import io.netty.channel.Channel;

/**
 * 聊天消息处理工厂
 */
public interface Action {

    void doAction(Channel currentChannel, DataContent dataContent);

}
