package one.kii.kiimate.model.core.dai;

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
@SpringBootTest(classes = {TestExtensionDai.class})
public class TestExtensionDai {

    String testId = "testId";
    String testOwnerId = "testOwnerId";
    String testGroup = "testGroup";
    String testName = "testName";
    String testTree = "testTree";
    String testStructure = "testStructure";
    String testVisibility = "testVisibility";
    @Autowired
    private ExtensionDai extensionDai;

    @Before
    public void setup() {
        extensionDai.deleteExtensionById(testId);
    }

    @Test(expected = NullPointerException.class)
    public void testNull() {
        extensionDai.selectExtensionById(null);
    }

    @Test(expected = NullPointerException.class)
    public void testEmpty() {
        extensionDai.selectExtensionById("");
    }

    @Test
    public void testFirstInsert() {
        extensionDai.deleteExtensionById(testId);

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

        ExtensionDai.Extension dbExtension = extensionDai.selectExtensionById(testId);

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
        extensionDai.deleteExtensionById(testId);
    }

}
