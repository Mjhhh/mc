package com.edu.framework.domain.ucenter.response;


import com.edu.framework.domain.ucenter.McCompany;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class McCompanyResult extends ResponseResult {
    public McCompanyResult(ResultCode resultCode, McCompany data) {
        super(resultCode);
        this.data = data;
    }
    private McCompany data;
}
