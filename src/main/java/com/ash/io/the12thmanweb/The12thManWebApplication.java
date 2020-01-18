package com.ash.io.the12thmanweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 扫描 mapper类
 */
@SpringBootApplication
@MapperScan("com.ash.io.the12thmanweb.mapper")
public class The12thManWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(The12thManWebApplication.class, args);
    }

}
