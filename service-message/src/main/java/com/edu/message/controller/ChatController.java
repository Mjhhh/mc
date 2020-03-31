package com.edu.message.controller;

import com.edu.api.message.ChatControllerApi;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.message.service.ChatMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController implements ChatControllerApi {

    @Autowired
    ChatMsgService chatMsgService;

    @Override
    @GetMapping("/getUnReadMsgList/{acceptUserId}")
    public CommonResponseResult getUnReadMsgList(@PathVariable String acceptUserId){
        return chatMsgService.getUnReadMsgList(acceptUserId);
    }

    @Override
    @GetMapping("/getChatListByName")
    public CommonResponseResult getChatListByName(@RequestParam String userId, @RequestParam String findName){
        return this.chatMsgService.findChatListByName(userId, findName);
    }

    @Override
    @GetMapping("/getChatList")
    public CommonResponseResult getChatList(@RequestParam String userId){
        return this.chatMsgService.findChatList(userId);
    }
}
