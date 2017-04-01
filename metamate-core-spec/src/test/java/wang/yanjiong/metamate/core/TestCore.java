package wang.yanjiong.metamate.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yanjiong.metamate.core.api.CreateExtensionAPITest;
import wang.yanjiong.metamate.core.dai.ExtensionDaiTest;

/**
 * Created by WangYanJiong on 30/03/2017.
 */

@RunWith(Suite.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@Suite.SuiteClasses({
        CreateExtensionAPITest.class,
        ExtensionDaiTest.class
})
@ComponentScan(basePackages = {"com.sinewang.metamate.core"})
public class TestCore {
}
