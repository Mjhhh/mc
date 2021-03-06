package com.edu.framework.domain.order.ext;

import com.edu.framework.domain.order.McOrders;
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
public class McOrdersExt extends McOrders {
    private String payId;
    private String paySystem;
    private String pic;
}
