package com.edu.api.message;

import com.edu.framework.domain.message.McMessage;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(value = "消息接口管理", tags = "消息接口管理")
public interface MessageControllerApi {

    @ApiOperation("公告列表")
    CommonResponseResult msgList();

    @ApiOperation("发布公告")
    ResponseResult addMsg(McMessage mcMessage);

    @ApiOperation("修改公告")
    ResponseResult editMsg(McMessage mcMessage);

    @ApiOperation("删除公告")
    ResponseResult deleteMsg(String id);

    @ApiOperation("删除公告列表")
    ResponseResult deleteMsgList(List<String> ids);

    @ApiOperation("同步公告到用户消息池")
    ResponseResult synUser(String userId);

    @ApiOperation("用户获取公告")
    CommonResponseResult getUserMsg(String userId);

    @ApiOperation("用户阅读公告")
    ResponseResult userReadMsg(String userId, String messageId);

    @ApiOperation("用户删除自身的公告")
    ResponseResult userDeleteMsg(String userId, String messageId);
}
