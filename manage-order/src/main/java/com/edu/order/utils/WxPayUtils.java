package com.edu.order.utils;

import com.edu.framework.domain.order.McOrders;
import com.edu.order.config.WxConfig;
import com.edu.order.config.WxConstants;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class WxPayUtils {

    /**
     * 生成微信支付二维码
     * @param mcOrders
     * @param payAddr
     * @return
     * @throws Exception
     */
    public static String wxPayUrl(McOrders mcOrders, String payAddr) throws Exception {
        String signType = WxConstants.SING_MD5;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        HashMap<String, String> data = new HashMap<>();
        //公众账号ID
        data.put("appid", WxConfig.appID);
        //商户号
        data.put("mch_id", WxConfig.mchID);
        //随机字符串
        data.put("nonce_str", WxUtil.getNonceStr());
        //商品描述
        data.put("body", mcOrders.getCourseName());
        //商户订单号
        data.put("out_trade_no", mcOrders.getOrderId());
        //商品ID
        data.put("product_id", mcOrders.getOrderId());
        //标价币种
        data.put("fee_type", "CNY");
        //标价金额

        data.put("total_fee", String.valueOf(Math.round(mcOrders.getPrice() * 100)));
        //交易起始时间
        data.put("time_start", simpleDateFormat.format(mcOrders.getStartTime()));
        //交易结束时间
        data.put("time_expire", simpleDateFormat.format(mcOrders.getEndTime()));
        //用户的IP
        data.put("spbill_create_ip", payAddr);
        //通知地址
        data.put("notify_url", WxConfig.notifyUrl);
        //交易类型
        data.put("trade_type", "NATIVE");
        //签名类型
        data.put("sign_type", signType);
        //签名
        data.put("sign", WxUtil.getSignature(data, WxConfig.key, signType));

        String requestXML = WxUtil.mapToXml(data);
        String reponseString = WxHttpsClient.httpsRequestReturnString(WxConstants.PAY_UNIFIEDORDER, WxHttpsClient.METHOD_POST, requestXML);
        Map<String, String> resultMap = WxUtil.processResponseXml(reponseString, signType);
        if (resultMap.get(WxConstants.RETURN_CODE).equals("SUCCESS")) {
            return resultMap.get("code_url");
        }

        return null;
    }

}

