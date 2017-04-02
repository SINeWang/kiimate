package wang.yanjiong.metamate.core.api;

import one.kii.summer.bound.Summary;
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

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.metamate.core")
@SpringBootTest(classes = {DeclareExtensionAPITest.class})
public class DeclareExtensionAPITest {

    @Autowired
    private SetExtensionApi setExtensionApi;

    @Autowired
    private ExtensionDai extensionDai;

    @Test
    public void testGroupOnly() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setGroup("testGroup");
        SetExtensionApi.Receipt receipt = setExtensionApi.declareExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }

    @Test
    public void testNameOnly() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setName("testName");
        SetExtensionApi.Receipt receipt = setExtensionApi.declareExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }

    @Test
    public void testVersionOnly() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setTree("testTree");
        SetExtensionApi.Receipt receipt = setExtensionApi.declareExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }

    @Test
    public void testVisibilityOnly() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setVisibility("testVisibility");
        SetExtensionApi.Receipt receipt = setExtensionApi.declareExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }

    @Test
    public void testStructureOnly() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setStructure("testStructure");
        SetExtensionApi.Receipt receipt = setExtensionApi.declareExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }


    @Test
    public void testInvalidStructure() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setGroup("testGroup");
        form.setName("testName");
        form.setTree("testTree");
        form.setVisibility("protected");
        form.setStructure("testStructure");
        SetExtensionApi.Receipt receipt = setExtensionApi.declareExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }

    @Test
    public void testInvalidVisibility() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setGroup("testGroup");
        form.setName("testName");
        form.setTree("testTree");
        form.setVisibility("testVisibility");
        form.setStructure("complex");
        SetExtensionApi.Receipt receipt = setExtensionApi.declareExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }

    @Test
    public void testSave() {
        String group = "testGroup";
        String name = "testName";
        String tree = "testTree";

        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setGroup(group);
        form.setName(name);
        form.setTree(tree);
        form.setVisibility("protected");
        form.setStructure("complex");
        SetExtensionApi.Receipt receipt = setExtensionApi.declareExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.ACCEPTED, receipt.getSummary().getStatus());
        String id = receipt.getId();
        Assert.assertNotNull(id);


        ExtensionDai.Extension extension = extensionDai.selectExtensionById(id);
        Assert.assertEquals(group, extension.getGroup());
        Assert.assertEquals(name, extension.getName());
        Assert.assertEquals(tree, extension.getTree());

        extensionDai.deleteExtensionById(id);
    }
}
