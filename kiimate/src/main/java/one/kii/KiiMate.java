package one.kii;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by WangYanJiong on 05/05/2017.
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan({
        "com.sinewang.kiimate.subject.core",
        "one.kii.kiimate.subject.core",
        "com.sinewang.kiimate.model.core",
        "one.kii.kiimate.model.core",
        "com.sinewang.kiimate.status.core",
        "one.kii.kiimate.status.core"})
@MapperScan({
        "com.sinewang.kiimate.model.core",
        "com.sinewang.kiimate.status.core"})
public class KiiMate {
    public static void main(String[] args) {
        SpringApplication.run(KiiMate.class, args);
    }
}
