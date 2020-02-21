package com.edu.framework.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 通用返回数据类
 * @author Admin
 */
@Data
@ToString
@NoArgsConstructor
public class CommonResponseResult extends ResponseResult {

    Object data;

    public CommonResponseResult(ResultCode resultCode, Object data) {
        super(resultCode);
        this.data = data;
    }

}
