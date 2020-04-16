package com.edu.order.service;

import com.edu.framework.domain.task.McTask;
import com.edu.framework.domain.task.McTaskHis;
import com.edu.order.dao.McTaskHisRepository;
import com.edu.order.dao.McTaskRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    McTaskRepository mcTaskRepository;

    @Autowired
    McTaskHisRepository mcTaskHisRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 取出前n条任务,取出指定时间之前处理的任务
     *
     * @param updateTime
     * @param size
     * @return
     */
    public List<McTask> findTaskList(Date updateTime, int size) {
        //设置分页参数，取出前n条记录
        Pageable pageable = PageRequest.of(0, size);
        Page<McTask> mcTasks = mcTaskRepository.findByUpdateTimeBefore(pageable, updateTime);
        return mcTasks.getContent();
    }

    /**
     * 发布消息
     *
     * @param mcTask
     * @param ex
     * @param routingKey
     */
    public void publish(McTask mcTask, String ex, String routingKey) {
        Optional<McTask> optional = mcTaskRepository.findById(mcTask.getId());
        if (optional.isPresent()) {
            rabbitTemplate.convertAndSend(ex, routingKey, mcTask);
            //更新任务时间
            McTask one = optional.get();
            one.setUpdateTime(new Date());
            mcTaskRepository.save(one);
        }
    }

    /**
     * 获取任务
     *
     * @param id
     * @param version
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int getTask(String id, int version) {
        //通过乐观锁的方式来更新数据表，如果结果大于0说明取到任务
        int count = mcTaskRepository.updateTaskVersion(id, version);
        return count;
    }

    /**
     * 完成任务
     *
     * @param taskId
     */
    @Transactional(rollbackFor = Exception.class)
    public void finishTask(String taskId) {
        Optional<McTask> optionalMcTask = mcTaskRepository.findById(taskId);
        if (optionalMcTask.isPresent()) {
            //当前任务
            McTask mcTask = optionalMcTask.get();
            //历史任务
            McTaskHis mcTaskHis = new McTaskHis();
            BeanUtils.copyProperties(mcTask, mcTaskHis);
            mcTaskHisRepository.save(mcTaskHis);
            mcTaskRepository.delete(mcTask);
        }
    }
}
