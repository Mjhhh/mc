package com.edu.order.controller;

import com.edu.api.order.OrderControllerApi;
import com.edu.framework.domain.order.McOrders;
import com.edu.framework.domain.order.McOrdersPay;
import com.edu.framework.domain.order.request.QueryMcOrderRequest;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController implements OrderControllerApi {

    @Autowired
    OrderService orderService;

    @Override
    @ResponseBody
    @GetMapping("/list/{page}/{size}")
    public CommonResponseResult findList(@PathVariable int page, @PathVariable int size, QueryMcOrderRequest queryMcOrderRequest) {
        return orderService.findList(page,size, queryMcOrderRequest);
    }

    @Override
    @ResponseBody
    @GetMapping("/get")
    public McOrders findOrdersByUserAndCourse(@RequestParam String userId, @RequestParam String courseId) {
        return orderService.findOrdersByUserAndCourse(userId, courseId);
    }

    @Override
    @ResponseBody
    @GetMapping("/getoder/{orderId}")
    public CommonResponseResult findOrder(@PathVariable String orderId) {
        return orderService.findOrder(orderId);
    }

    @Override
    @ResponseBody
    @GetMapping("/getorder/user/{page}/{size}")
    public CommonResponseResult findOrderByUser(@PathVariable int page, @PathVariable int size, @RequestParam String userId) {
        return orderService.findOrderByUser(page, size, userId);
    }

    @Override
    @ResponseBody
    @PostMapping("/add")
    public CommonResponseResult addOrder(@RequestParam String courseId) {
        return orderService.addOrder(courseId);
    }

    @Override
    @ResponseBody
    @PutMapping("/edit")
    public ResponseResult editOrder(@RequestParam String courseId, @RequestParam String status) {
        return orderService.editOrder(courseId, status);
    }

    @Override
    @ResponseBody
    @GetMapping("/getorderpay/{orderId}")
    public McOrdersPay findOrderPayByOrderId(@PathVariable String orderId) {
        return orderService.findOrderPayByOrderId(orderId);
    }

    @Override
    @GetMapping("/alipay/pay")
    public void alipay(@RequestParam String orderId) {
        orderService.alipay(orderId);
    }

    @Override
    @PostMapping("/alipay/notify/url")
    public void aliNotifyUrl() {
        orderService.notifyUrl();
    }

    @Override
    @GetMapping("/wxpay/check")
    @ResponseBody
    public CommonResponseResult createWxPay(@RequestParam String orderId) {
        return orderService.createWxPay(orderId);
    }

    @Override
    @GetMapping("/wxpay/pay")
    @ResponseBody
    public CommonResponseResult wxpay(@RequestParam String orderId) {
        return orderService.wxpay(orderId);
    }

    @Override
    @PostMapping("/wxpay/notify/url")
    public void wxNotifyUrl() {
        orderService.wxNotifyUrl();
    }


}
