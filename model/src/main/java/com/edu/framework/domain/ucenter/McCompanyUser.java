package com.edu.framework.domain.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
@Entity
@Table(name="mc_company_user")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class McCompanyUser implements Serializable {
    private static final long serialVersionUID = -916357110051689786L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name="company_id")
    private String companyId;
    @Column(name="user_id")
    private String userId;
    private String status;

}
