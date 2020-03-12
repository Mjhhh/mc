package com.edu.framework.domain.order.response;

import com.edu.framework.domain.order.McOrdersPay;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mrt on 2018/3/27.
 */
@Data
@ToString
public class PayOrderResult extends ResponseResult {
    public PayOrderResult(ResultCode resultCode) {
        super(resultCode);
    }
    public PayOrderResult(ResultCode resultCode, McOrdersPay mcOrdersPay) {
        super(resultCode);
        this.mcOrdersPay = mcOrdersPay;
    }
    private McOrdersPay mcOrdersPay;
    private String orderNumber;

    //当tradeState为NOTPAY（未支付）时显示支付二维码
    private String codeUrl;
    private Float money;


}
