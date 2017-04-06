package wang.yanjiong.metamate.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;
import wang.yanjiong.metamate.core.api.*;
import wang.yanjiong.metamate.core.dai.TestExtensionDai;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@RunWith(value = Suite.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.metamate.core")
@Suite.SuiteClasses({
        TestDeclareExtensionApi.class,
        TestDeclareIntensionApi.class,
        TestExtensionDai.class,
        TestSaveInstanceApi.class,
        TestSnapshotModelApi.class,
        TestSubscribeModelApi.class
})
public class TestCore {
}
