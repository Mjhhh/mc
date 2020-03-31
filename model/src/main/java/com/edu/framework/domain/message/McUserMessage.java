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
@Table(name="mc_user_message")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class McUserMessage {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    private String userId;
    private String messageId;
    @ApiModelProperty("阅读状态：0-未读，1-已读")
    private String readStatus;
    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty(value = "更新时间", hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
