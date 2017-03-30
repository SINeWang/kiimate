package com.sinewang.metamate.core.dai.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yanjiong.metamate.core.dai.ExtensionDai;

import java.util.Date;

/**
 * Created by WangYanJiong on 3/23/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan(basePackages = {"com.sinewang.metamate.core"})
@SpringBootTest(classes = {ExtensionMapperTest.class})
public class ExtensionMapperTest {

    @Autowired
    private ExtensionMapper extensionMapper;

    @Test
    public void testCRUD() {
        String id = "12";
        extensionMapper.deleteExtensionById(id);

        ExtensionDai.Extension ext = extensionMapper.selectExtensionById("12");
        Assert.assertNull(ext);

        extensionMapper.insertExtension(id, "12", "56", "78", "90", "ab", new Date());
        ExtensionDai.Extension ext1 = extensionMapper.selectExtensionById(id);
        Assert.assertNotNull(ext1);

        extensionMapper.deleteExtensionById("12");
        Assert.assertNull(ext);
    }

}
