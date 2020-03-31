package com.edu.framework.domain.order;

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
@Table(name="mc_orders_pay")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class McOrdersPay implements Serializable {
    private static final long serialVersionUID = -916357210051689789L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "pay_id")
    private String payId;
    private String status;
    @Column(name = "pay_system")
    private String paySystem;
}
