package com.mooc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mooc.mapper")
public class SellApplication {
    public static void main(String[] args) {
        SpringApplication.run(SellApplication.class, args);
    }

}
