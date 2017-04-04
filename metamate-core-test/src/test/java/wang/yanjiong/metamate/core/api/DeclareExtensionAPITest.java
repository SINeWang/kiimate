package wang.yanjiong.metamate.core.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yanjiong.metamate.core.dai.ExtensionDai;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.metamate.core")
@SpringBootTest(classes = {DeclareExtensionApiTest.class})
public class DeclareExtensionApiTest {

    @Autowired
    private SetExtensionApi setExtensionApi;

    @Autowired
    private ExtensionDai extensionDai;

    private String ownerId = "testOwnerId";

    private String operatorId = "testOperatorId";

    @Test
    public void testGroupOnly() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setGroup("testGroup");
        ResponseEntity<SetExtensionApi.Receipt> response = setExtensionApi.declareExtensionViaFormUrlEncoded(form, ownerId, operatorId);
        SetExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(receipt);
    }

    @Test
    public void testNameOnly() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setName("testName");
        ResponseEntity<SetExtensionApi.Receipt> response = setExtensionApi.declareExtensionViaFormUrlEncoded(form, ownerId, operatorId);
        SetExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(receipt);
    }

    @Test
    public void testVersionOnly() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setTree("testTree");
        ResponseEntity<SetExtensionApi.Receipt> response = setExtensionApi.declareExtensionViaFormUrlEncoded(form, ownerId, operatorId);
        SetExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(receipt);
    }

    @Test
    public void testVisibilityOnly() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setVisibility("testVisibility");
        ResponseEntity<SetExtensionApi.Receipt> response = setExtensionApi.declareExtensionViaFormUrlEncoded(form, ownerId, operatorId);
        SetExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(receipt);
    }

    @Test
    public void testStructureOnly() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setStructure("testStructure");
        ResponseEntity<SetExtensionApi.Receipt> response = setExtensionApi.declareExtensionViaFormUrlEncoded(form, ownerId, operatorId);
        SetExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(receipt);
    }


    @Test
    public void testInvalidStructure() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setGroup("testGroup");
        form.setName("testName");
        form.setTree("testTree");
        form.setVisibility("protected");
        form.setStructure("testStructure");
        ResponseEntity<SetExtensionApi.Receipt> response = setExtensionApi.declareExtensionViaFormUrlEncoded(form, ownerId, operatorId);
        SetExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(receipt);
    }

    @Test
    public void testInvalidVisibility() {
        SetExtensionApi.Form form = new SetExtensionApi.Form();
        form.setGroup("testGroup");
        form.setName("testName");
        form.setTree("testTree");
        form.setVisibility("testVisibility");
        form.setStructure("complex");
        ResponseEntity<SetExtensionApi.Receipt> response = setExtensionApi.declareExtensionViaFormUrlEncoded(form, ownerId, operatorId);
        SetExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(receipt);
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
        ResponseEntity<SetExtensionApi.Receipt> response = setExtensionApi.declareExtensionViaFormUrlEncoded(form, ownerId, operatorId);
        SetExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.ACCEPTED.value(), httpStatus.value());
        Assert.assertNotNull(receipt);
        String id = receipt.getId();
        Assert.assertNotNull(id);


        ExtensionDai.Extension extension = extensionDai.selectExtensionById(id);
        Assert.assertEquals(group, extension.getGroup());
        Assert.assertEquals(name, extension.getName());
        Assert.assertEquals(tree, extension.getTree());

        extensionDai.deleteExtensionById(id);
    }
}
