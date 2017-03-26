package wang.yanjiong.metamate.core.dai;

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
public class ExtensionDaiTest {

    @Autowired
    private ExtensionDai extensionDai;

    @Test(expected = NullPointerException.class)
    public void testNull() {
        extensionDai.selectExtensionById(null);
    }

    @Test(expected = NullPointerException.class)
    public void testEmpty() {
        extensionDai.selectExtensionById("");
    }

    @Test
    public void testExist() {
        ExtensionDai.Extension extension = extensionDai.selectExtensionById("12");
        Assert.assertNotNull(extension);
    }
}
