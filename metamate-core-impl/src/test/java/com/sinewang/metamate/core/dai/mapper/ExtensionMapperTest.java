package com.sinewang.metamate.core.dai.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.yanjiong.metamate.core.model.Extension;

/**
 * Created by WangYanJiong on 3/23/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@SpringBootConfiguration
@MapperScan("com.sinewang.metamate.core.dai.mapper")
public class ExtensionMapperTest {

    @Autowired
    private ExtensionMapper extensionMapper;


    @Test
    public void insertExtension() {
        extensionMapper.insertExtension("12", "34", "56", "78", "90", "ab");
    }

    @Test
    public void selectExtensionById() {
        Extension ext = extensionMapper.selectExtensionById("12");
        Assert.assertNotNull(ext);
    }
}
