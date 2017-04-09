package one.kii.statemate.core.spi;

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
        form.setGroup("testSubGroup");
        form.setPubSetHash("5c3c9d5c8cef9b2a6764423e3022f761b104ca5800d783a1e98cb4af67f670c1");
        SubscribeModelSpi.Receipt receipt =  subscribeModelSpi.subscribe(form);
        Assert.assertNotNull(receipt);
    }
}
