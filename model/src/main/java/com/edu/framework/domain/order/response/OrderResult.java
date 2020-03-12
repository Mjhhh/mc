package com.edu.framework.domain.order.response;

import com.edu.framework.domain.order.McOrders;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mrt on 2018/3/26.
 */
@Data
@ToString
public class OrderResult extends ResponseResult {
    private McOrders mcOrders;
    public OrderResult(ResultCode resultCode, McOrders mcOrders) {
        super(resultCode);
        this.mcOrders = mcOrders;
    }


}
