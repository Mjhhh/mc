package com.edu.message.service;

import com.alibaba.fastjson.JSONObject;
import com.edu.framework.domain.message.McMessage;
import com.edu.framework.domain.message.McUserMessage;
import com.edu.framework.domain.ucenter.McCompanyUser;
import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.response.UcenterCode;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.message.client.UserClient;
import com.edu.message.dao.McMessageMapper;
import com.edu.message.dao.McMessageRepository;
import com.edu.message.dao.McUserMessageMapper;
import com.edu.message.dao.McUserMessageRepository;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService{

    @Autowired
    McMessageRepository mcMessageRepository;
    @Autowired
    McUserMessageRepository mcUserMessageRepository;
    @Autowired
    McUserMessageMapper mcUserMessageMapper;
    @Autowired
    McMessageMapper mcMessageMapper;
    @Autowired
    UserClient userClient;

    private McMessage getMessage(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        Optional<McMessage> optional = mcMessageRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 发布公告
     * @param mcMessage
     * @return
     */
    @Transactional
    public ResponseResult addMsg(McMessage mcMessage) {
        if (mcMessage == null) {
            ExceptionCast.cast(CommonCode.OBJECT_IS_NOT_EXISTS);
        }
        mcMessage.setCreateTime(new Date());
        mcMessage.setUpdateTime(new Date());
        mcMessageRepository.save(mcMessage);
        return ResponseResult.SUCCESS();
    }

    /**
     * 编辑公告
     * @param mcMessage
     * @return
     */
    @Transactional
    public ResponseResult editMsg(McMessage mcMessage) {
        McMessage updateMessage = this.getMessage(mcMessage.getId());
        if (updateMessage == null) {
            ExceptionCast.cast(CommonCode.OBJECT_IS_NOT_EXISTS);
        }
        if (StringUtils.isNotBlank(mcMessage.getContent())) {
            updateMessage.setContent(mcMessage.getContent());
        }
        if (StringUtils.isNotBlank(mcMessage.getReceiver())) {
            updateMessage.setReceiver(mcMessage.getReceiver());
        }
        if (StringUtils.isNotBlank(mcMessage.getType())) {
            updateMessage.setType(mcMessage.getType());
        }
        updateMessage.setUpdateTime(new Date());
        mcMessageRepository.save(updateMessage);
        return ResponseResult.SUCCESS();
    }

    /**
     * 删除公告
     * @param id
     * @return
     */
    @Transactional
    public ResponseResult deleteMsg(String id) {
        McMessage message = this.getMessage(id);
        if (message == null) {
            ExceptionCast.cast(CommonCode.OBJECT_IS_NOT_EXISTS);
        }
        List<McUserMessage> mcUserMessages = mcUserMessageRepository.findByMessageId(id);
        if (!CollectionUtils.isEmpty(mcUserMessages)) {
            mcUserMessageRepository.deleteAll(mcUserMessages);
        }
        mcMessageRepository.delete(message);
        return ResponseResult.SUCCESS();
    }

    @Transactional
    public ResponseResult synUser(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        //获取消息池
        List<McMessage> messageList = mcMessageRepository.findAll();
        //获取当前用户的消息池消息主键
        List<String> userMessageIds = mcUserMessageMapper.selectMessageIdByUserId(userId);
        List<McUserMessage> insertMessageLisst = new ArrayList<>();
        for (McMessage mcMessage : messageList) {
            if (!userMessageIds.contains(mcMessage.getId())) {
                if (StringUtils.equals(mcMessage.getReceiver(),"0") ||  StringUtils.equals(mcMessage.getReceiver(),userId)) {
                    McUserMessage mcUserMessage = new McUserMessage();
                    mcUserMessage.setCreateTime(new Date());
                    mcUserMessage.setUpdateTime(new Date());
                    mcUserMessage.setMessageId(mcMessage.getId());
                    //消息未读
                    mcUserMessage.setReadStatus("0");
                    mcUserMessage.setUserId(userId);
                    insertMessageLisst.add(mcUserMessage);
                }
            }
        }
        if (!CollectionUtils.isEmpty(insertMessageLisst)) {
            mcUserMessageRepository.saveAll(insertMessageLisst);
        }
        return ResponseResult.SUCCESS();
    }


    /**
     * 用户获取公告
     * @param page
     * @param size
     * @param userId
     * @return
     */
    public CommonResponseResult getUserMsg(int page, int size, String userId) {
        if (StringUtils.isBlank(userId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        List<String> messageIds = mcUserMessageMapper.selectMessageIdByUserId(userId);
        Page<Object> startPage = PageHelper.startPage(page, size);
        List<McMessage> messageList = mcMessageMapper.selectListByIds(messageIds);

        JSONObject result = new JSONObject();
        result.put("total", startPage.getTotal());
        result.put("list", messageList);
        return new CommonResponseResult(CommonCode.SUCCESS, result);
    }

    /**
     * 用户阅读公告
     * @param userId
     * @return
     */
    @Transactional
    public ResponseResult userReadMsg(String userId) {
        if (StringUtils.isBlank(userId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        List<McUserMessage> mcUserMessageList = mcUserMessageRepository.findByUserIdAndReadStatus(userId, "0");
        if (CollectionUtils.isEmpty(mcUserMessageList)) {
            return ResponseResult.SUCCESS();
        }
        for (McUserMessage mcUserMessage : mcUserMessageList) {
            //更新为已读
            mcUserMessage.setReadStatus("1");
        }
        mcUserMessageRepository.saveAll(mcUserMessageList);
        return ResponseResult.SUCCESS();
    }

    /**
     * 用户删除公告
     * @param userId
     * @param messageId
     * @return
     */
    @Transactional
    public ResponseResult userDeleteMsg(String userId, String messageId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(messageId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        McUserMessage mcUserMessage = mcUserMessageRepository.findByUserIdAndMessageId(userId, messageId);
        if (mcUserMessage == null) {
            ExceptionCast.cast(CommonCode.OBJECT_IS_NOT_EXISTS);
        }
        mcUserMessageRepository.delete(mcUserMessage);
        return ResponseResult.SUCCESS();
    }

    /**
     * 公告列表
     * @return
     */
    public CommonResponseResult msgList() {
        List<McMessage> all = mcMessageRepository.findAll();
        return new CommonResponseResult(CommonCode.SUCCESS, all);
    }

    /**
     * 删除列表
     * @param ids
     * @return
     */
    @Transactional
    public ResponseResult deleteMsgList(List<String> ids) {
        List<McMessage> mcMessageList = mcMessageRepository.findAllById(ids);
        if (!CollectionUtils.isEmpty(mcMessageList)) {
            mcMessageRepository.deleteAll(mcMessageList);
        }
        List<McUserMessage> mcUserMessageList = mcUserMessageMapper.selectListByMessageIds(ids);
        if (!CollectionUtils.isEmpty(mcUserMessageList)) {
            mcUserMessageRepository.deleteAll(mcUserMessageList);
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 用户获取未读消息
     * @param userId
     * @return
     */
    public CommonResponseResult getUnReadMsg(String userId) {
        List<McUserMessage> mcUserMessageList = mcUserMessageRepository.findByUserIdAndReadStatus(userId, "0");
        if (CollectionUtils.isEmpty(mcUserMessageList)) {
            return new CommonResponseResult(CommonCode.SUCCESS, false);
        }
        return new CommonResponseResult(CommonCode.SUCCESS, true);
    }

    /**
     * 处理邀请消息
     * @param messageId
     * @param receiver
     * @param isAccept
     * @return
     */
    @Transactional
    public ResponseResult dealInviteMsg(String messageId, String receiver, String isAccept) {
        if (StringUtils.isBlank(messageId) || StringUtils.isBlank(receiver) || StringUtils.isBlank(isAccept)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        McMessage message = this.getMessage(messageId);
        if (message == null || StringUtils.equals(message.getType(), "0")) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        McCompanyUser companyUser = userClient.getCompanyUser(receiver);
        if (companyUser == null) {
            ExceptionCast.cast(CommonCode.OBJECT_IS_NOT_EXISTS);
        }
        //拒绝
        if (StringUtils.equals(isAccept, "0")) {
            mcUserMessageRepository.deleteByMessageId(messageId);
            mcMessageRepository.deleteById(messageId);
            userClient.delCompanyUserByUserId(receiver);
        }
        //接受
        else {
            mcUserMessageRepository.deleteByMessageId(messageId);
            mcMessageRepository.deleteById(messageId);
            userClient.accpetInvite(receiver);
        }
        return ResponseResult.SUCCESS();
    }
}
