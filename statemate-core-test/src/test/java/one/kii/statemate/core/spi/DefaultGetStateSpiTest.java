package one.kii.statemate.core.spi;

import one.kii.summer.bound.Receipt;
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
import wang.yanjiong.statemate.core.spi.GetStateSpi;

/**
 * Created by WangYanJiong on 02/04/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = "com.sinewang")
@SpringBootTest(classes = {DefaultGetStateSpiTest.class})
public class DefaultGetStateSpiTest {

    @Autowired
    private GetStateSpi getStateSpi;

    @Test
    public void test() {
        Receipt<GetStateSpi.State> receipt = getStateSpi.getLatestState("testOwnerId", "testGroup", "testName", "testTag", GetStateSpi.State.class);

        GetStateSpi.State state = receipt.getBody();

        Assert.assertNotNull(state);


    }gi
}
