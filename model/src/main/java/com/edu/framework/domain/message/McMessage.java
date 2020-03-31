package com.edu.framework.domain.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@Entity
@Table(name="mc_message")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class McMessage {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    private String content;
    @ApiModelProperty("接受者:0-通知所有用户、其他数字通知单用户")
    private String receiver;
    @ApiModelProperty("消息类型:message-系统消息 invite-邀请消息")
    private String type;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;
}
