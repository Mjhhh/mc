package com.edu.message.actionFactory.impl;

import com.edu.framework.domain.message.DataContent;
import com.edu.message.actionFactory.Action;
import com.edu.message.netty.ChatHandler;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SignedAction implements Action {

    /**
     * 2.3  签收消息类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态[已签收]
     */
    @Override
    public void doAction(Channel currentChannel, DataContent dataContent) {
        // 扩展字段在signed类型的消息中，代表需要去签收的消息id，逗号间隔
        if (StringUtils.isNotBlank(dataContent.getExtand())){
            String[] msgIds = dataContent.getExtand().split(",");

            List<String> msgIdList = new ArrayList<>();
            for (String mid : msgIds) {
                if (StringUtils.isNotBlank(mid)) {
                    msgIdList.add(mid);
                }
            }
            //签收
            ChatHandler.signedMsg(msgIdList);
        }
    }
}
