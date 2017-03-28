package wang.yanjiong.metamate.core.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yanjiong.magnet.xi.boundary.Summary;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
public class CreateExtensionAPITest {

    @Autowired
    private CreateExtensionApi createExtensionApi;

    @Test
    public void testGroupOnly() {
        CreateExtensionApi.Form form = new CreateExtensionApi.Form();
        form.setGroup("testGroup");
        CreateExtensionApi.Receipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(receipt.getSummary().getStatus(), Summary.Status.REJECTED);
    }

    @Test
    public void testNameOnly() {
        CreateExtensionApi.Form form = new CreateExtensionApi.Form();
        form.setName("testName");
        CreateExtensionApi.Receipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(receipt.getSummary().getStatus(), Summary.Status.REJECTED);
    }

    @Test
    public void testVersionOnly() {
        CreateExtensionApi.Form form = new CreateExtensionApi.Form();
        form.setVersion("testVersion");
        CreateExtensionApi.Receipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(receipt.getSummary().getStatus(), Summary.Status.REJECTED);
    }

    @Test
    public void testVisibilityOnly() {
        CreateExtensionApi.Form form = new CreateExtensionApi.Form();
        form.setVisibility("testVisibility");
        CreateExtensionApi.Receipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(receipt.getSummary().getStatus(), Summary.Status.REJECTED);
    }

    @Test
    public void testStructureOnly() {
        CreateExtensionApi.Form form = new CreateExtensionApi.Form();
        form.setStructure("testStructure");
        CreateExtensionApi.Receipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
        Assert.assertNotNull(receipt);
        Assert.assertEquals(receipt.getSummary().getStatus(), Summary.Status.REJECTED);
    }
}
