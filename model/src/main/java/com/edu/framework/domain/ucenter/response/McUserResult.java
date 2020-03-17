package com.edu.framework.domain.ucenter.response;

import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class McUserResult extends ResponseResult {
    McUser mcUser;
    public McUserResult(ResultCode resultCode, McUser mcUser) {
        super(resultCode);
        this.mcUser = mcUser;
    }
}
