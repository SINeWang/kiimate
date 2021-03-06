package one.kii.kiimate.model.cases.spi;

import one.kii.summer.io.exception.*;
import one.kii.txdid.txd64.T1Did64Generator;
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

    private static final T1Did64Generator idgen = new T1Did64Generator(0);


    @Test
    public void test() {
//        SubscribeModelsSpi.Form form = new SubscribeModelsSpi.Form();
//        form.setSubscriberId("wangyj");
//        form.setGroup("test-token");
//        form.setName("default");
//        form.setSet(idgen.born());
//        SubscribeModelsSpi.Receipt receipt = null;
//        try {
//            receipt = subscribeModelsSpi.commit(form);
//        } catch (Panic | BadRequest | Forbidden | Conflict | NotFound panic) {
//            panic.printStackTrace();
//        }
//        Assert.assertNotNull(receipt);
//
//
//        try {
//            receipt = subscribeModelsSpi.commit(form);
//        } catch (Conflict conflict) {
//            Assert.assertNotNull(conflict);
//        } catch (Panic | Forbidden | NotFound | BadRequest oops) {
//            oops.printStackTrace();
//        }
    }
}
