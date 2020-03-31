package com.edu.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.edu.framework.domain.cms.CmsPage;
import com.edu.manage_cms_client.dao.CmsPageRepository;
import com.edu.manage_cms_client.service.CmsPageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;


/**
 * 发布页面的消息消费方
 *
 * @author Administrator
 */
@Component
public class ConsumerPostPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerPostPage.class);
    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsPageService cmsPageService;

    @RabbitListener(queues = {"${mq.queue}"})
    public void editPage(String msg) {
        //解析消息
        Map map = JSON.parseObject(msg, Map.class);
        LOGGER.info("receive cms post page:{}", msg);
        //取出页面id
        String pageId = (String) map.get("pageId");
        //取出消息的类型
        String type = (String) map.get("type");
        //查询页面消息
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (!optional.isPresent()) {
            LOGGER.error("receive cms post page,cmsPage is null:{}", msg);
            return;
        }
        if (StringUtils.equals(type, "post")) {
            //将页面保存到服务器的物理路径
            cmsPageService.savePageToServerPath(pageId);
        }
        if (StringUtils.equals(type, "delete")) {
            //将页面从服务器删除
            cmsPageService.deletePageFormServerPath(pageId);
        }
    }
}
