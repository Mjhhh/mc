package com.edu.message;

import com.edu.message.netty.WSServer;
import com.edu.message.utils.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.edu.message.dao")
@EntityScan("com.edu.framework.domain.message")//扫描实体类
@ComponentScan(basePackages={"com.edu.api"})//扫描接口
@ComponentScan(basePackages={"com.edu.framework"})//扫描common下的所有类
@SpringBootApplication
public class ManageMessageApplication {
    public static void main(String[] args){
        SpringApplication.run(ManageMessageApplication.class, args);
        WSServer.getInstance().start();

    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

    @Bean
    public SpringUtil getSpringUtil(){
        return new SpringUtil();
    }
}