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

    long testId = 11111;
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

    @Test
    public void testFirstInsert() {
        extensionMapper.deleteExtensionById(testId);

        ExtensionDai.Record record = new ExtensionDai.Record();
        record.setGroup(testGroup);
        record.setOwnerId(testOwnerId);
        record.setName(testName);
        record.setTree(testTree);
        record.setVisibility(testVisibility);
        record.setId(testId);
        try {
            extensionDai.remember(record);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            //ignore
        }
        ExtensionDai.ChannelId extId = new ExtensionDai.ChannelId();
        extId.setId(testId);

        ExtensionDai.Record dbRecord = null;
        try {
            dbRecord = extensionDai.loadLast(extId);
        } catch (NotFound notFound) {
        }

        Assert.assertEquals(dbRecord.getId(), testId);
        Assert.assertEquals(dbRecord.getOwnerId(), testOwnerId);
        Assert.assertEquals(dbRecord.getGroup(), testGroup);
        Assert.assertEquals(dbRecord.getName(), testName);
        Assert.assertEquals(dbRecord.getTree(), testTree);
        Assert.assertEquals(dbRecord.getVisibility(), testVisibility);
    }

    @Test()
    public void testSecondInsert() {
        ExtensionDai.Record record = new ExtensionDai.Record();
        record.setGroup(testGroup);
        record.setOwnerId(testOwnerId);
        record.setName(testName);
        record.setTree(testTree);
        record.setVisibility(testVisibility);
        record.setId(testId);
        try {
            extensionDai.remember(record);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            extensionDuplicated.printStackTrace();
        }
    }

    @After
    public void cleanUp() {
        extensionMapper.deleteExtensionById(testId);
    }

}
