package com.edu.manage_cms.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Configuration
public class RabbitMQConfig {
    /**
     * 交换机的名称
     */
    public static final String EX_ROUTING_CMS_EDITPAGE = "ex_routing_cms_editpage";

    /**
     * 交换机配置使用direct类型
     *
     * @return the exchange
     */
    @Bean(EX_ROUTING_CMS_EDITPAGE)
    public Exchange EXCHANGE_TOPICS_INFORM() {
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_EDITPAGE).durable(true).build();
    }

}
