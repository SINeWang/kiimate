package wang.yanjiong.metamate.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;
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
public class TestCore {
}
