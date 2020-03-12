package com.edu.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("com.edu.framework.domain.search")//扫描实体类
@ComponentScan(basePackages={"com.edu.api"})//扫描接口
@ComponentScan(basePackages={"com.edu.search"})//扫描本项目下的所有类
@ComponentScan(basePackages={"com.edu.framework"})//扫描common下的所有类
public class SearchApplication {

    public static void main(String[] args){
        SpringApplication.run(SearchApplication.class, args);
    }

}
