package one.kii.kiimate.model.core;

import one.kii.kiimate.model.core.api.TestDeclareExtensionApi;
import one.kii.kiimate.model.core.dai.TestExtensionDai;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@RunWith(value = Suite.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.kiimate.model.core")
@Suite.SuiteClasses({
        TestDeclareExtensionApi.class,
        TestExtensionDai.class})
public class TestCore {
}
