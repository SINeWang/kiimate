package com.sinewang.kiimate.model.core.api;

import com.sinewang.kiimate.model.core.dai.mapper.ModelSubscriptionMapper;
import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.api.SubscribeModelsApi;
import one.kii.kiimate.model.core.fui.AnSubscribeModelExtractor;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.kiimate.model.core")
@SpringBootTest(classes = {TestSubscribeModelsApi.class})
public class TestSubscribeModelsApi {

    private static final Eid64Generator idgen = new Eid64Generator();


    @Autowired
    private SubscribeModelsApi subscribeModelsApi;

    @Autowired
    private AnSubscribeModelExtractor subscribeModelExtractor;

    @Autowired
    private ModelSubscriptionMapper modelSubscriptionMapper;

    private String group = "testSubGroup";

    private String subscriberId = "testSubscriberId";

    private String operatorId = "testOperatorId";

    private String ownerId = "testOwnerId";

    private String requestId = "testRequestId";

    private long pubSet = 1;

    private String name = "testSubName";

    private String tree = "testSubTree";

    @Test
    public void test() {
        SubscribeModelsApi.Form form = new SubscribeModelsApi.Form();

        form.setGroup("testGroup");

        form.setSubSet(pubSet);

        long id = idgen.born();

        AnSubscribeModelExtractor.ModelSubscription model = new AnSubscribeModelExtractor.ModelSubscription();
        model.setSubscriberId(subscriberId);
        model.setSubSet(pubSet);
        model.setGroup(group);
        model.setName(name);
        model.setTree(tree);

        modelSubscriptionMapper.deleteById(id);

        form.setGroup(group);
        form.setName(name);

        WriteContext context = new WriteContext(requestId, ownerId, operatorId);


        SubscribeModelsApi.Receipt receipt = null;
        try {
            receipt = subscribeModelsApi.commit(context, form);
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }

        modelSubscriptionMapper.deleteById(id);

    }
}
