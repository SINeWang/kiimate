package com.sinewang.metamate.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by WangYanJiong on 3/24/17.
 */

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"com.sinewang.metamate", "wang.yanjiong.metamate"})
public class DefaultCore {

    public static void main(String[] args) {
        SpringApplication.run(DefaultCore.class, args);
    }
}
