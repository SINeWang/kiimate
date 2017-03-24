package com.sinewang.metamate.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Core {
    public static void main(String[] args) {
        SpringApplication.run(Core.class, args);
    }

}
