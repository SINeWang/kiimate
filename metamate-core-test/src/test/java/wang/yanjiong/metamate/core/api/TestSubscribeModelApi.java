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

    private String subscriberId = "testSubscriberId";

    private String operatorId = "testOperatorId";

    private String requestId = "testRequestId";

    private String pubSetHash = "testPubSetHash";

    private String name = "testSubName";

    private String tree = "testSubTree";

    @Test
    public void test() {
        SubscribeModelApi.Form form = new SubscribeModelApi.Form();

        form.setGroup("testGroup");

        form.setPubSetHash(pubSetHash);


        String id = subscribeModelExtractor.hashId(subscriberId, pubSetHash, group, name, tree);
        modelSubscriptionMapper.deleteById(id);


        form.setSubscriberId(subscriberId);
        form.setGroup(group);
        form.setName(name);
        SubscribeModelApi.Receipt receipt = subscribeModelApi.subscribe(
                requestId,
                operatorId,
                form
        ).getBody();

        Assert.assertEquals(receipt.getSubSetHash(), pubSetHash);

        modelSubscriptionMapper.deleteById(id);

    }
}
