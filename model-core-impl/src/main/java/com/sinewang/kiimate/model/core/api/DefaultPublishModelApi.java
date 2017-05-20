package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.kiimate.model.core.fui.AnPublicationExtractor;
import one.kii.summer.beans.utils.BasicCopy;
import one.kii.summer.beans.utils.HashTools;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
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

        ExtensionDai.Extension extension = extensionDai.selectExtensionById(form.getExtId());
        List<ModelPublicationDai.Publication> publications = new ArrayList<>();
        List<IntensionDai.Intension> allIntensions = new ArrayList<>();

        Date date = new Date();
        List<String> ids = new ArrayList<>();
        List<AnExtensionExtractor.Extension> newExtensions = new ArrayList<>();
        AnExtensionExtractor.Extension newExtension = BasicCopy.from(AnExtensionExtractor.Extension.class, extension);
        String tree = form.getStability() + "-" + form.getVersion();
        newExtension.setTree(tree);
        newExtensions.add(newExtension);

        AnPublicationExtractor.Publication snapshot;
        extensionExtractor.hashId(newExtension);
        snapshot = publicationExtractor.extractSnapshot(form, newExtension.getId(), context.getOperatorId(), date);

        List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extension.getId());
        if (intensions.isEmpty()) {
            throw new NotFound(new String[]{"intensions"});
        }
        for (IntensionDai.Intension intension : intensions) {
            intension.setExtId(newExtension.getId());
            intension.setId(HashTools.hashHex(intension));
        }

        allIntensions.addAll(intensions);

        final String publishExtId = publicationExtractor.hashPublishExtId(snapshot.getProviderId(), newExtension.getId());

        for (IntensionDai.Intension intension : intensions) {
            String id = publicationExtractor.hashId(publishExtId, intension.getId());
            ids.add(id);
            ModelPublicationDai.Publication daiPublication = BasicCopy.from(ModelPublicationDai.Publication.class, snapshot);
            daiPublication.setStability(form.getStability());
            daiPublication.setIntId(intension.getId());
            daiPublication.setId(id);
            daiPublication.setBeginTime(snapshot.getCreatedAt());
            publications.add(daiPublication);
        }

        String[] idArray = ids.toArray(new String[0]);
        Arrays.sort(idArray);
        String pubSet = HashTools.hashHex(idArray);

        try {
            List<ExtensionDai.Extension> newExtensionList = BasicCopy.from(ExtensionDai.Extension.class, newExtensions);
            modelPublicationDai.savePublications(pubSet, publications, newExtensionList, allIntensions);
        } catch (ModelPublicationDai.DuplicatedPublication duplicatedPublication) {
            Receipt receipt = BasicCopy.from(Receipt.class, duplicatedPublication);
            receipt.setVersion(form.getVersion());
            receipt.setOwnerId(context.getOwnerId());
            throw new Conflict(pubSet);
        }

        Receipt receipt = new Receipt();

        receipt.setVersion(form.getVersion());

        receipt.setCreatedAt(date);

        List<Intension> snapshotIntensions = BasicCopy.from(Intension.class, allIntensions);

        receipt.setIntensions(snapshotIntensions);

        receipt.setProviderId(form.getProviderId());

        receipt.setOwnerId(context.getOwnerId());

        receipt.setPubSet(pubSet);

        return receipt;

    }

}
