package com.edu.auth.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.*;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 短信工具类
 */
@Component
public class SmsUtil {

    @Value("${sms.accessKeyId}")
    private String accessKeyId;

    @Value("${sms.accessKeySecret}")
    private String accessKeySecret;


    /**
     * 发送验证码
     */
    private boolean sendValidateCode(String mobile,String code,String templateCode){
        boolean isSuccess = false;
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "惠而信");
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject jsonObject = JSONObject.parseObject(response.getData());
            String message =  jsonObject.getString("Message");
            System.out.println(message);
            if(StringUtils.equals(message,"OK")){
                isSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 注册，登录发送验证码
     * @param mobile
     * @param code
     * @return
     */
    public boolean smsLoginValidateCode(String mobile,String code){
        return this.sendValidateCode(mobile,code,"SMS_162733318");
    }

    /**
     * 修改手机号码，发送验证码
     * @param mobile
     * @param code
     * @return
     */
    public boolean smsUpdateMobileValidateCode(String mobile,String code){
        return this.sendValidateCode(mobile,code,"SMS_171745372");
    }

    /**
     * 修改手机号码，发送验证码
     * @param mobile
     * @param code
     * @return
     */
    public boolean smsUpdatePayPasswordValidateCode(String mobile,String code){
        return this.sendValidateCode(mobile,code,"SMS_171854378");
    }

    /**
     * 修改密码，发送验证码
     * @param mobile
     * @param code
     * @return
     */
    public boolean smsUpdatePwdValidateCode(String mobile,String code){
        return this.sendValidateCode(mobile,code,"SMS_178456666");
    }


}
