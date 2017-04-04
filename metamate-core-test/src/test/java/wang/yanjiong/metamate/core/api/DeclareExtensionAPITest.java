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
    private DeclareNameApi declareNameApi;

    @Autowired
    private ExtensionDai extensionDai;

    private String ownerId = "testOwnerId";

    private String operatorId = "testOperatorId";

    @Test
    public void testGroupOnly() {
        DeclareNameApi.NameForm nameForm = new DeclareNameApi.NameForm();
        nameForm.setGroup("testGroup");
        ResponseEntity<DeclareNameApi.NameReceipt> response = declareNameApi.declareByFormUrlEncoded(nameForm, ownerId, operatorId);
        DeclareNameApi.NameReceipt nameReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(nameReceipt);
    }

    @Test
    public void testNameOnly() {
        DeclareNameApi.NameForm nameForm = new DeclareNameApi.NameForm();
        nameForm.setName("testName");
        ResponseEntity<DeclareNameApi.NameReceipt> response = declareNameApi.declareByFormUrlEncoded(nameForm, ownerId, operatorId);
        DeclareNameApi.NameReceipt nameReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(nameReceipt);
    }

    @Test
    public void testVersionOnly() {
        DeclareNameApi.NameForm nameForm = new DeclareNameApi.NameForm();
        nameForm.setTree("testTree");
        ResponseEntity<DeclareNameApi.NameReceipt> response = declareNameApi.declareByFormUrlEncoded(nameForm, ownerId, operatorId);
        DeclareNameApi.NameReceipt nameReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(nameReceipt);
    }

    @Test
    public void testVisibilityOnly() {
        DeclareNameApi.NameForm nameForm = new DeclareNameApi.NameForm();
        nameForm.setVisibility("testVisibility");
        ResponseEntity<DeclareNameApi.NameReceipt> response = declareNameApi.declareByFormUrlEncoded(nameForm, ownerId, operatorId);
        DeclareNameApi.NameReceipt nameReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(nameReceipt);
    }

    @Test
    public void testStructureOnly() {
        DeclareNameApi.NameForm nameForm = new DeclareNameApi.NameForm();
        nameForm.setStructure("testStructure");
        ResponseEntity<DeclareNameApi.NameReceipt> response = declareNameApi.declareByFormUrlEncoded(nameForm, ownerId, operatorId);
        DeclareNameApi.NameReceipt nameReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(nameReceipt);
    }


    @Test
    public void testInvalidStructure() {
        DeclareNameApi.NameForm nameForm = new DeclareNameApi.NameForm();
        nameForm.setGroup("testGroup");
        nameForm.setName("testName");
        nameForm.setTree("testTree");
        nameForm.setVisibility("protected");
        nameForm.setStructure("testStructure");
        ResponseEntity<DeclareNameApi.NameReceipt> response = declareNameApi.declareByFormUrlEncoded(nameForm, ownerId, operatorId);
        DeclareNameApi.NameReceipt nameReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(nameReceipt);
    }

    @Test
    public void testInvalidVisibility() {
        DeclareNameApi.NameForm nameForm = new DeclareNameApi.NameForm();
        nameForm.setGroup("testGroup");
        nameForm.setName("testName");
        nameForm.setTree("testTree");
        nameForm.setVisibility("testVisibility");
        nameForm.setStructure("complex");
        ResponseEntity<DeclareNameApi.NameReceipt> response = declareNameApi.declareByFormUrlEncoded(nameForm, ownerId, operatorId);
        DeclareNameApi.NameReceipt nameReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), httpStatus.value());
        Assert.assertNull(nameReceipt);
    }

    @Test
    public void testSave() {
        String group = "testGroup";
        String name = "testName";
        String tree = "testTree";

        DeclareNameApi.NameForm nameForm = new DeclareNameApi.NameForm();
        nameForm.setGroup(group);
        nameForm.setName(name);
        nameForm.setTree(tree);
        nameForm.setVisibility("protected");
        nameForm.setStructure("complex");
        ResponseEntity<DeclareNameApi.NameReceipt> response = declareNameApi.declareByFormUrlEncoded(nameForm, ownerId, operatorId);
        DeclareNameApi.NameReceipt nameReceipt = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();
        Assert.assertEquals(HttpStatus.ACCEPTED.value(), httpStatus.value());
        Assert.assertNotNull(nameReceipt);
        String id = nameReceipt.getId();
        Assert.assertNotNull(id);


        ExtensionDai.Extension extension = extensionDai.selectExtensionById(id);
        Assert.assertEquals(group, extension.getGroup());
        Assert.assertEquals(name, extension.getName());
        Assert.assertEquals(tree, extension.getTree());

        extensionDai.deleteExtensionById(id);
    }
}
