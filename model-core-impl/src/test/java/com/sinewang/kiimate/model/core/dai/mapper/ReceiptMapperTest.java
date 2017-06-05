package com.sinewang.kiimate.model.core.dai.mapper;

import one.kii.kiimate.model.core.dai.ExtensionDai;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by WangYanJiong on 3/23/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@SpringBootTest(classes = {ReceiptMapperTest.class})
@EnableAutoConfiguration
public class ReceiptMapperTest {

    @Autowired
    private ExtensionMapper extensionMapper;

    @Test
    public void testCRUD() {
        long id = 12;

        String ownerId = "testOwnerId";
        extensionMapper.deleteExtensionById(id);

        ExtensionDai.Record ext = extensionMapper.selectLatestExtensionById(id);
        Assert.assertNull(ext);

        extensionMapper.insertExtension(id, "sfsfsdf", ownerId, "12", "56", "78", "90", "somebody", new Date());
        ExtensionDai.Record ext1 = extensionMapper.selectLatestExtensionById(id);
        Assert.assertNotNull(ext1);

        extensionMapper.deleteExtensionById(id);
        Assert.assertNull(ext);
    }

}
