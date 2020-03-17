package com.edu.framework.domain.order.response;

import com.edu.framework.model.response.ResultCode;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;


/**
 * Created by Mjh on 2019-11-11
 */
@ToString
public enum OrderCode implements ResultCode {
    ORDER_ADD_ITEMISNULL(false,41001,"创建订单未选择课程！"),
    ORDER_ADD_ORDERNUMERROR(false,41002,"生成订单号错误！"),
    ORDER_ADD_GETCOURSEERROR(false,41003,"找不到课程信息！"),
    ORDER_FINISH_NOTFOUNDORDER(false,41004,"找不到订单信息！"),
    Pay_NOTFOUNDORDER(false,41010,"找不到要支付的订单！"),
    Pay_USERERROR(false,41011,"支付用户与订单用户不一致！"),
    Pay_NOTFOUNDPAY(false,41012,"支付记录不存在！");

    /**
     * 操作是否成功
     */
    boolean success;

    /**
     * 操作代码
     */
    int code;

    /**
     * 提示信息
     */
    String message;

    OrderCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
