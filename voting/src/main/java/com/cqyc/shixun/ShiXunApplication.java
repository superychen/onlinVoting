package com.cqyc.shixun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @Author:
 * @Date:
 */
@SpringBootApplication
@MapperScan("com.cqyc.shixun.mapper")
public class ShiXunApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiXunApplication.class);
    }
}
