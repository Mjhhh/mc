package com.edu.api.order;

import com.edu.framework.domain.order.McOrders;
import com.edu.framework.domain.order.McOrdersPay;
import com.edu.framework.domain.order.request.QueryMcOrderRequest;
import com.edu.framework.domain.ucenter.request.QueryMcUserRequest;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(value = "订单接口管理",tags = "订单接口管理，负责订单的增删改查")
public interface OrderControllerApi {

    @ApiOperation("查找订单列表")
    CommonResponseResult findList(int page, int size, QueryMcOrderRequest queryMcOrderRequest);

    @ApiOperation("根据用户id和课程id查找订单")
    McOrders findOrdersByUserAndCourse(String userId,String courseId);

    @ApiOperation("查找订单")
    CommonResponseResult findOrder(String orderId);

    @ApiOperation("根据用户id查找订单")
    CommonResponseResult findOrderByUser(int page, int size, String userId);

    @ApiOperation("添加订单")
    CommonResponseResult addOrder(String courseId);

    @ApiOperation("修改订单状态")
    ResponseResult editOrder(String courseId, String status);

    @ApiOperation("查询支付单")
    McOrdersPay findOrderPayByOrderId(String orderId);

    @ApiOperation("支付宝支付")
    void alipay(String orderId);

    @ApiOperation("支付宝-服务器异步通知链接")
    void aliNotifyUrl();

    @ApiOperation("微信支付-生成二维码")
    CommonResponseResult wxpay(String orderId);

    @ApiOperation("创建微信支付单号")
    CommonResponseResult createWxPay(String orderId);

    @ApiOperation("微信支付-服务器异步通知链接")
    void wxNotifyUrl();
}
