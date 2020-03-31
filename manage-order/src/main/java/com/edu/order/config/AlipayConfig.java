package com.edu.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class AlipayConfig {

    /**
     * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
     */
    public static String APP_ID;

    @Value("${alipay.app_id}")
    public void setAppId(String appId) {
        APP_ID = appId;
    }

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    public static String APP_PRIVATE_KEY;

    @Value("${alipay.app_private_key}")
    public void setAppPrivateKey(String appPrivateKey) {
        APP_PRIVATE_KEY = appPrivateKey;
    }

    /**
     * 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
     */
    public static String ALIPAY_PUBLIC_KEY;

    @Value("${alipay.alipay_public_key}")
    public void setAlipayPublicKey(String alipayPublicKey) {
        ALIPAY_PUBLIC_KEY = alipayPublicKey;
    }

    /**
     * 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static String NOTIFY_URL;

    @Value("${alipay.notify_url}")
    public void setNotify_url(String notify_url) {
        AlipayConfig.NOTIFY_URL = notify_url;
    }

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
     */
    public static String RETURN_URL;

    @Value("${alipay.return_url}")
    public void setReturn_url(String return_url) {
        AlipayConfig.RETURN_URL = return_url;
    }

    /**
     * 返回格式
     */
    public static String FORMAT;

    @Value("${alipay.format}")
    public void setFormat(String format) {
        AlipayConfig.FORMAT = format;
    }

    /**
     * 签名方式
     */
    public static String SIGN_TYPE;

    @Value("${alipay.sign_type}")
    public void setSign_type(String sign_type) {
        AlipayConfig.SIGN_TYPE = sign_type;
    }

    /**
     * 字符编码格式
     */
    public static String CHARSET;

    @Value("${alipay.charset}")
    public void setCHARSET(String CHARSET) {
        AlipayConfig.CHARSET = CHARSET;
    }

    /**
     * 支付宝网关，这是沙箱的网关
     */
    public static String GATEWAY_URL;

    @Value("${alipay.gatewayUrl}")
    public void setGatewayUrl(String gatewayUrl) {
        AlipayConfig.GATEWAY_URL = gatewayUrl;
    }

    /**
     * 支付宝网关
     */
    public static String LOG_PATH;

    @Value("${alipay.log_path}")
    public void setLog_path(String log_path) {
        AlipayConfig.LOG_PATH = log_path;
    }

    //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(LOG_PATH + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

