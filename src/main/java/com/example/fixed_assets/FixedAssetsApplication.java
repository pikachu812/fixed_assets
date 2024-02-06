package com.example.fixed_assets;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.fixed_assets.dao")
public class FixedAssetsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FixedAssetsApplication.class, args);
    }

}
