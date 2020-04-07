package com.edu.framework.domain.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装了聊天过程中发送的信息
 */
@Data
@ToString
public class UserChatMsg implements Serializable {
    private static final long serialVersionUID = -4419852523073508191L;
    /**
     * 发送者ID
     */
    private String sendUserId;
    /**
     * 发送者名称
     */
    private String sendUserName;
    /**
     * 接收者ID
     */
    private String acceptUserId;
    /**
     * 接收者昵称
     */
    private String acceptUserName;
    /**
     * 聊天内容
     */
    private String msg;
    /**
     * 消息的主键，用于消息的签收
     */
    private String msgId;
    /**
     * 发送过来的图片地址
     */
    private String picture;
    /**
     * 发送时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
