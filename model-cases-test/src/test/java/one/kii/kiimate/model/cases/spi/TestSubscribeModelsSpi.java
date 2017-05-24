package one.kii.kiimate.model.cases.spi;

import one.kii.summer.io.exception.*;
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
@ComponentScan("com.sinewang.kiimate.model")
@SpringBootTest(classes = {TestSubscribeModelsSpi.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestSubscribeModelsSpi {


    @Autowired
    private SubscribeModelsSpi subscribeModelsSpi;

    @Test
    public void test() {
        SubscribeModelsSpi.Form form = new SubscribeModelsSpi.Form();
        form.setSubscriberId("wangyj");
        form.setGroup("testSubMultiValueGroup");
        form.setName("default");
        form.setPubSet("33df86613e40cadbb399e9e4ef478108fd1a7b17b5b325cc8ca809acaf2c1850");
        SubscribeModelsSpi.Receipt receipt = null;
        try {
            receipt = subscribeModelsSpi.commit(form);
        } catch (Panic | BadRequest | Forbidden | Conflict | NotFound panic) {
            panic.printStackTrace();
        }
        Assert.assertNotNull(receipt);
    }
}
