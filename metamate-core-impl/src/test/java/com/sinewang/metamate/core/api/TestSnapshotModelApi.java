package com.sinewang.metamate.core.api;

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
import wang.yanjiong.metamate.core.api.SnapshotModelApi;
import wang.yanjiong.metamate.core.api.VisitExtensionApi;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.fui.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fui.AnIntensionExtractor;
import wang.yanjiong.metamate.core.fui.AnStructureValidator;
import wang.yanjiong.metamate.core.fui.AnVisibilityValidator;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.metamate.core")
@SpringBootTest(classes = {TestSnapshotModelApi.class})
public class TestSnapshotModelApi {

    @Autowired
    private SnapshotModelApi snapshotModelApi;

    @Autowired
    private VisitExtensionApi visitExtensionApi;

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

    private String visibility = AnVisibilityValidator.Visibility.PUBLIC.name();

    private String operatorId = "operatorId";

    private String extId;

    private String[] fields = new String[]{"username", "password", "driver-class-name"};


    private String version = "1.0.0";

    private String requestId = "testRequestId";


    @Before
    public void before() {
        this.extId = extensionExtractor.hashId(ownerId, group, name, tree, visibility);

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
        form.setProviderId(providerId);
        form.setGroup(group);
        modelPublicationMapper.deletePublicationByProviderIdExtIdPubVersion(providerId, extId, "SNAPSHOT", version);


        WriteContext context = new WriteContext(requestId, ownerId, operatorId);


        SnapshotModelApi.Receipt receipt = null;
        try {
            receipt = snapshotModelApi.snapshot(context, form);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        }


        Assert.assertNotNull(receipt);
        Assert.assertEquals(version, receipt.getVersion());

        Assert.assertEquals(ownerId, receipt.getOwnerId());
        Assert.assertEquals(providerId, receipt.getProviderId());

    }

    @After
    public void after() {
        extensionDai.deleteExtensionById(extId);
        intensionDai.deleteIntensionsByExitId(extId);
        modelPublicationMapper.deletePublicationByProviderIdExtIdPubVersion(providerId, extId, "SNAPSHOT", version);
    }
}
