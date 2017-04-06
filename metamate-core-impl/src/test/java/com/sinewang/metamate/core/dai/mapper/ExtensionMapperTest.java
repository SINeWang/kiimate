package com.sinewang.metamate.core.dai.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yanjiong.metamate.core.dai.ExtensionDai;

import java.util.Date;

/**
 * Created by WangYanJiong on 3/23/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@SpringBootTest(classes = {ExtensionMapperTest.class})
@EnableAutoConfiguration
public class ExtensionMapperTest {

    @Autowired
    private ExtensionMapper extensionMapper;

    @Test
    public void testCRUD() {
        String id = "12";

        String ownerId = "testOwnerId";
        extensionMapper.deleteExtensionById(id);

        ExtensionDai.Extension ext = extensionMapper.selectExtensionById("12");
        Assert.assertNull(ext);

        extensionMapper.insertExtension(id, ownerId, "12", "56", "78", "90", new Date());
        ExtensionDai.Extension ext1 = extensionMapper.selectExtensionById(id);
        Assert.assertNotNull(ext1);

        extensionMapper.deleteExtensionById("12");
        Assert.assertNull(ext);
    }

}
