package one.kii;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static one.kii.KiiMate.IMPL_PACKAGE;
import static one.kii.KiiMate.SPEC_PACKAGE;

/**
 * Created by WangYanJiong on 05/05/2017.
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan({
        SPEC_PACKAGE,
        IMPL_PACKAGE
})
@MapperScan({
        IMPL_PACKAGE
})
public class KiiMate {


    public final static String SPEC_PACKAGE = "one.kiimate";

    public final static String IMPL_PACKAGE = "com.sinewang";

    public static void main(String[] args) {
        SpringApplication.run(KiiMate.class, args);
    }
}
