package com.edu.api.message;

import com.edu.framework.model.response.CommonResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "聊天接口管理", tags = "聊天接口管理")
public interface ChatControllerApi {

    @ApiOperation("拉取未读消息")
    CommonResponseResult getUnReadMsgList(String acceptUserId);

    @ApiOperation("根据名字查找聊天列表")
    CommonResponseResult getChatListByName(String userId, String findName);

    @ApiOperation("查找聊天列表")
    CommonResponseResult getChatList(String userId);
}
