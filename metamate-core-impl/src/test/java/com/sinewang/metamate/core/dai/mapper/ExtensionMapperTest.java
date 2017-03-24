package com.sinewang.metamate.core.dai.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yanjiong.metamate.core.model.Extension;

/**
 * Created by WangYanJiong on 3/23/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
public class ExtensionMapperTest {

    @Autowired
    private ExtensionMapper extensionMapper;

    @Test
    public void insertExtension() {
        String id = "12";
        extensionMapper.deleteExtensionById(id);

        extensionMapper.insertExtension(id, "34", "56", "78", "90", "ab");
        Extension ext = extensionMapper.selectExtensionById(id);
        Assert.assertNotNull(ext);
    }

    @Test
    public void selectExtensionById() {
        Extension ext = extensionMapper.selectExtensionById("12");
        Assert.assertNotNull(ext);
    }
}
