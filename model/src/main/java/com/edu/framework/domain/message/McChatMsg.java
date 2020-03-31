package com.edu.framework.domain.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Mjh
 */
@Data
@ToString
@Entity
@Table(name="mc_chat_msg")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class McChatMsg {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name = "send_user_id")
    private String sendUserId;
    @Column(name = "send_user_name")
    private String sendUserName;
    @Column(name = "accept_user_id")
    private String acceptUserId;
    @Column(name = "accept_user_name")
    private String acceptUserName;
    private String msg;
    @Column(name = "sign_flag")
    private Integer signFlag;
    @Column(name = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String picture;

}