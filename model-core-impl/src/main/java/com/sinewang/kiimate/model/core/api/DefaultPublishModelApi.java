package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.kiimate.model.core.fui.AnPublicationExtractor;
import one.kii.summer.beans.utils.KeyFactorTools;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultPublishModelApi implements PublishModelApi {

    @Autowired
    private AnPublicationExtractor publicationExtractor;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private ModelPublicationDai modelPublicationDai;

    @Autowired
    private AnExtensionExtractor extensionExtractor;


    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound {
        ExtensionDai.ChannelId channelId = new ExtensionDai.ChannelId();
        channelId.setId(form.getExtId());
        ExtensionDai.Extension extension = extensionDai.loadExtension(channelId);

        Date date = new Date();

        AnPublicationExtractor.ExtensionPublication extensionPublication = publicationExtractor.extract(form, extension.getId(), context.getOperatorId(), date);

        IntensionDai.ChannelExtension channel = ValueMapping.from(IntensionDai.ChannelExtension.class, extension);

        List<IntensionDai.Intension> allIntensions = new ArrayList<>();

        List<IntensionDai.Intension> intensions = intensionDai.loadIntensions(channel);
        if (intensions.isEmpty()) {
            throw new NotFound(new String[]{"intensions"});
        }
        allIntensions.addAll(intensions);

        List<AnPublicationExtractor.IntensionPublication> publications = publicationExtractor.extract(extensionPublication, intensions);

        List<ModelPublicationDai.Publication> publications1 = ValueMapping.from(ModelPublicationDai.Publication.class, publications);

        try {
            modelPublicationDai.save(publications1);
        } catch (ModelPublicationDai.DuplicatedPublication duplicatedPublication) {
            throw new Conflict(KeyFactorTools.find(Form.class));
        }

        Receipt receipt = new Receipt();

        receipt.setVersion(form.getVersion());

        receipt.setCreatedAt(date);

        List<Intension> snapshotIntensions = ValueMapping.from(Intension.class, allIntensions);

        receipt.setIntensions(snapshotIntensions);

        receipt.setProviderId(form.getProviderId());

        receipt.setOwnerId(context.getOwnerId());

        return receipt;

    }

}
