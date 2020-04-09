package com.edu.framework.domain.order;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
@Entity
@Table(name="mc_orders")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class McOrders implements Serializable {
    private static final long serialVersionUID = -916357210051689789L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "order_id",length = 32)
    private String orderId;
    @Column(name = "initial_price")
    private Float initialPrice;
    private Float price;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "course_id")
    private String courseId;
    @Column(name = "course_name")
    private String courseName;
    private String valid;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "pay_time")
    private Date payTime;
    private String status;
    @Column(name = "details")
    private String details;

}
