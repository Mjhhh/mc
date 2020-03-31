package com.edu.learning.client;

import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.course.response.TeachplanMediaPub;
import com.edu.framework.domain.order.McOrders;
import com.edu.framework.domain.order.McOrdersPay;
import com.edu.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = ServiceList.SERVER_MANAGE_ORDER)
public interface OrderClient {
    @GetMapping("/order/get")
    McOrders findOrdersByUserAndCourse(@RequestParam String userId, @RequestParam String courseId);

    @GetMapping("/order/getorder/{orderId}")
    McOrders findOrdersByUserAndCourse(@PathVariable String orderId);

    @GetMapping("/order/getorderpay/{orderId}")
    McOrdersPay findOrderPayByOrderId(@PathVariable String orderId);

}
