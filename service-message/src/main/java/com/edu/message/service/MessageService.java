package com.edu.message.service;

import com.edu.framework.domain.message.McMessage;
import com.edu.framework.domain.message.McUserMessage;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.message.dao.McMessageRepository;
import com.edu.message.dao.McUserMessageMapper;
import com.edu.message.dao.McUserMessageRepository;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param userId
     * @return
     */
    public CommonResponseResult getUserMsg(String userId) {
        if (StringUtils.isBlank(userId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        List<McUserMessage> mcUserMessageList = mcUserMessageRepository.findByUserId(userId);
        return new CommonResponseResult(CommonCode.SUCCESS, mcUserMessageList);
    }

    /**
     * 用户阅读公告
     * @param userId
     * @param messageId
     * @return
     */
    @Transactional
    public ResponseResult userReadMsg(String userId, String messageId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(messageId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        McUserMessage mcUserMessage = mcUserMessageRepository.findByUserIdAndMessageId(userId, messageId);
        if (mcUserMessage == null) {
            ExceptionCast.cast(CommonCode.OBJECT_IS_NOT_EXISTS);
        }
        //更新为已读
        mcUserMessage.setReadStatus("1");
        mcUserMessageRepository.save(mcUserMessage);
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
}
