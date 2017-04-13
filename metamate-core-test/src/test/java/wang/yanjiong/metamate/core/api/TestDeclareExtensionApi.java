package wang.yanjiong.metamate.core.api;

import one.kii.summer.context.exception.BadRequest;
import one.kii.summer.context.exception.Conflict;
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
        form.setRequestId(requestId);
        form.setOwnerId(ownerId);
        form.setOperatorId(operatorId);
        DeclareExtensionApi.Receipt response = null;
        try {
            response = declareExtensionApi.declareExtension(form);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }
        Assert.assertNull(response);
    }

    @Test
    public void testNameOnly() {
        DeclareExtensionApi.Form form = new DeclareExtensionApi.Form();
        form.setName("testName");
        form.setRequestId(requestId);
        form.setOwnerId(ownerId);
        form.setOperatorId(operatorId);
        DeclareExtensionApi.Receipt response = null;
        try {
            response = declareExtensionApi.declareExtension(form);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }
        Assert.assertNull(response);
    }

    @Test
    public void testVersionOnly() {
        DeclareExtensionApi.Form form = new DeclareExtensionApi.Form();
        form.setTree("testTree");
        form.setRequestId(requestId);
        form.setOwnerId(ownerId);
        form.setOperatorId(operatorId);
        DeclareExtensionApi.Receipt response = null;
        try {
            response = declareExtensionApi.declareExtension(form);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }
        Assert.assertNull(response);
    }

    @Test
    public void testVisibilityOnly() {
        DeclareExtensionApi.Form form = new DeclareExtensionApi.Form();
        form.setVisibility("testVisibility");
        form.setRequestId(requestId);
        form.setOwnerId(ownerId);
        form.setOperatorId(operatorId);
        DeclareExtensionApi.Receipt response = null;
        try {
            response = declareExtensionApi.declareExtension(form);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }
        Assert.assertNull(response);
    }


    @Test
    public void testInvalidStructure() {
        DeclareExtensionApi.Form form = new DeclareExtensionApi.Form();
        form.setGroup("testGroup");
        form.setName("testName");
        form.setTree("testTree");
        form.setVisibility("protected");
        form.setRequestId(requestId);
        form.setOwnerId(ownerId);
        form.setOperatorId(operatorId);
        DeclareExtensionApi.Receipt response = null;
        try {
            response = declareExtensionApi.declareExtension(form);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }
        Assert.assertNull(response);
    }

    @Test
    public void testInvalidVisibility() {
        DeclareExtensionApi.Form form = new DeclareExtensionApi.Form();
        form.setGroup("testGroup");
        form.setName("testName");
        form.setTree("testTree");
        form.setVisibility("testVisibility");
        form.setRequestId(requestId);
        form.setOwnerId(ownerId);
        form.setOperatorId(operatorId);
        DeclareExtensionApi.Receipt response = null;
        try {
            response = declareExtensionApi.declareExtension(form);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }
        Assert.assertNull(response);
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
        form.setRequestId(requestId);
        form.setOwnerId(ownerId);
        form.setOperatorId(operatorId);
        form.setVisibility(AnVisibilityValidator.Visibility.PROTECTED.name());
        DeclareExtensionApi.Receipt response = null;
        try {
            response = declareExtensionApi.declareExtension(form);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }
        String id = response.getId();
        Assert.assertNotNull(id);


        ExtensionDai.Extension extension = extensionDai.selectExtensionById(id);
        Assert.assertEquals(group, extension.getGroup());
        Assert.assertEquals(name, extension.getName());
        Assert.assertEquals(tree, extension.getTree());

        extensionDai.deleteExtensionById(id);
    }
}
