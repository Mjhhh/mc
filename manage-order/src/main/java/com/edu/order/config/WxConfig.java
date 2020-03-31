package com.edu.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 微信开发配置类
 */
@Component
public class WxConfig {

    /**
     * 开发者ID
     */
    public static String appID;

    @Value("${wx.appID}")
    public void setAppID(String appID) {
        WxConfig.appID = appID;
    }

    /**
     * 开发者密码
     */
    public static String appSecret;

    @Value("${wx.appSecret}")
    public void setAppSecret(String appSecret) {
        WxConfig.appSecret = appSecret;
    }

    /**
     * 商户号
     */
    public static String mchID;

    @Value("${wx.mchID}")
    public void setMchID(String mchID) {
        WxConfig.mchID = mchID;
    }

    /**
     * API密钥
     */
    public static String key;

    @Value("${wx.key}")
    public void setKey(String key) {
        WxConfig.key = key;
    }

    /**
     * 统一下单-通知链接
     */
    public static String notifyUrl;

    @Value("${wx.notify_url}")
    public void setNotifyUrl(String notifyUrl) {
        WxConfig.notifyUrl = notifyUrl;
    }

    /**
     * 连接超时时间
     */
    public static int connectionTimeout;

    @Value("${wx.connectionTimeout}")
    public void setConnectionTimeout(int connectionTimeout) {
        WxConfig.connectionTimeout = connectionTimeout;
    }

    /**
     * 连接超时时间
     */
    public static int readTimeout;

    @Value("${wx.readTimeout}")
    public void setReadTimeout(int readTimeout) {
        WxConfig.readTimeout = readTimeout;
    }

    //支付map缓存处理
    private static HashMap<String, String> payMap = new HashMap<String, String>();

    public static String getPayMap(String key) {
        return payMap.get(key);
    }

    public static void setPayMap(String key, String value) {
        payMap.put(key, value);
    }


}
