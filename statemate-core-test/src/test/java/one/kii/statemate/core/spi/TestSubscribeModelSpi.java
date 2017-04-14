package one.kii.statemate.core.spi;

import one.kii.summer.context.exception.Panic;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by WangYanJiong on 09/04/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.statemate")
@SpringBootTest(classes = {TestSubscribeModelSpi.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestSubscribeModelSpi {


    @Autowired
    private SubscribeModelSpi subscribeModelSpi;

    @Test
    public void test(){
        SubscribeModelSpi.Form form  = new SubscribeModelSpi.Form();
        form.setSubscriberId("wangyj");
        form.setGroup("testSubMultiValueGroup");
        form.setName("default");
        form.setPubSetHash("1ef3725c9ddc304a6489d35918637f85fb45415c85857f9e48d0385585e710db");
        SubscribeModelSpi.Receipt receipt = null;
        try {
            receipt = subscribeModelSpi.subscribe(form);
        } catch (Panic panic) {
            panic.printStackTrace();
        }
        Assert.assertNotNull(receipt);
    }
}
