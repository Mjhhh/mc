package com.edu.framework.domain.order.request;

import com.edu.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class QueryMcOrderRequest extends RequestData {
    String orderId;
    String courseName;
    String status;
    List<String> courseIds;
}
