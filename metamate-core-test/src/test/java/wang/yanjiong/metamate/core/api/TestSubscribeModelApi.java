package wang.yanjiong.metamate.core.api;

import com.sinewang.metamate.core.dai.mapper.ModelSubscriptionMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yanjiong.metamate.core.fi.AnSubscribeModelExtractor;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.metamate.core")
@SpringBootTest(classes = {TestSubscribeModelApi.class})
public class TestSubscribeModelApi {

    @Autowired
    private SubscribeModelApi subscribeModelApi;

    @Autowired
    private AnSubscribeModelExtractor subscribeModelExtractor;


    @Autowired
    private ModelSubscriptionMapper modelSubscriptionMapper;

    private String group = "testSubGroup";

    private String name = "testSubName";

    private String tree = "testSubTree";

    private String subscriberId = "testSubscriberId";

    private String providerId = "testProviderId";

    private String operatorId = "testOperatorId";

    private String version = "testVersion";

    private String extId = "testExtId";

    private String pubExtId = "testPubExtId";

    private String publication = "SNAPSHOT";

    @Test
    public void test() {
        SubscribeModelApi.Form form = new SubscribeModelApi.Form();


        String id = subscribeModelExtractor.hashId(pubExtId, subscriberId);
        modelSubscriptionMapper.deleteById(id);


        form.setGroup(group);
        form.setName(name);
        form.setTree(tree);
        SubscribeModelApi.Receipt receipt = subscribeModelApi.subscribe(
                form,
                subscriberId,
                operatorId,
                providerId,
                extId,
                publication,
                version
        ).getBody();

        Assert.assertEquals(pubExtId, receipt.getPubExtId());
        Assert.assertEquals(group, receipt.getGroup());
        Assert.assertEquals(id, receipt.getId());
        Assert.assertEquals(name, receipt.getName());
        Assert.assertEquals(tree, receipt.getTree());
        Assert.assertEquals(subscriberId, receipt.getSubscriberId());
        Assert.assertEquals(operatorId, receipt.getOperatorId());

        modelSubscriptionMapper.deleteById(id);

    }
}
