package com.sinewang.metamate.core;

import com.sinewang.metamate.core.dai.DefaultExtensionDaiTest;
import com.sinewang.metamate.core.dai.mapper.ExtensionMapperTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;

/**
 * Created by WangYanJiong on 3/24/17.
 */

@Suite.SuiteClasses({
        DefaultExtensionDaiTest.class,
        ExtensionMapperTest.class}
)
@RunWith(Suite.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
public class DefaultTestSuite {
}
