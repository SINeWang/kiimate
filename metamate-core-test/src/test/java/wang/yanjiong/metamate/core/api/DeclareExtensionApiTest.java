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
    private DeclareExtensionApi declareExtensionApi;

    @Autowired
    private ExtensionDai extensionDai;

    private String ownerId = "testOwnerId";

    private String operatorId = "testOperatorId";

    @Test
    public void testGroupOnly() {
        DeclareExtensionApi.ExtensionForm extensionForm = new DeclareExtensionApi.ExtensionForm();
        extensionForm.setGroup("testGroup");
        ResponseEntity<DeclareExtensionApi.ExtensionReceipt> response = declareExtensionApi.declareByFormUrlEncoded(extensionForm, ownerId, operatorId);
        DeclareExtensionApi.ExtensionReceipt extensionReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(extensionReceipt);
    }

    @Test
    public void testNameOnly() {
        DeclareExtensionApi.ExtensionForm extensionForm = new DeclareExtensionApi.ExtensionForm();
        extensionForm.setName("testName");
        ResponseEntity<DeclareExtensionApi.ExtensionReceipt> response = declareExtensionApi.declareByFormUrlEncoded(extensionForm, ownerId, operatorId);
        DeclareExtensionApi.ExtensionReceipt extensionReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(extensionReceipt);
    }

    @Test
    public void testVersionOnly() {
        DeclareExtensionApi.ExtensionForm extensionForm = new DeclareExtensionApi.ExtensionForm();
        extensionForm.setTree("testTree");
        ResponseEntity<DeclareExtensionApi.ExtensionReceipt> response = declareExtensionApi.declareByFormUrlEncoded(extensionForm, ownerId, operatorId);
        DeclareExtensionApi.ExtensionReceipt extensionReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(extensionReceipt);
    }

    @Test
    public void testVisibilityOnly() {
        DeclareExtensionApi.ExtensionForm extensionForm = new DeclareExtensionApi.ExtensionForm();
        extensionForm.setVisibility("testVisibility");
        ResponseEntity<DeclareExtensionApi.ExtensionReceipt> response = declareExtensionApi.declareByFormUrlEncoded(extensionForm, ownerId, operatorId);
        DeclareExtensionApi.ExtensionReceipt extensionReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(extensionReceipt);
    }

    @Test
    public void testStructureOnly() {
        DeclareExtensionApi.ExtensionForm extensionForm = new DeclareExtensionApi.ExtensionForm();
        extensionForm.setStructure("testStructure");
        ResponseEntity<DeclareExtensionApi.ExtensionReceipt> response = declareExtensionApi.declareByFormUrlEncoded(extensionForm, ownerId, operatorId);
        DeclareExtensionApi.ExtensionReceipt extensionReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(extensionReceipt);
    }


    @Test
    public void testInvalidStructure() {
        DeclareExtensionApi.ExtensionForm extensionForm = new DeclareExtensionApi.ExtensionForm();
        extensionForm.setGroup("testGroup");
        extensionForm.setName("testName");
        extensionForm.setTree("testTree");
        extensionForm.setVisibility("protected");
        extensionForm.setStructure("testStructure");
        ResponseEntity<DeclareExtensionApi.ExtensionReceipt> response = declareExtensionApi.declareByFormUrlEncoded(extensionForm, ownerId, operatorId);
        DeclareExtensionApi.ExtensionReceipt extensionReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(extensionReceipt);
    }

    @Test
    public void testInvalidVisibility() {
        DeclareExtensionApi.ExtensionForm extensionForm = new DeclareExtensionApi.ExtensionForm();
        extensionForm.setGroup("testGroup");
        extensionForm.setName("testName");
        extensionForm.setTree("testTree");
        extensionForm.setVisibility("testVisibility");
        extensionForm.setStructure("complex");
        ResponseEntity<DeclareExtensionApi.ExtensionReceipt> response = declareExtensionApi.declareByFormUrlEncoded(extensionForm, ownerId, operatorId);
        DeclareExtensionApi.ExtensionReceipt extensionReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(extensionReceipt);
    }

    @Test
    public void testSave() {
        String group = "testGroup";
        String name = "testName";
        String tree = "testTree";

        DeclareExtensionApi.ExtensionForm extensionForm = new DeclareExtensionApi.ExtensionForm();
        extensionForm.setGroup(group);
        extensionForm.setName(name);
        extensionForm.setTree(tree);
        extensionForm.setVisibility("protected");
        extensionForm.setStructure("complex");
        ResponseEntity<DeclareExtensionApi.ExtensionReceipt> response = declareExtensionApi.declareByFormUrlEncoded(extensionForm, ownerId, operatorId);
        DeclareExtensionApi.ExtensionReceipt extensionReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.ACCEPTED.value(), httpStatus.value());
        Assert.assertNotNull(extensionReceipt);
        String id = extensionReceipt.getId();
        Assert.assertNotNull(id);


        ExtensionDai.Extension extension = extensionDai.selectExtensionById(id);
        Assert.assertEquals(group, extension.getGroup());
        Assert.assertEquals(name, extension.getName());
        Assert.assertEquals(tree, extension.getTree());

        extensionDai.deleteExtensionById(id);
    }
}
