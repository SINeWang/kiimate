package wang.yanjiong.metamate.core.api;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnIntensionExtractor;
import wang.yanjiong.metamate.core.fi.AnStructureValidator;
import wang.yanjiong.metamate.core.fi.AnVisibilityValidator;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.metamate.core")
@SpringBootTest(classes = {TestDeclareExtensionApi.class})
public class TestDeclareIntensionApi {

    @Autowired
    private DeclareIntensionApi declareIntensionApi;

    @Autowired
    private VisitIntensionsApi visitIntensionsApi;

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Autowired
    private AnIntensionExtractor intensionExtractor;

    private String group = "testGroup";

    private String name = "testName";

    private String tree = "testTree";

    private String ownerId = "testOwnerId";

    private String operatorId = "testOperatorId";

    private String visibility = AnVisibilityValidator.Visibility.PUBLIC.name();

    private String structure = AnStructureValidator.Structure.STRING.name();

    private String refExtId = "testRefExtId";

    private String field = "testField";

    private String visitorId = "testVisitorId";

    private boolean single = true;

    private String extId;

    @Before
    public void before() {
        extId = extensionExtractor.hashId(ownerId, group, name, tree, visibility);
    }

//    @Test
//    public void testForm1() {
//        DeclareIntensionApi.IntensionForm1 form1 = new DeclareIntensionApi.IntensionForm1();
//        form1.setStructure(structure);
//        form1.setVisibility(visibility);
//        form1.setSingle(single);
//        form1.setField(field);
//        form1.setRefExtId(refExtId);
//        ResponseEntity<DeclareIntensionApi.Receipt> response = declareIntensionApi.declarePropViaFormUrlEncoded1(
//                form1,
//                group,
//                name,
//                tree,
//                ownerId,
//                operatorId
//
//        );
//        String id = intensionExtractor.hashId(extId, field);
//        DeclareIntensionApi.Receipt receipt = response.getBody();
//        Assert.assertEquals(single, receipt.isSingle());
//        Assert.assertEquals(visibility, receipt.getVisibility());
//        Assert.assertEquals(structure, receipt.getStructure());
//        Assert.assertEquals(extId, receipt.getExtId());
//        Assert.assertEquals(refExtId, receipt.getRefExtId());
//        Assert.assertEquals(field, receipt.getField());
//        Assert.assertEquals(id, receipt.getId());
//
//        ResponseEntity<VisitIntensionsApi.Extension> extsionResponse = visitIntensionsApi.readExtensionByGroupNameVersion(
//                ownerId,
//                visitorId,
//                group,
//                name,
//                tree);
//
//        VisitIntensionsApi.Extension extension = extsionResponse.getBody();
//        Assert.assertNotNull(extension);
//
//        Assert.assertEquals(extId, extension.getExtId());
//        Assert.assertEquals(1, extension.getIntensions().size());
//        VisitIntensionsApi.Intension intension = extension.getIntensions().get(0);
//
//        Assert.assertEquals(single, intension.isSingle());
//        Assert.assertEquals(visibility, intension.getVisibility());
//        Assert.assertEquals(structure, intension.getStructure());
//        Assert.assertEquals(field, intension.getField());
//        Assert.assertEquals(refExtId, intension.getRefExtId());
//    }
}
