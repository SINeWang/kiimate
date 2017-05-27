package one.kii.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ExtensionMapper;
import one.kii.summer.io.exception.NotFound;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.kiimate.model")
@SpringBootTest(classes = {TestReceiptDai.class})
public class TestReceiptDai {

    String testId = "testId";
    String testOwnerId = "testOwnerId";
    String testGroup = "testGroup";
    String testName = "testName";
    String testTree = "testTree";
    String testStructure = "testStructure";
    String testVisibility = "testVisibility";
    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private ExtensionMapper extensionMapper;

    @Before
    public void setup() {
        extensionMapper.deleteExtensionById(testId);
    }

    @Test(expected = NotFound.class)
    public void testNull() throws NotFound {
        extensionDai.loadExtension(null);
    }


    @Test
    public void testFirstInsert() {
        extensionMapper.deleteExtensionById(testId);

        ExtensionDai.Extension extension = new ExtensionDai.Extension();
        extension.setGroup(testGroup);
        extension.setOwnerId(testOwnerId);
        extension.setName(testName);
        extension.setTree(testTree);
        extension.setVisibility(testVisibility);
        extension.setId(testId);
        try {
            extensionDai.insertExtension(extension);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            //ignore
        }
        ExtensionDai.ChannelId extId = new ExtensionDai.ChannelId();
        extId.setId(testId);

        ExtensionDai.Extension dbExtension = null;
        try {
            dbExtension = extensionDai.loadExtension(extId);
        } catch (NotFound notFound) {
        }

        Assert.assertEquals(dbExtension.getId(), testId);
        Assert.assertEquals(dbExtension.getOwnerId(), testOwnerId);
        Assert.assertEquals(dbExtension.getGroup(), testGroup);
        Assert.assertEquals(dbExtension.getName(), testName);
        Assert.assertEquals(dbExtension.getTree(), testTree);
        Assert.assertEquals(dbExtension.getVisibility(), testVisibility);
    }

    @Test()
    public void testSecondInsert() {
        ExtensionDai.Extension extension = new ExtensionDai.Extension();
        extension.setGroup(testGroup);
        extension.setOwnerId(testOwnerId);
        extension.setName(testName);
        extension.setTree(testTree);
        extension.setVisibility(testVisibility);
        extension.setId(testId);
        try {
            extensionDai.insertExtension(extension);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            extensionDuplicated.printStackTrace();
        }
    }

    @After
    public void cleanUp() {
        extensionMapper.deleteExtensionById(testId);
    }

}
