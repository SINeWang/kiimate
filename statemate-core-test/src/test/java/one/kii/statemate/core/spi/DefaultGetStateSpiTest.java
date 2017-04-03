package one.kii.statemate.core.spi;

import one.kii.summer.annot.SummerSpi;
import one.kii.summer.bound.Receipt;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yanjiong.statemate.core.spi.GetStateSpi;

/**
 * Created by WangYanJiong on 02/04/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@Configuration
@ComponentScan(basePackages = "com.sinewang", includeFilters = @ComponentScan.Filter(
        classes = SummerSpi.class,
        type = FilterType.ANNOTATION
))
@SpringBootTest(classes = {DefaultGetStateSpiTest.class})
public class DefaultGetStateSpiTest {

    @Autowired
    private GetStateSpi getStateSpi;

    @Test
    public void test() {
        Receipt<GetStateSpi.State> receipt = getStateSpi.getLatestState("testOwnerId", "testGroup", "testName", "testTag", GetStateSpi.State.class);

        GetStateSpi.State state = receipt.getBody();

        Assert.assertNotNull(state);


    }
}
