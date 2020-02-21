package com.edu.manage_course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Administrator
 * @version 1.0
 **/
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.edu.framework.domain.course")//扫描实体类
@ComponentScan(basePackages={"com.edu.api"})//扫描接口
@ComponentScan(basePackages={"com.edu.manage_course"})//扫描自身
@ComponentScan(basePackages={"com.edu.framework"})//扫描common下的所有类
public class ManageCourseApplication {
    public static void main(String[] args){
        SpringApplication.run(ManageCourseApplication.class, args);
    }
}
