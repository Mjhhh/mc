package com.edu.framework.domain.ucenter.request;

import com.edu.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class QueryMcUserRequest extends RequestData {
    String name;
    String utype;
    String status;
}
