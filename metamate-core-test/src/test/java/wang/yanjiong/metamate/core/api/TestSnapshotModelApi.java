package wang.yanjiong.metamate.core.api;

import com.sinewang.metamate.core.dai.mapper.ModelPublicationMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnIntensionExtractor;
import wang.yanjiong.metamate.core.fi.AnStructureValidator;
import wang.yanjiong.metamate.core.fi.AnVisibilityValidator;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.metamate.core")
@SpringBootTest(classes = {TestDeclareExtensionApi.class})
public class TestSnapshotModelApi {

    @Autowired
    private SnapshotModelApi snapshotModelApi;

    @Autowired
    private VisitModelsApi visitModelsApi;

    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Autowired
    private AnIntensionExtractor intensionExtractor;

    @Autowired
    private ModelPublicationMapper modelPublicationMapper;

    private String providerId = "testProviderId";

    private String ownerId = "testOwnerId";

    private String group = "testGroup";

    private String name = "testName";

    private String tree = "testTree";

    private String visitorId = "testVisitorId";

    private String tag = "testTag";

    private String visibility = AnVisibilityValidator.Visibility.PUBLIC.name();

    private String operatorId = "operatorId";

    private String extId;

    private String[] fields = new String[]{"username", "password", "driver-class-name"};


    private String version = "1.0.0";

    @Before
    public void before() {
        this.extId = extensionExtractor.hashId(ownerId, group, name, tree);

        modelPublicationMapper.deletePublicationByProviderIdExtIdPubVersion(providerId, extId, "SNAPSHOT", version);
        extensionDai.deleteExtensionById(extId);
        intensionDai.deleteIntensionsByExitId(extId);


        ExtensionDai.Extension extension = new ExtensionDai.Extension();

        extension.setGroup(group);

        extension.setName(name);

        extension.setTree(tree);

        extension.setOwnerId(ownerId);

        extension.setVisibility(visibility);


        extension.setId(extId);

        extension.setStructure(AnStructureValidator.Structure.IMPORT.name());

        try {
            extensionDai.insertExtension(extension);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            extensionDuplicated.printStackTrace();
        }


        IntensionDai.Intension intension = new IntensionDai.Intension();

        for (String field : fields) {
            intension.setExtId(extId);
            intension.setSingle(true);
            intension.setVisibility(visibility);
            intension.setStructure(AnStructureValidator.Structure.STRING.name());
            intension.setField(field);
            String intId = intensionExtractor.hashId(extId, field);
            intension.setId(intId);
            try {
                intensionDai.insertIntension(intension);
            } catch (IntensionDai.IntensionDuplicated intensionDuplicated) {
                intensionDuplicated.printStackTrace();
            }
        }

    }

    @Test
    public void test() {
        SnapshotModelApi.Form form = new SnapshotModelApi.Form();
        form.setVersion(version);
        modelPublicationMapper.deletePublicationByProviderIdExtIdPubVersion(providerId, extId, "SNAPSHOT", version);

        ResponseEntity<SnapshotModelApi.Receipt> response = null;
        try {
            response = snapshotModelApi.snapshot(
                    form,
                    providerId,
                    ownerId,
                    operatorId,
                    group,
                    name,
                    tree);
        } catch (SnapshotModelApi.RefereceExtensionHasNotBeenPublished refereceExtensionHasNotBeenPublished) {
            refereceExtensionHasNotBeenPublished.printStackTrace();
        }
        SnapshotModelApi.Receipt receipt = response.getBody();
        List<SnapshotModelApi.Intension> intensions = receipt.getIntensions();
        Assert.assertNotNull(receipt);
        Assert.assertEquals(version, receipt.getVersion());
        Assert.assertEquals(fields.length, intensions.size());
        Assert.assertEquals(ownerId, receipt.getOwnerId());
        Assert.assertEquals(providerId, receipt.getProviderId());

        Map<String, Object> map = visitModelsApi.readIntensionsByGroupNameVersion(
                ownerId,
                visitorId,
                group,
                name,
                tree,
                tag
        ).getBody();

        Assert.assertNotNull(map);
    }

    @After
    public void after() {
        extensionDai.deleteExtensionById(extId);
        intensionDai.deleteIntensionsByExitId(extId);
        modelPublicationMapper.deletePublicationByProviderIdExtIdPubVersion(providerId, extId, "SNAPSHOT", version);
    }
}
