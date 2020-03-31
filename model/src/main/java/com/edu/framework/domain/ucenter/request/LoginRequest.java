package com.edu.framework.domain.ucenter.request;

import com.edu.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Mjh on 2019-11-11
 */
@Data
@ToString
public class LoginRequest extends RequestData {
    String username;
    String password;
    String verifycode;
    String verifyEncode;
    String phone;
}
