package wang.yanjiong.metamate.core.api;

import org.junit.Assert;
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

import java.util.Arrays;
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
    private VisitInstancesApi visitInstancesApi;

    private String ownerId = "testOwnerId";

    private String operatorId = "testOperatorId";

    private String providerId = "testProviderId";

    private String extId = "testExtId";

    private String requestId = "testRequestId";

    private String visitorId = "testVisitorId";

    private String tree = "testTree";


    @Test
    public void test() {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        String[] valueOfA = new String[]{"a1", "a2"};

        String valueOfB = "d";

        String keyA = "a";

        String keyB = "b";

        Arrays.sort(valueOfA);

        map.put(keyA, Arrays.asList(valueOfA));
        map.put(keyB, Arrays.asList(valueOfB));
        List<SaveInstanceApi.Instance> instances = saveInstanceApi.saveInstanceViaFormUrlEncoded(
                ownerId,
                operatorId,
                requestId,
                providerId,
                extId
                , map

        ).getBody();

        Assert.assertEquals(4, instances.size());
        for (SaveInstanceApi.Instance instance : instances) {
            if (instance.getField().equals(keyA)) {
                String[] v = instance.getValue();
                Arrays.sort(v);
                Assert.assertArrayEquals(valueOfA, v);
            } else if (instance.getField().equals(keyB)) {
                String v = instance.getValue()[0];
                Assert.assertEquals(valueOfB, v);
            }
        }


        Map<String, Object> instancesMap = visitInstancesApi.readInstancesByGroupNameVersion(
                ownerId,
                visitorId,
                null
        ).getBody();

        for (String key : instancesMap.keySet()) {
            if (key.equals(keyB)) {
                Assert.assertEquals(valueOfB, instancesMap.get(keyB));
            } else if (key.equals(keyA)) {
                Assert.assertArrayEquals(valueOfA, (String[]) instancesMap.get(keyB));

            }
        }

    }

}

