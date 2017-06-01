package com.sinewang.kiimate.model.core.api;

import com.sinewang.kiimate.model.core.dai.mapper.ExtensionMapper;
import com.sinewang.kiimate.model.core.dai.mapper.ModelPublicationMapper;
import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.kiimate.model.core.fui.AnStructureValidator;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
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

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.kiimate.model.core")
@SpringBootTest(classes = {TestPublishModelApi.class})
public class TestPublishModelApi {

    private static final Eid64Generator idgen = new Eid64Generator();
    @Autowired
    private PublishModelApi publishModelApi;
    @Autowired
    private ExtensionDai extensionDai;
    @Autowired
    private IntensionDai intensionDai;
    @Autowired
    private AnExtensionExtractor extensionExtractor;
    @Autowired
    private ExtensionMapper extensionMapper;
    @Autowired
    private ModelPublicationMapper modelPublicationMapper;
    private String providerId = "testProviderId";
    private String ownerId = "testOwnerId";
    private String group = "testGroup";
    private String name = "testName";
    private String tree = "testTree";
    private String visitorId = "testVisitorId";
    private String visibility = "protected";
    private String operatorId = "operatorId";
    private long extId;
    private String[] fields = new String[]{"username", "password", "driver-class-name"};
    private String version = "1.0.0";
    private String requestId = "testRequestId";

    @Before
    public void before() {
        AnExtensionExtractor.Extension extension1 = new AnExtensionExtractor.Extension();
        extension1.setOwnerId(ownerId);
        extension1.setGroup(group);
        extension1.setName(name);
        extension1.setTree(tree);
        extension1.setVisibility(visibility);

        this.extId = extension1.getId();

        modelPublicationMapper.deletePublicationByProviderIdExtIdPubVersion(providerId, extId, "SNAPSHOT", version);
        extensionMapper.deleteExtensionById(extId);


        ExtensionDai.Record extension = new ExtensionDai.Record();

        extension.setGroup(group);

        extension.setName(name);

        extension.setTree(tree);

        extension.setOwnerId(ownerId);

        extension.setVisibility(visibility);


        extension.setId(extId);

        try {
            extensionDai.remember(extension);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            extensionDuplicated.printStackTrace();
        }


        IntensionDai.Record record = new IntensionDai.Record();

        for (String field : fields) {
            record.setExtId(extId);
            record.setSingle(true);
            record.setVisibility(visibility);
            record.setStructure(AnStructureValidator.Structure.STRING.name());
            record.setField(field);

            record.setId(idgen.born());
            try {
                intensionDai.remember(record);
            } catch (IntensionDai.IntensionDuplicated intensionDuplicated) {
                intensionDuplicated.printStackTrace();
            }
        }

    }

    @Test
    public void test() {
        PublishModelApi.Form form = new PublishModelApi.Form();
        form.setVersion(version);
        form.setProviderId(providerId);
        form.setExtId(extId);
        modelPublicationMapper.deletePublicationByProviderIdExtIdPubVersion(providerId, extId, "SNAPSHOT", version);


        WriteContext context = new WriteContext(requestId, ownerId, operatorId);


        PublishModelApi.Receipt receipt = null;
        try {
            receipt = publishModelApi.commit(context, form);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        }


        Assert.assertNotNull(receipt);
        Assert.assertEquals(version, receipt.getVersion());

        Assert.assertEquals(ownerId, receipt.getOwnerId());
        Assert.assertEquals(providerId, receipt.getProviderId());

    }

    @After
    public void after() {
        extensionMapper.deleteExtensionById(extId);
        modelPublicationMapper.deletePublicationByProviderIdExtIdPubVersion(providerId, extId, "SNAPSHOT", version);
    }
}
