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
    /**
     * 可以存在任何数据的容器
     */
    Object data;
    public CommonResponseResult(ResultCode resultCode, Object data) {
        super(resultCode);
        this.data = data;
    }

}
