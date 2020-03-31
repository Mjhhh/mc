package com.edu.framework.domain.order.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AliPayRequestParams {
    /**
     * 商户订单
     */
    private String outTradeNo;
    /**
     * 支付金额
     */
    private String totalAmount;
    /**
     * 订单名称
     */
    private String subject;
    /**
     * 商品描述
     */
    private String body;
}
