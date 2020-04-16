package com.edu.message.controller;

import com.edu.api.message.MessageControllerApi;
import com.edu.framework.domain.message.McMessage;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/msg")
public class MessageController implements MessageControllerApi {

    @Autowired
    MessageService messageService;

    @Override
    @GetMapping("/list")
    public CommonResponseResult msgList() {
        return messageService.msgList();
    }

    @Override
    @PostMapping("/addmsg")
    public ResponseResult addMsg(@RequestBody McMessage mcMessage) {
        return messageService.addMsg(mcMessage);
    }

    @Override
    @PutMapping("/editmsg")
    public ResponseResult editMsg(@RequestBody McMessage mcMessage) {
        return messageService.editMsg(mcMessage);
    }

    @Override
    @DeleteMapping("/delMsg/{id}")
    public ResponseResult deleteMsg(@PathVariable String id) {
        return messageService.deleteMsg(id);
    }

    @Override
    @DeleteMapping("/delMsgList")
    public ResponseResult deleteMsgList(@RequestBody List<String> ids) {
        return messageService.deleteMsgList(ids);
    }

    @Override
    @PutMapping("/synMsg/{userId}")
    public ResponseResult synUser(@PathVariable String userId) {
        return messageService.synUser(userId);
    }

    @Override
    @GetMapping("/getusermsg/{page}/{size}")
    public CommonResponseResult getUserMsg(@PathVariable int page, @PathVariable int size, @RequestParam String userId) {
        return messageService.getUserMsg(page, size, userId);
    }

    @Override
    @GetMapping("/getunreadmsg")
    public CommonResponseResult getUnReadMsg(@RequestParam String userId) {
        return messageService.getUnReadMsg(userId);
    }

    @Override
    @PutMapping("/userreadmsg")
    public ResponseResult userReadMsg(@RequestParam String userId) {
        return messageService.userReadMsg(userId);
    }

    @Override
    @DeleteMapping("/delusermsg")
    public ResponseResult userDeleteMsg(@RequestParam String userId, @RequestParam String messageId) {
        return messageService.userDeleteMsg(userId, messageId);
    }

    @Override
    @PutMapping("/dealInviteMsg")
    public ResponseResult dealInviteMsg(@RequestParam String messageId,@RequestParam String receiver,@RequestParam String isAccept) {
        return messageService.dealInviteMsg(messageId, receiver, isAccept);
    }
}
