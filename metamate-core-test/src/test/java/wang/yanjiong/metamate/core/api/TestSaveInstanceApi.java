package wang.yanjiong.metamate.core.api;

import com.sinewang.metamate.core.dai.mapper.InstanceMapper;
import com.sinewang.metamate.core.dai.mapper.IntensionMapper;
import com.sinewang.metamate.core.dai.mapper.ModelPublicationMapper;
import com.sinewang.metamate.core.dai.mapper.ModelSubscriptionMapper;
import one.kii.summer.context.exception.Conflict;
import one.kii.summer.context.exception.NotFound;
import one.kii.summer.context.io.ReadContext;
import one.kii.summer.context.io.WriteContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import wang.yanjiong.metamate.core.fi.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.metamate.core")
@SpringBootTest(classes = {TestSaveInstanceApi.class})
public class TestSaveInstanceApi {

    @Autowired
    private SaveInstanceApi saveInstanceApi;

    @Autowired
    private VisitEntitiesApi visitEntitiesApi;

    @Autowired
    private ModelSubscriptionMapper modelSubscriptionMapper;

    @Autowired
    private ModelPublicationMapper modelPublicationMapper;

    @Autowired
    private IntensionMapper intensionMapper;

    @Autowired
    private InstanceMapper instanceMapper;

    @Autowired
    private AnSubscribeModelExtractor subscribeModelExtractor;

    @Autowired
    private AnPublicationExtractor publicationExtractor;

    @Autowired
    private AnIntensionExtractor intensionExtractor;

    private String ownerId = "testOwnerId";

    private String operatorId = "testOperatorId";

    private String providerId = "testProviderId";

    private String pubSetHash = "testPubSetHash";

    private String extId = "testExtId";

    private String requestId = "testRequestId";

    private String visitorId = "testVisitorId";

    private String group = "testSubGroup";

    private String name = "testSubName";

    private String tree = "testSubTree";

    private String publication = "SNAPSHOT";

    private String version = "testSubVersion";

    private String subscriberId = ownerId;

    private String subId;

    private String[] fields = {"testFieldA", "testFieldB"};

    private boolean[] singles = {true, false};

    private String structure = AnStructureValidator.Structure.STRING.name();

    private String visibility = AnVisibilityValidator.Visibility.PUBLIC.name();

    @Before
    public void before() {
        modelPublicationMapper.deletePublicationByProviderIdExtIdPubVersion(providerId, extId, publication, version);

        SnapshotModelApi.Form form = new SnapshotModelApi.Form();
        form.setVersion(version);

        for (int i = 0; i < fields.length; i++) {
            String intId = intensionExtractor.hashId(extId, fields[i]);
            String pubExtId = publicationExtractor.hashPubExtId(providerId, extId, publication, version);
            String publicationId = publicationExtractor.hashId(pubExtId, intId);
            modelPublicationMapper.insertPublication(
                    publicationId, pubSetHash, providerId, extId, intId, version, publication, operatorId, new Date());

            intId = intensionExtractor.hashId(extId, fields[i]);
            intensionMapper.insertIntension(
                    intId, extId, fields[i], singles[i], structure, null, visibility, new Date());

        }


        String pubExtId = publicationExtractor.hashPubExtId(providerId, extId, publication, version);

        subId = subscribeModelExtractor.hashId(subscriberId, pubSetHash, group, name, tree);

        modelSubscriptionMapper.insertSubscription(

                subId, subscriberId, pubSetHash, group, name, tree, operatorId, new Date()
        );

    }


    @Test
    public void test() {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        String[] valueOfA = new String[]{"a1", "a2"};

        String valueOfB = "d";

        String keyA = fields[0];

        String keyB = fields[1];

        Arrays.sort(valueOfA);

        map.put(keyA, Arrays.asList(valueOfA));
        map.put(keyB, Arrays.asList(valueOfB));
        List<SaveInstanceApi.Instance> instances = null;
        try {
            WriteContext context = new WriteContext();
            context.setRequestId(requestId);
            context.setOperatorId(operatorId);
            context.setOwnerId(ownerId);

            SaveInstanceApi.Form form = new SaveInstanceApi.Form();
            form.setMap(map);
            form.setGroup(group);
            form.setName(name);
            form.setTree(tree);

            instances = saveInstanceApi.saveInstance(context, form).getInstances();
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }

        Assert.assertEquals(3, instances.size());

        ReadContext context = new ReadContext();
        context.setRequestId(requestId);
        context.setVisitorId(visitorId);
        context.setOwnerId(ownerId);

        VisitEntitiesApi.Form form = new VisitEntitiesApi.Form();

        form.setName(name);
        form.setGroup(group);
        form.setTree(tree);

        Map<String, Object> instancesMap = visitEntitiesApi.readInstancesByGroupNameTree(context, form);

        for (String key : instancesMap.keySet()) {
            if (key.equals(keyB)) {
                Assert.assertEquals(valueOfB, instancesMap.get(keyB));
            } else if (key.equals(keyA)) {
                Assert.assertArrayEquals(valueOfA, (String[]) instancesMap.get(keyB));

            }
        }

    }

    @After
    public void after() {
        modelSubscriptionMapper.deleteById(subId);

        instanceMapper.deleteInstanceByOwnerId(ownerId, subId);

        intensionMapper.deleteIntensionsByExtId(extId);

        modelPublicationMapper.deletePublicationByProviderId(providerId);

    }

}

