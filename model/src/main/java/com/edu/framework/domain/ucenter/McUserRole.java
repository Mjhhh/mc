package com.edu.framework.domain.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
@Entity
@Table(name="mc_user_role")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class McUserRole {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name="user_id")
    private String userId;
    @Column(name="role_id")
    private String roleId;
    @Column(name="create_time")
    private Date createTime;

}
