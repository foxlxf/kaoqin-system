package com.qiuzhiguo.springdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@MapperScan("com.qiuzhiguo.springdemo.mapper")
public class SpringDemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringDemoApplication.class, args);
    }

}
