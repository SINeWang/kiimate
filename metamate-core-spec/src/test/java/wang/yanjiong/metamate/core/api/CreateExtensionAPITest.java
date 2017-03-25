package wang.yanjiong.metamate.core.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
public class CreateExtensionAPITest {

    @Autowired
    private CreateExtensionApi createExtensionApi;

    @Test()
    public void testCreate() {
//        CreateExtensionForm form = new CreateExtensionForm();
//        form.setGroup("123");
//        CreateExtensionReceipt receipt = createExtensionApi.createExtensionViaFormUrlEncoded(form);
//        Assert.assertNotNull(receipt);
    }

}
