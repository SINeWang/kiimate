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
import wang.yanjiong.metamate.core.fi.AnVisibilityValidator;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.metamate.core")
@SpringBootTest(classes = {TestDeclareExtensionApi.class})
public class TestDeclareExtensionApi {

    @Autowired
    private DeclareExtensionApi declareExtensionApi;

    @Autowired
    private ExtensionDai extensionDai;

    private String ownerId = "testOwnerId";

    private String operatorId = "testOperatorId";

    private String requestId = "requestId";

    @Test
    public void testGroupOnly() {
        DeclareExtensionApi.Form form = new DeclareExtensionApi.Form();
        form.setGroup("testGroup");
        ResponseEntity<DeclareExtensionApi.Receipt> response = declareExtensionApi.declareExtension(requestId, ownerId, operatorId, form);
        DeclareExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(receipt);
    }

    @Test
    public void testNameOnly() {
        DeclareExtensionApi.Form form = new DeclareExtensionApi.Form();
        form.setName("testName");
        ResponseEntity<DeclareExtensionApi.Receipt> response = declareExtensionApi.declareExtension(requestId, ownerId, operatorId, form);
        DeclareExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(receipt);
    }

    @Test
    public void testVersionOnly() {
        DeclareExtensionApi.Form form = new DeclareExtensionApi.Form();
        form.setTree("testTree");
        ResponseEntity<DeclareExtensionApi.Receipt> response = declareExtensionApi.declareExtension(requestId, ownerId, operatorId, form);
        DeclareExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(receipt);
    }

    @Test
    public void testVisibilityOnly() {
        DeclareExtensionApi.Form form = new DeclareExtensionApi.Form();
        form.setVisibility("testVisibility");
        ResponseEntity<DeclareExtensionApi.Receipt> response = declareExtensionApi.declareExtension(requestId, ownerId, operatorId, form);
        DeclareExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(receipt);
    }


    @Test
    public void testInvalidStructure() {
        DeclareExtensionApi.Form form = new DeclareExtensionApi.Form();
        form.setGroup("testGroup");
        form.setName("testName");
        form.setTree("testTree");
        form.setVisibility("protected");
        ResponseEntity<DeclareExtensionApi.Receipt> response = declareExtensionApi.declareExtension(requestId, ownerId, operatorId, form);
        DeclareExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(receipt);
    }

    @Test
    public void testInvalidVisibility() {
        DeclareExtensionApi.Form form = new DeclareExtensionApi.Form();
        form.setGroup("testGroup");
        form.setName("testName");
        form.setTree("testTree");
        form.setVisibility("testVisibility");
        ResponseEntity<DeclareExtensionApi.Receipt> response = declareExtensionApi.declareExtension(requestId, ownerId, operatorId, form);
        DeclareExtensionApi.Receipt receipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(receipt);
    }

    @Test
    public void testSave() {
        String group = "testGroup";
        String name = "testName";
        String tree = "testTree";

        DeclareExtensionApi.Form form = new DeclareExtensionApi.Form();
        form.setGroup(group);
        form.setName(name);
        form.setTree(tree);
        form.setVisibility(AnVisibilityValidator.Visibility.PROTECTED.name());
        ResponseEntity<DeclareExtensionApi.Receipt> response = declareExtensionApi.declareExtension(requestId, ownerId, operatorId, form);
        DeclareExtensionApi.Receipt receipt = response.getBody();
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
