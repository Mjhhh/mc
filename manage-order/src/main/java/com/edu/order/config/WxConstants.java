package com.edu.order.config;

/**
 * 微信常量类
 */
public class WxConstants {

    /**
     * 默认编码
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 统一下单-扫描支付
     */
    public static String PAY_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 返回状态码
     */
    public static final String RETURN_CODE= "return_code";

    /**
     * 签名类型 MD5
     */
    public static final String SING_MD5 = "MD5";

    /**
     * 签名类型 HMAC-SHA256
     */
    public static final String SING_HMACSHA256 = "HMAC-SHA256";

}
