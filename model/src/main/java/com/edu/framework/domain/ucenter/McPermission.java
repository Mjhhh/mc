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
@Table(name="mc_permission")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class McPermission {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name="role_id")
    private String roleId;
    @Column(name="menu_id")
    private String menuId;
    @Column(name="create_time")
    private Date createTime;


}
