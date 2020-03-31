package com.edu.message.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.framework.domain.message.McChatMsg;
import com.edu.framework.domain.message.UserChatMsg;
import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.message.client.UserClient;
import com.edu.message.dao.McChatMsgMapper;
import com.edu.message.dao.McChatMsgRepository;
import com.edu.message.enums.MsgSignFlagEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ChatMsgService {

    @Autowired
    McChatMsgRepository mcChatMsgRepository;
    @Autowired
    McChatMsgMapper mcChatMsgMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserClient userClient;

    @Transactional
    public String saveMsg(UserChatMsg chatMsg) {

        McChatMsg msgDb = new McChatMsg();
        BeanUtils.copyProperties(chatMsg, msgDb);

        msgDb.setCreateTime(new Date());
        msgDb.setSignFlag(MsgSignFlagEnum.unsign.type);

        McChatMsg save = mcChatMsgRepository.save(msgDb);
        return save.getId();
    }

    /**
     * 更新消息签收状态
     * @param msgIdList
     */
    @Transactional
    public void updateMsgSigned(List<String> msgIdList) {
        List<McChatMsg> mcChatMsgList = mcChatMsgRepository.findAllById(msgIdList);
        for (McChatMsg mcChatMsg : mcChatMsgList) {
            mcChatMsg.setSignFlag(MsgSignFlagEnum.signed.type);
        }
        mcChatMsgRepository.saveAll(mcChatMsgList);
    }

    /**
     * 获取未读消息
     * @param acceptUserId
     * @return
     */
    public CommonResponseResult getUnReadMsgList(String acceptUserId) {
        //获取未读消息
        List<McChatMsg> chatMsgList = mcChatMsgRepository.findByAcceptUserIdAndSignFlag(acceptUserId, MsgSignFlagEnum.unsign.type);
        List<UserChatMsg> result = new ArrayList<>();
        for (McChatMsg chatMsg : chatMsgList) {
            UserChatMsg userChatMsg = new UserChatMsg();
            BeanUtils.copyProperties(chatMsg, userChatMsg);

            result.add(userChatMsg);
        }
        return new CommonResponseResult(CommonCode.SUCCESS, result);
    }

    /**
     * 查找当前用户的聊天列表
     * @param userId
     * @return
     */
    public CommonResponseResult findChatList(String userId) {
        if (StringUtils.isBlank(userId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        List<McChatMsg> chatMsgList = mcChatMsgMapper.selectListByUserId(userId);

        Set<String> keys = new HashSet<>();
        //去除重复的聊天对象
        for (McChatMsg chatMsg : chatMsgList) {
            if (!StringUtils.equals(chatMsg.getAcceptUserId(), userId)) {
                keys.add(chatMsg.getAcceptUserId());
            } else {
                keys.add(chatMsg.getSendUserId());
            }
        }
        //装配返回的数据
        JSONArray returnData = new JSONArray();
        for (String key : keys) {
            List<UserChatMsg> userChatMsgList = new ArrayList<>();
            String listName = null;
            for (McChatMsg chatMsg : chatMsgList) {
                if (StringUtils.equals(chatMsg.getAcceptUserId(), key) || StringUtils.equals(chatMsg.getSendUserId(), key)) {
                    if (StringUtils.isBlank(listName)) {
                        if (StringUtils.equals(chatMsg.getAcceptUserId(), key)) {
                            listName = chatMsg.getAcceptUserName();
                        }
                        if (StringUtils.equals(chatMsg.getSendUserId(), key)) {
                            listName = chatMsg.getSendUserName();
                        }
                    }
                    UserChatMsg userChatMsg = new UserChatMsg();
                    BeanUtils.copyProperties(chatMsg, userChatMsg);

                    userChatMsgList.add(userChatMsg);
                }
            }

            JSONArray message = new JSONArray();
            for (UserChatMsg userChatMsg : userChatMsgList) {
                JSONObject messageBody = new JSONObject();
                messageBody.put("sendUserName", userChatMsg.getSendUserName());
                messageBody.put("acceptUserName", userChatMsg.getAcceptUserName());
                messageBody.put("msg", userChatMsg.getMsg());
                messageBody.put("picture", userChatMsg.getPicture());
                messageBody.put("createTime", userChatMsg.getCreateTime());

                message.add(messageBody);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",listName);
            jsonObject.put("userpic", this.getHeadIcon(userId));
            jsonObject.put("id",key);
            jsonObject.put("message",message);

            returnData.add(jsonObject);
        }
        return new CommonResponseResult(CommonCode.SUCCESS, returnData);
    }

    /**
     * 根据用户名查找聊天列表
     * @param userId
     * @param findName
     * @return
     */
    public CommonResponseResult findChatListByName(String userId, String findName) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(findName)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        List<McChatMsg> chatMsgList;
        chatMsgList = mcChatMsgMapper.selectListByUserIdAndUserName(userId, findName);

        List<UserChatMsg> userChatMsgList = new ArrayList<>();
        for (McChatMsg chatMsg : chatMsgList) {
            UserChatMsg userChatMsg = new UserChatMsg();
            BeanUtils.copyProperties(userChatMsg, chatMsg);

            userChatMsgList.add(userChatMsg);
        }
        return new CommonResponseResult(CommonCode.SUCCESS, userChatMsgList);
    }

    /**
     * 获取用户头像
     * @param userId
     * @return
     */
    public String getHeadIcon(String userId) {
        McUser mcUser = userClient.getById(userId);
        return mcUser.getUserpic();
    }

}
