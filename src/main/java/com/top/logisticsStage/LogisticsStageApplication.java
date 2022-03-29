package com.top.logisticsStage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.top.logistics.service.mapper.*Impl")
@SpringBootApplication
public class LogisticsStageApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsStageApplication.class, args);
    }

}
