package wang.yanjiong.metamate.core.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.metamate.core")
@SpringBootTest(classes = {TestDeclareExtensionApi.class})
public class TestPublishModelApi {

    @Autowired
    private SnapshotModelApi snapshotModelApi;

    @Test
    public void test() {
        SnapshotModelApi.Form form = new SnapshotModelApi.Form();
        form.setVersion("1.0.0");
        ResponseEntity<SnapshotModelApi.Receipt> response = snapshotModelApi.snapshot(
                form,
                "wangyj",
                "testOperatorId",
                "one.euler.gitlab",
                "project",
                "master"

        );
        SnapshotModelApi.Receipt receipt = response.getBody();
        Assert.assertNotNull(receipt);
    }
}
