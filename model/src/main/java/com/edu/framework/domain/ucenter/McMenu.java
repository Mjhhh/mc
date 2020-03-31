package com.edu.framework.domain.ucenter;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name="mc_menu")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class McMenu {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name="p_id")
    private String pId;
    private String code;
    @Column(name="menu_name")
    private String menuName;
    private String url;
    @Column(name="is_menu")
    private String isMenu;
    private Integer level;
    private Integer sort;
    private String status;
    private String icon;
    @Column(name="create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Column(name="update_time")
    private Date updateTime;


}
