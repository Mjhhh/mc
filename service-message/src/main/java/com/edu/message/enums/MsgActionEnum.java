package com.edu.message.enums;

/**
 * 
 * @author Administrator
 * @Description: 发送消息的动作 枚举
 */
public enum MsgActionEnum {
	//第一次(或重连)初始化连接
	CONNECT(1, "第一次(或重连)初始化连接"),
	//聊天消息
	CHAT(2, "聊天消息"),
	//消息签收
	SIGNED(3, "消息签收"),
	//客户端保持心跳
	KEEPALIVE(4, "客户端保持心跳"),
	//发送图片
	SEND_PIC(5, "发送图片"),
	//消息推送
	PUSH_MESSAGE(6, "消息推送"),
	//强制退出
	LOGOUT(7, "强制退出"),
	//通知后台检查代码
	CHECK_ORDER_NO(8, "通知后台检查代码"),
	//刷新缓存
	REFRESH_CACHE(9,"刷新缓存"),
	//更新area.js
	UPDATE_AREAJS(10, "更新area.js"),
	//分包发送
	SEND_PIC_PACK(11, "分包发送"),
	//消息通知
	MESSAGE_NOTIFICATION(12, "消息通知");


	public final Integer type;
	public final String content;
	
	MsgActionEnum(Integer type, String content){
		this.type = type;
		this.content = content;
	}
	
	public Integer getType() {
		return type;
	}  
}
