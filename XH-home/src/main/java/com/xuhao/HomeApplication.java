package com.xuhao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.xuhao.mapper")
public class HomeApplication{
    public static void main(String[] args){
        SpringApplication.run(HomeApplication.class, args);
//        System.out.println("hello");
    }
}
