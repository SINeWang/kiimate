package wang.yanjiong.metamate.core.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yanjiong.magnet.xi.boundary.Summary;
import wang.yanjiong.metamate.core.dai.ExtensionDai;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
public class CreateExtensionAPITest {

    @Autowired
    private CreateExtensionApi createExtensionApi;

    @Autowired
    private ExtensionDai extensionDai;

    @Test
    public void testGroupOnly() {
        CreateExtensionApi.Form form = new CreateExtensionApi.Form();
        form.setGroup("testGroup");
        CreateExtensionApi.Receipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }

    @Test
    public void testNameOnly() {
        CreateExtensionApi.Form form = new CreateExtensionApi.Form();
        form.setName("testName");
        CreateExtensionApi.Receipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }

    @Test
    public void testVersionOnly() {
        CreateExtensionApi.Form form = new CreateExtensionApi.Form();
        form.setVersion("testVersion");
        CreateExtensionApi.Receipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }

    @Test
    public void testVisibilityOnly() {
        CreateExtensionApi.Form form = new CreateExtensionApi.Form();
        form.setVisibility("testVisibility");
        CreateExtensionApi.Receipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }

    @Test
    public void testStructureOnly() {
        CreateExtensionApi.Form form = new CreateExtensionApi.Form();
        form.setStructure("testStructure");
        CreateExtensionApi.Receipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }


    @Test
    public void testInvalidStructure() {
        CreateExtensionApi.Form form = new CreateExtensionApi.Form();
        form.setGroup("testGroup");
        form.setName("testName");
        form.setVersion("testVersion");
        form.setVisibility("protected");
        form.setStructure("testStructure");
        CreateExtensionApi.Receipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }

    @Test
    public void testInvalidVisibility() {
        CreateExtensionApi.Form form = new CreateExtensionApi.Form();
        form.setGroup("testGroup");
        form.setName("testName");
        form.setVersion("testVersion");
        form.setVisibility("testVisibility");
        form.setStructure("complex");
        CreateExtensionApi.Receipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.REJECTED, receipt.getSummary().getStatus());
    }

    @Test
    public void testSave() {
        String group = "testGroup";
        String name = "testName";
        String version = "testVersion";

        CreateExtensionApi.Form form = new CreateExtensionApi.Form();
        form.setGroup(group);
        form.setName(name);
        form.setVersion(version);
        form.setVisibility("protected");
        form.setStructure("complex");
        CreateExtensionApi.Receipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(Summary.Status.ACCEPTED, receipt.getSummary().getStatus());
        String id = receipt.getId();
        Assert.assertNotNull(id);


        ExtensionDai.Extension extension = extensionDai.selectExtensionById(id);
        Assert.assertEquals(group, extension.getGroup());
        Assert.assertEquals(name, extension.getName());
        Assert.assertEquals(version, extension.getVersion());
    }
}
