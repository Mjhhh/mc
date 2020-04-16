package com.edu.learning.task;

import com.alibaba.fastjson.JSON;
import com.edu.framework.domain.order.McOrders;
import com.edu.framework.domain.task.McTask;
import com.edu.framework.model.response.ResponseResult;
import com.edu.learning.config.RabbitMQConfig;
import com.edu.learning.service.LearningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChooseCourseTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseCourseTask.class);

    @Autowired
    LearningService learningService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.MC_LEARNING_ADDCHOOSECOURSE)
    public void receiveChoosecourseTask(McTask mcTask) {
        LOGGER.info("receive choose course task,taskId:{}",mcTask.getId());
        //取出消息的内容
        String requestBody = mcTask.getRequestBody();
        McOrders mcOrders = JSON.parseObject(requestBody, McOrders.class);

        //添加选课
        ResponseResult addcourse = learningService.addcourse(mcOrders, mcTask);
        if (addcourse.isSuccess()) {
            //添加选课成功，要向mq发送完成添加选课的消息
            rabbitTemplate.convertAndSend(RabbitMQConfig.EX_LEARNING_ADDCHOOSECOURSE, RabbitMQConfig.MC_LEARNING_FINISHADDCHOOSECOURSE_KEY, mcTask);
            LOGGER.info("send finish choose course taskId:{}",mcTask.getId());
        }
    }
}