package com.edu.manage_media_process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@EntityScan("com.edu.framework.domain.media")//扫描实体类
@ComponentScan(basePackages={"com.edu.api"})//扫描接口
@ComponentScan(basePackages={"com.edu.manage_media_process"})//扫描本项目下的所有类
@ComponentScan(basePackages={"com.edu.framework"})//扫描common下的所有类
public class
ManageMediaProcessorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageMediaProcessorApplication.class, args);
    }
}
