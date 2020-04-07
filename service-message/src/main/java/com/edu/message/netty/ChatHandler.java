package com.edu.message.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.framework.domain.message.DataContent;
import com.edu.framework.domain.message.UserChannelRel;
import com.edu.framework.domain.message.UserChatMsg;
import com.edu.message.actionFactory.Action;
import com.edu.message.actionFactory.impl.*;
import com.edu.message.dao.McChatMsgMapper;
import com.edu.message.dao.McChatMsgRepository;
import com.edu.message.enums.MsgActionEnum;
import com.edu.message.service.ChatMsgService;
import com.edu.message.utils.JsonUtils;
import com.edu.message.utils.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理消息的载体
 * TextWebSocketFrame：在netty中，它是专门为webSocket处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 用于记录和管理所有客户端的channle
     */
    static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 更新缓存
     */

    static ChatMsgService chatMsgService = (ChatMsgService) SpringUtil.getBean("chatMsgService");

    static StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringUtil.getBean("stringRedisTemplate");

    static McChatMsgRepository mcChatMsgRepository = (McChatMsgRepository) SpringUtil.getBean("mcChatMsgRepository");

    static McChatMsgMapper mcChatMsgMapper = (McChatMsgMapper) SpringUtil.getBean("mcChatMsgMapper");


    /**
     * 存储操作
     */
    private Map<Integer, Action> actions = new HashMap<>();

    public ChatHandler() {
        actions.put(MsgActionEnum.CONNECT.type, new ConnectAction());
        actions.put(MsgActionEnum.CHAT.type, new SendMessageAction());
        actions.put(MsgActionEnum.SIGNED.type, new SignedAction());
        actions.put(MsgActionEnum.KEEPALIVE.type, new KeepAliveAction());
        actions.put(MsgActionEnum.SEND_PIC.type, new SendPicAction());
        actions.put(MsgActionEnum.PUSH_MESSAGE.type, new PushMessageAction());
        actions.put(MsgActionEnum.LOGOUT.type, new LogoutAction());
        actions.put(MsgActionEnum.CHECK_ORDER_NO.type, new SendMessageAction());
        actions.put(MsgActionEnum.MESSAGE_NOTIFICATION.type, new PushMessageAction());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取客户端传输过来的消息
        String content = msg.text();
        Channel currentChannel = ctx.channel();

        // 1. 获取客户端发来的消息
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        assert dataContent != null;
        Integer action = dataContent.getAction();

        // 2. 判断消息类型，根据不同的类型来处理不同的业务
        Action workAction = actions.get(action);
        workAction.doAction(currentChannel, dataContent);

    }

    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的channle，并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        System.out.println("客户端连接，channelId为：" + channelId);
        users.add(ctx.channel());
        System.out.println("当前客户端的数量：" + users.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        System.out.println("客户端被移除，channelId为：" + channelId);
        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }

    /**
     * 发送消息并保存
     */
    public static void sendMsg(Channel receiverChannel, DataContent dataContent) {
        UserChatMsg userChatMsg = dataContent.getUserChatMsg();
        // 保存消息到数据库，并且标记为 未签收
        String msgId = chatMsgService.saveMsg(userChatMsg);
        userChatMsg.setMsgId(msgId);
        userChatMsg.setCreateTime(new Date());

        if (receiverChannel != null) {
            // 当receiverChannel不为空的时候，从ChannelGroup去查找对应的channel是否存在
            Channel findChannel = users.find(receiverChannel.id());
            if (findChannel != null) {
                // 用户在线,发送消息
                receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContent)));
            }
        }
    }

    /**
     * 发送消息但不保存
     */
    public static void sendMsgAndNotSave(Channel receiverChannel, DataContent dataContent) {
        if (receiverChannel != null) {
            UserChatMsg userChatMsg = dataContent.getUserChatMsg();
            // 当receiverChannel不为空的时候，从ChannelGroup去查找对应的channel是否存在
            Channel findChannel = users.find(receiverChannel.id());
            userChatMsg.setCreateTime(new Date());
            if (findChannel != null) {
                // 用户在线,发送消息
                receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContent)));
            }
        }
    }

    /**
     * 绑定用户主键与通道并输入打印当前连接信息
     */
    public static void bindUserChannelRel(String sendUserId, Channel currentChannel) {
        //将用户ID与channel绑定
        if (StringUtils.isNotBlank(sendUserId)) {
            UserChannelRel.put(sendUserId, currentChannel);
        }
        //用户主键与通道的绑定信息
        System.out.println("========用户与通道的绑定情况========");
        UserChannelRel.output();
        System.out.println("========用户与通道的绑定情况========");
        //打印所有已连接的通道
        System.out.println("=======当前链接数======");
        for (Channel c : users) {
            System.out.println(c.id().asShortText());
        }
        System.out.println("=======当前链接数======");

    }

    /**
     * 系统推送消息，转发给所有在线的用户
     */
    public static void pushSysMessage(DataContent dataContent) {
        for (Channel receiverChannel : users) {
            receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContent)));
        }
    }

    /**
     * 签收消息
     */
    public static void signedMsg(List<String> msgIdList) {
        if (!msgIdList.isEmpty()) {
            // 批量签收
            chatMsgService.updateMsgSigned(msgIdList);
        }
    }
}
