package wang.yanjiong.metamate.core.api;

import one.kii.summer.context.exception.BadRequest;
import one.kii.summer.context.exception.Conflict;
import one.kii.summer.context.io.WriteContext;
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
import wang.yanjiong.metamate.core.fui.AnVisibilityValidator;

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
        DeclareExtensionApi.CommitForm commitForm = new DeclareExtensionApi.CommitForm();
        commitForm.setGroup("testGroup");

        WriteContext context = new WriteContext();
        context.setRequestId(requestId);
        context.setOperatorId(operatorId);
        context.setOwnerId(ownerId);

        DeclareExtensionApi.CommitReceipt response = null;
        try {
            response = declareExtensionApi.commit(context, commitForm);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }
        Assert.assertNull(response);
    }

    @Test
    public void testNameOnly() {
        DeclareExtensionApi.CommitForm commitForm = new DeclareExtensionApi.CommitForm();
        commitForm.setName("testName");

        WriteContext context = new WriteContext();
        context.setRequestId(requestId);
        context.setOperatorId(operatorId);
        context.setOwnerId(ownerId);

        DeclareExtensionApi.CommitReceipt response = null;
        try {
            response = declareExtensionApi.commit(context, commitForm);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }
        Assert.assertNull(response);
    }

    @Test
    public void testVersionOnly() {
        DeclareExtensionApi.CommitForm commitForm = new DeclareExtensionApi.CommitForm();
        commitForm.setTree("testTree");

        WriteContext context = new WriteContext();
        context.setRequestId(requestId);
        context.setOperatorId(operatorId);
        context.setOwnerId(ownerId);

        DeclareExtensionApi.CommitReceipt response = null;
        try {
            response = declareExtensionApi.commit(context, commitForm);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }
        Assert.assertNull(response);
    }

    @Test
    public void testVisibilityOnly() {
        DeclareExtensionApi.CommitForm commitForm = new DeclareExtensionApi.CommitForm();
        commitForm.setVisibility("testVisibility");

        WriteContext context = new WriteContext();
        context.setRequestId(requestId);
        context.setOperatorId(operatorId);
        context.setOwnerId(ownerId);

        DeclareExtensionApi.CommitReceipt response = null;
        try {
            response = declareExtensionApi.commit(context, commitForm);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }
        Assert.assertNull(response);
    }


    @Test
    public void testInvalidStructure() {
        DeclareExtensionApi.CommitForm commitForm = new DeclareExtensionApi.CommitForm();
        commitForm.setGroup("testGroup");
        commitForm.setName("testName");
        commitForm.setTree("testTree");
        commitForm.setVisibility("protected");
        WriteContext context = new WriteContext();
        context.setRequestId(requestId);
        context.setOperatorId(operatorId);
        context.setOwnerId(ownerId);

        DeclareExtensionApi.CommitReceipt response = null;
        try {
            response = declareExtensionApi.commit(context, commitForm);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }
        Assert.assertNull(response);
    }

    @Test
    public void testInvalidVisibility() {
        DeclareExtensionApi.CommitForm commitForm = new DeclareExtensionApi.CommitForm();
        commitForm.setGroup("testGroup");
        commitForm.setName("testName");
        commitForm.setTree("testTree");
        commitForm.setVisibility("testVisibility");
        WriteContext context = new WriteContext();
        context.setRequestId(requestId);
        context.setOperatorId(operatorId);
        context.setOwnerId(ownerId);

        DeclareExtensionApi.CommitReceipt response = null;
        try {
            response = declareExtensionApi.commit(context, commitForm);
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

        DeclareExtensionApi.CommitForm commitForm = new DeclareExtensionApi.CommitForm();
        commitForm.setGroup(group);
        commitForm.setName(name);
        commitForm.setTree(tree);

        WriteContext context = new WriteContext();
        context.setRequestId(requestId);
        context.setOperatorId(operatorId);
        context.setOwnerId(ownerId);

        commitForm.setVisibility(AnVisibilityValidator.Visibility.PROTECTED.name());
        DeclareExtensionApi.CommitReceipt response = null;
        try {
            response = declareExtensionApi.commit(context, commitForm);
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
