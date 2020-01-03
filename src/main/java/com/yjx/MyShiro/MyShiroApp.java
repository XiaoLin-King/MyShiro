package com.yjx.MyShiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan(value = "com.yjx.MyShiro.dao")
public class MyShiroApp {
    public static void main(String[] args) {
        SpringApplication.run(MyShiroApp.class, args);
    }
}
