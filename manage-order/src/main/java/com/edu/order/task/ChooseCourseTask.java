package com.edu.order.task;

import com.edu.framework.domain.task.McTask;
import com.edu.order.config.RabbitMQConfig;
import com.edu.order.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class ChooseCourseTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseCourseTask.class);

    @Autowired
    TaskService taskService;

    /**
     * 定时发送添加选课任务
     * 每隔1分钟扫描消息表，向mq发送消息
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void sendChoosecourseTask() {
        //得到1分钟之前的时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(GregorianCalendar.MINUTE, -1);
        Date time = calendar.getTime();
        List<McTask> mcTaskList = taskService.findTaskList(time, 100);
        //调用service发布消息，将添加选课的任务发送给mq
        for (McTask mcTask : mcTaskList) {
            //取任务
            if (taskService.getTask(mcTask.getId(), mcTask.getVersion()) > 0) {
                //要发送的交换机
                String ex = mcTask.getMqExchange();
                //发送消息要带routingKey
                String routingKey = mcTask.getMqRoutingkey();
                taskService.publish(mcTask, ex, routingKey);
            }

        }
    }

    /**
     * 接收选课响应
     * @param mcTask
     */
    @RabbitListener(queues = RabbitMQConfig.MC_LEARNING_FINISHADDCHOOSECOURSE)
    public void receiveFinishChoosecourseTask(McTask mcTask) {
        if (mcTask != null && StringUtils.isNotEmpty(mcTask.getId())) {
            taskService.finishTask(mcTask.getId());
        }
    }
}