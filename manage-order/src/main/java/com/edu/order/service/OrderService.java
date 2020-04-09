package com.edu.order.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.framework.domain.course.CourseBase;
import com.edu.framework.domain.course.CourseMarket;
import com.edu.framework.domain.course.CoursePic;
import com.edu.framework.domain.order.McOrders;
import com.edu.framework.domain.order.ext.McOrdersExt;
import com.edu.framework.domain.order.McOrdersPay;
import com.edu.framework.domain.order.request.AliPayRequestParams;
import com.edu.framework.domain.order.request.QueryMcOrderRequest;
import com.edu.framework.domain.order.response.OrderCode;
import com.edu.framework.domain.ucenter.request.QueryMcUserRequest;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.utils.CommonUtil;
import com.edu.framework.utils.McOauth2Util;
import com.edu.order.client.CourseClient;
import com.edu.order.config.AlipayConfig;
import com.edu.order.config.WxConfig;
import com.edu.order.config.WxConstants;
import com.edu.order.dao.McOrdersMapper;
import com.edu.order.dao.McOrdersPayRepository;
import com.edu.order.dao.McOrdersRepository;
import com.edu.order.utils.AliPayUtils;
import com.edu.order.utils.WxPayUtils;
import com.edu.order.utils.WxUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    McOrdersRepository mcOrdersRepository;

    @Autowired
    McOrdersPayRepository mcOrdersPayRepository;

    @Autowired
    CourseClient courseClient;

    @Autowired
    McOrdersMapper mcOrdersMapper;

    /**
     * 获取公司id
     * @return
     */
    private String getCompanyId() {
        HttpServletRequest request = this.getRequest();
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        if (StringUtils.isBlank(userJwt.getCompanyId())) {
            ExceptionCast.cast(CommonCode.MISS_COMPANY_ID);
        }
        return userJwt.getCompanyId();
    }

    /**
     * 获得订单
     *
     * @return
     */
    private McOrders getMcOrders(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        Optional<McOrders> optional = mcOrdersRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 获取HttpServletRequest
     *
     * @return
     */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取HttpServletResponse
     *
     * @return
     */
    private HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取用户id
     *
     * @return
     */
    private String getUserId() {
        HttpServletRequest request = this.getRequest();
        McOauth2Util mcOauth2Util = new McOauth2Util();
        McOauth2Util.UserJwt userJwt = mcOauth2Util.getUserJwtFromHeader(request);
        if (StringUtils.isBlank(userJwt.getId())) {
            ExceptionCast.cast(CommonCode.UNAUTHENTICATED);
        }
        return userJwt.getId();
    }

    /**
     * @param courseId
     * @return
     */
    @Transactional
    public CommonResponseResult addOrder(String courseId) {
        if (StringUtils.isBlank(courseId)) {
            ExceptionCast.cast(CommonCode.OBJECT_IS_NOT_EXISTS);
        }
        String userId = this.getUserId();
        McOrders mcOrders = mcOrdersRepository.findByUserIdAndCourseId(userId, courseId);
        if (mcOrders == null) {
            mcOrders = new McOrders();
        }
        mcOrders.setUserId(userId);
        mcOrders.setCourseId(courseId);
        //获取课程基本信息
        CommonResponseResult baseResult = courseClient.getCourseBaseById(courseId);
        CourseBase courseBase = JSON.parseObject(JSONObject.toJSONString(baseResult.getData()), CourseBase.class);
        mcOrders.setCourseName(courseBase.getName());
        //获取课程营销信息
        CommonResponseResult marketResut = courseClient.getCourseMarketById(courseId);
        CourseMarket courseMarket = JSON.parseObject(JSONObject.toJSONString(marketResut.getData()), CourseMarket.class);
        mcOrders.setPrice(courseMarket.getPrice());
        mcOrders.setInitialPrice(courseMarket.getPriceOld());
        mcOrders.setValid(courseMarket.getValid());
        //401001-未付款 401002-已付款 401003-已取消
        if (StringUtils.isBlank(mcOrders.getStatus()) || (StringUtils.isNotBlank(mcOrders.getStatus()) && !StringUtils.equals(mcOrders.getStatus(), "401002"))) {
            mcOrders.setStatus("401001");
        }
        mcOrders.setStartTime(new Date());
        mcOrders.setEndTime(new Date(System.currentTimeMillis() + (10 * 60 * 1000)));
        McOrders save = mcOrdersRepository.save(mcOrders);

        return new CommonResponseResult(CommonCode.SUCCESS, save.getOrderId());
    }

    /**
     * 修改订单
     *
     * @param courseId
     * @param status
     * @return
     */
    public ResponseResult editOrder(String courseId, String status) {
        if (StringUtils.isBlank(courseId) || StringUtils.isBlank(status)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        String userId = this.getUserId();
        McOrders mcOrders = mcOrdersRepository.findByUserIdAndCourseId(userId, courseId);
        if (mcOrders == null) {
            ExceptionCast.cast(OrderCode.ORDER_IS_NOT_EXISTS);
        }
        //401001-未付款 401002-已付款 401003-已取消
        //取消订单
        if (StringUtils.equals(status, "401003")) {
            mcOrders.setStatus(status);
        }
        //订单付款
        else if (StringUtils.equals(status, "401002")) {
            McOrdersPay mcOrdersPay = mcOrdersPayRepository.findByOrderId(mcOrders.getOrderId());
            //402001-未支付 402002-支付成功 402003-已关闭 402004-支付失败
            if (mcOrdersPay == null || !StringUtils.equals(mcOrders.getStatus(), "402002")) {
                ExceptionCast.cast(OrderCode.PAY_NOTFOUNDPAY);
            }
            mcOrders.setStatus(status);
        }
        //未付款
        else if (StringUtils.equals(status, "401001")) {
            mcOrders.setStatus(status);
        }
        mcOrdersRepository.save(mcOrders);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 支付宝支付
     *
     * @param orderId
     * @return
     */
    public void alipay(String orderId) {
        McOrders mcOrders = this.getMcOrders(orderId);
        if (mcOrders == null) {
            ExceptionCast.cast(OrderCode.ORDER_IS_NOT_EXISTS);
        }
        AliPayRequestParams aliPayRequestParams = new AliPayRequestParams();
        aliPayRequestParams.setOutTradeNo(mcOrders.getOrderId());
        aliPayRequestParams.setBody(mcOrders.getCourseName());
        aliPayRequestParams.setSubject(mcOrders.getCourseName());
        aliPayRequestParams.setTotalAmount(mcOrders.getPrice().toString());

        AliPayUtils alipayUtils = new AliPayUtils();
        String form = alipayUtils.pay(aliPayRequestParams);
        HttpServletResponse response = this.getResponse();
        try {
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            //直接将完整的表单html输出到页面
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付宝-服务器异步通知页面
     */
    @Transactional(rollbackFor = Exception.class)
    public void notifyUrl() {
        HttpServletRequest request = this.getRequest();
        HttpServletResponse response = this.getResponse();
        AliPayUtils aliPayUtils = new AliPayUtils();
        boolean signVerified = aliPayUtils.notifyUrl(request);
        try {
            //验证成功
            if (signVerified) {
                //商户订单号
                String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(), StandardCharsets.UTF_8);
                //支付宝交易号
                String tradeNo = new String(request.getParameter("trade_no").getBytes(), StandardCharsets.UTF_8);
                //交易状态
                String tradeStatus = new String(request.getParameter("trade_status").getBytes(), StandardCharsets.UTF_8);
                //交易金额
                String totalAmount = new String(request.getParameter("total_amount").getBytes(), StandardCharsets.UTF_8);

                McOrdersPay mcOrdersPay = mcOrdersPayRepository.findByOrderId(outTradeNo);
                //交易结束，不可退款
                if (StringUtils.equals(tradeStatus, "TRADE_FINISHED")) {
                    //402001-未支付 402002-支付成功 402003-已关闭 402004-支付失败
                    if (!StringUtils.equals(mcOrdersPay.getStatus(), "402003")) {
                        mcOrdersPay.setStatus("402003");
                        mcOrdersPayRepository.save(mcOrdersPay);
                    }
                }
                //交易支付成功
                else if (tradeStatus.equals("TRADE_SUCCESS")) {
                    if (mcOrdersPay == null) {
                        mcOrdersPay = new McOrdersPay();
                    }
                    mcOrdersPay.setOrderId(outTradeNo);
                    //402001-未支付 402002-支付成功 402003-已关闭 402004-支付失败
                    mcOrdersPay.setStatus("402002");
                    mcOrdersPay.setPayId(tradeNo);
                    //1-支付宝 0-微信
                    mcOrdersPay.setPaySystem("1");
                    mcOrdersPayRepository.save(mcOrdersPay);

                    McOrders mcOrders = this.getMcOrders(outTradeNo);
                    mcOrders.setStatus("401002");
                    mcOrders.setPayTime(new Date());
                    mcOrdersRepository.save(mcOrders);
                }
                response.getWriter().print("success");
            }
            //验证失败
            else {
                response.getWriter().print("error");
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找支付单
     *
     * @param orderId
     * @return
     */
    public McOrdersPay findOrderPayByOrderId(String orderId) {
        McOrdersPay mcOrdersPay = mcOrdersPayRepository.findByOrderId(orderId);
        return mcOrdersPay;
    }

    /**
     * 根据用户id和课程id查找订单
     *
     * @param userId
     * @param courseId
     * @return
     */
    public McOrders findOrdersByUserAndCourse(String userId, String courseId) {
        McOrders mcOrders = mcOrdersRepository.findByUserIdAndCourseId(userId, courseId);
        return mcOrders;
    }

    /**
     * 查找订单
     *
     * @param orderId
     * @return
     */
    public CommonResponseResult findOrder(String orderId) {
        McOrders mcOrders = this.getMcOrders(orderId);
        return new CommonResponseResult(CommonCode.SUCCESS, mcOrders);
    }

    /**
     * 微信支付-生成二维码
     *
     * @param orderId
     */
    public CommonResponseResult wxpay(String orderId) {
        McOrders mcOrders = this.getMcOrders(orderId);
        if (mcOrders == null) {
            ExceptionCast.cast(OrderCode.ORDER_IS_NOT_EXISTS);
        }
        //401001-未付款 401002-已付款 401003-已取消
        if (!StringUtils.equals(mcOrders.getStatus(), "401001")) {
            ExceptionCast.cast(OrderCode.ORDER_STATUS_ERROR);
        }
        McOrdersPay mcOrdersPay = mcOrdersPayRepository.findByOrderId(orderId);
        if (mcOrdersPay == null) {
            mcOrdersPay = new McOrdersPay();
        }
        //402001-未支付 402002-支付成功 402003-已关闭 402004-支付失败
        if (StringUtils.isNotBlank(mcOrdersPay.getStatus()) && !StringUtils.equals(mcOrdersPay.getStatus(), "402001")) {
            ExceptionCast.cast(OrderCode.ORDER_STATUS_ERROR);
        }
        try {
            if (mcOrders == null) {
                ExceptionCast.cast(OrderCode.ORDER_IS_NOT_EXISTS);
            }
            HttpServletRequest request = this.getRequest();
            HttpServletResponse response = this.getResponse();
            String codeUrl = WxPayUtils.wxPayUrl(mcOrders, CommonUtil.getRemortIP(request));
            return new CommonResponseResult(CommonCode.SUCCESS, codeUrl);
//            WxUtil.writerPayImage(response, WxPayUtils.wxPayUrl(mcOrders, CommonUtil.getRemortIP(request)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommonResponseResult(CommonCode.FAIL,null);
    }

    /**
     * 微信支付-服务器异步通知链接
     */
    public void wxNotifyUrl() {
        HttpServletRequest request = this.getRequest();
        HttpServletResponse response = this.getResponse();
        String xmlContent = "<xml>" +
                "<return_code><![CDATA[FAIL]]></return_code>" +
                "<return_msg><![CDATA[签名失败]]></return_msg>" +
                "</xml>";
        try {
            String requstXml = WxUtil.getStreamString(request.getInputStream());
            Map<String, String> returnMap = WxUtil.xmlToMap(requstXml);
            String returnCode = returnMap.get(WxConstants.RETURN_CODE);
            //校验
            if (StringUtils.isNotBlank(returnCode) && StringUtils.equals(returnCode, "SUCCESS") && WxUtil.isSignatureValid(returnMap, WxConfig.key, WxConstants.SING_MD5)) {

                //商户订单号
                String outTradeNo = returnMap.get("out_trade_no");
                //微信支付订单号
                String transactionId = returnMap.get("transaction_id");

                McOrdersPay mcOrdersPay = mcOrdersPayRepository.findByOrderId(outTradeNo);

                //交易支付成功
                if (mcOrdersPay == null) {
                    mcOrdersPay = new McOrdersPay();
                }
                mcOrdersPay.setOrderId(outTradeNo);
                //402001-未支付 402002-支付成功 402003-已关闭 402004-支付失败
                mcOrdersPay.setStatus("402002");
                mcOrdersPay.setPayId(transactionId);
                //1-支付宝 0-微信
                mcOrdersPay.setPaySystem("0");
                mcOrdersPayRepository.save(mcOrdersPay);

                McOrders mcOrders = this.getMcOrders(outTradeNo);
                mcOrders.setStatus("401002");
                mcOrders.setPayTime(new Date());
                mcOrdersRepository.save(mcOrders);


                xmlContent = "<xml>" +
                        "<return_code><![CDATA[SUCCESS]]></return_code>" +
                        "<return_msg><![CDATA[OK]]></return_msg>" +
                        "</xml>";
            }
            WxUtil.responsePrint(response, xmlContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建微信支付单号
     * @param orderId
     * @return
     */
    public CommonResponseResult createWxPay(String orderId) {
        McOrders mcOrders = this.getMcOrders(orderId);
        if (mcOrders == null) {
            ExceptionCast.cast(OrderCode.ORDER_IS_NOT_EXISTS);
        }
        //401001-未付款 401002-已付款 401003-已取消
        if (!StringUtils.equals(mcOrders.getStatus(), "401001")) {
            ExceptionCast.cast(OrderCode.ORDER_STATUS_ERROR);
        }
        McOrdersPay mcOrdersPay = mcOrdersPayRepository.findByOrderId(orderId);
        if (mcOrdersPay == null) {
            mcOrdersPay = new McOrdersPay();
        }
        //402001-未支付 402002-支付成功 402003-已关闭 402004-支付失败
        if (StringUtils.isNotBlank(mcOrdersPay.getStatus()) && !StringUtils.equals(mcOrdersPay.getStatus(), "402001")) {
            ExceptionCast.cast(OrderCode.ORDER_STATUS_ERROR);
        }
        String url = "/api/order/wxpay/pay?orderId=" + orderId + "&data=" + System.currentTimeMillis();
        return new CommonResponseResult(CommonCode.SUCCESS, url);
    }

    /**
     * 根据用户id获取订单信息
     * @param userId
     * @return
     */
    public CommonResponseResult findOrderByUser(int page, int size, String userId) {
        page = page - 1;
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        if (StringUtils.isBlank(userId)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        Page<Object> startPage = PageHelper.startPage(page, size);
        List<McOrders> mcOrdersList = mcOrdersMapper.selectListByUserId(userId);
        List<McOrdersExt> mcOrdersExts = new ArrayList<>();
        for (McOrders mcOrder : mcOrdersList) {
            CommonResponseResult commonResponseResult = courseClient.findCoursePic(mcOrder.getCourseId());
            CoursePic coursePic = JSON.parseObject(JSONObject.toJSONString(commonResponseResult.getData()), CoursePic.class);
            if (coursePic != null) {
                McOrdersExt mcOrdersExt = new McOrdersExt();
                BeanUtils.copyProperties(mcOrder, mcOrdersExt);
                mcOrdersExt.setPic(coursePic.getPic());
                mcOrdersExts.add(mcOrdersExt);
            }
        }
        JSONObject result = new JSONObject();
        result.put("total", startPage.getTotal());
        result.put("list", mcOrdersExts);
        return new CommonResponseResult(CommonCode.SUCCESS, result);
    }

    /**
     * 查找订单列表
     * @param page
     * @param size
     * @param queryMcOrderRequest
     * @return
     */
    public CommonResponseResult findList(int page, int size, QueryMcOrderRequest queryMcOrderRequest) {
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        Page<Object> startPage = PageHelper.startPage(page, size);

        String companyId = this.getCompanyId();
        List<CourseBase> courseBaseList = courseClient.findCourseBaseByCompanyId(companyId);
        List<String> courseIds = new ArrayList<>();
        for (CourseBase courseBase : courseBaseList) {
            courseIds.add(courseBase.getId());
        }
        queryMcOrderRequest.setCourseIds(courseIds);
        List<McOrdersExt> mcOrdersList = mcOrdersMapper.selectListByCourseIds(queryMcOrderRequest);
        Map<String, Object> result = new HashMap<>();
        result.put("list", mcOrdersList);
        result.put("total", startPage.getTotal());
        return new CommonResponseResult(CommonCode.SUCCESS, result);
    }
}
