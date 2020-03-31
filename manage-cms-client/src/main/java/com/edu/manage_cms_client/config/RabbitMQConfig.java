package com.edu.manage_cms_client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Configuration
public class RabbitMQConfig {
    /**
     * 队列bean的名称
     */
    public static final String QUEUE_CMS_EDITPAGE = "queue_cms_editpage";

    /**
     * 交换机的名称
     */
    public static final String EX_ROUTING_CMS_EDITPAGE = "ex_routing_cms_editpage";

    /**
     * 队列的名称
     */
    @Value("${mq.queue}")
    public String queue_cms_editpage;

    /**
     * routingKey即站点Id
     */
    @Value("${mq.routingKey}")
    public String routingKey;

    /**
     * 交换机配置使用direct类型
     *
     * @return the exchange
     */
    @Bean(EX_ROUTING_CMS_EDITPAGE)
    public Exchange EXCHANGE_TOPICS_INFORM() {
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_EDITPAGE).durable(true).build();
    }

    /**
     * 声明队列
     * @return
     */
    @Bean(QUEUE_CMS_EDITPAGE)
    public Queue QUEUE_CMS_POSTPAGE() {
        Queue queue = new Queue(queue_cms_editpage);
        return queue;
    }

    /**
     * 绑定队列到交换机
     *
     * @param queue    the queue
     * @param exchange the exchange
     * @return the binding
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(QUEUE_CMS_EDITPAGE) Queue queue,
                                            @Qualifier(EX_ROUTING_CMS_EDITPAGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }
}
