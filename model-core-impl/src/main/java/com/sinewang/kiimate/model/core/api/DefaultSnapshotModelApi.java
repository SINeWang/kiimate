package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.SnapshotModelApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.kiimate.model.core.fui.AnIntensionExtractor;
import one.kii.kiimate.model.core.fui.AnPublicationExtractor;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.codec.utils.HashTools;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
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
public class DefaultSnapshotModelApi implements SnapshotModelApi {

    private static final String SNAPSHOT = "commit";
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

    @Autowired
    private AnIntensionExtractor intensionExtractor;

    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict {

        ExtensionDai.Extension extension = extensionDai.selectExtensionById(form.getExtId());
        List<ModelPublicationDai.Publication> publications = new ArrayList<>();
        List<IntensionDai.Intension> allIntensions = new ArrayList<>();

        Date date = new Date();
        List<String> ids = new ArrayList<>();
        List<AnExtensionExtractor.Extension> newExtensions = new ArrayList<>();
        AnExtensionExtractor.Extension newExtension = DataTools.copy(extension, AnExtensionExtractor.Extension.class);
        String tree = SNAPSHOT + "-" + form.getVersion();
        newExtension.setTree(tree);
        newExtensions.add(newExtension);

        AnPublicationExtractor.Publication snapshot;
        extensionExtractor.hashId(newExtension);
        try {
            snapshot = publicationExtractor.extractSnapshot(form, newExtension.getId(), context.getOperatorId(), date);
        } catch (AnPublicationExtractor.MissingParamException e) {
            throw new BadRequest(e.getMessage());
        }

        List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extension.getId());

        for (IntensionDai.Intension intension : intensions) {
            intension.setExtId(newExtension.getId());
            intension.setId(intensionExtractor.hashId(newExtension.getId(), intension.getField()));
        }

        allIntensions.addAll(intensions);

        final String snapExtId = publicationExtractor.hashPublishExtId(snapshot.getProviderId(), newExtension.getId());

        for (IntensionDai.Intension intension : intensions) {
            String id = publicationExtractor.hashId(snapExtId, intension.getId());
            ids.add(id);
            ModelPublicationDai.Publication daiPublication = DataTools.copy(snapshot, ModelPublicationDai.Publication.class);
            daiPublication.setPublication(SNAPSHOT);
            daiPublication.setIntId(intension.getId());
            daiPublication.setId(id);
            daiPublication.setBeginTime(snapshot.getCreatedAt());
            publications.add(daiPublication);
        }

        String[] idArray = ids.toArray(new String[0]);
        Arrays.sort(idArray);
        String pubSet = HashTools.hashHex(idArray);

        try {
            List<ExtensionDai.Extension> newExtensionList = DataTools.copy(newExtensions, ExtensionDai.Extension.class);
            modelPublicationDai.savePublications(pubSet, publications, newExtensionList, allIntensions);
        } catch (ModelPublicationDai.DuplicatedPublication duplicatedPublication) {
            Receipt receipt = DataTools.copy(duplicatedPublication, Receipt.class);
            receipt.setVersion(form.getVersion());
            receipt.setOwnerId(context.getOwnerId());
            throw new Conflict(pubSet);
        }

        Receipt receipt = new Receipt();

        receipt.setVersion(form.getVersion());

        receipt.setCreatedAt(date);

        List<Intension> snapshotIntensions = DataTools.copy(allIntensions, Intension.class);

        receipt.setIntensions(snapshotIntensions);

        receipt.setProviderId(form.getProviderId());

        receipt.setOwnerId(context.getOwnerId());

        receipt.setPubSet(pubSet);

        return receipt;

    }

}
