package com.edu.framework.domain.message;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class DataContent implements Serializable {
    private static final long serialVersionUID = 870718817843803176L;
    /**
     * 动作的类型
     */
    private int action;
    /**
     * 发送的内容
     */
    private UserChatMsg userChatMsg;
    /**
     * 扩展字段
     */
    private String extand;
}
