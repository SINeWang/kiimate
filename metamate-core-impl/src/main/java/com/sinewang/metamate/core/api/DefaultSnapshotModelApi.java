package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.SnapshotModelApi;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.dai.ModelPublicationDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnPublicationExtractor;
import wang.yanjiong.metamate.core.fi.AnPublicationValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@RestController
@RequestMapping("/v1")
public class DefaultSnapshotModelApi implements SnapshotModelApi {

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


    @RequestMapping(value = "{ownerId}/snapshot/{group}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Receipt> snapshot(
            @ModelAttribute Form form,
            @RequestHeader("X-MM-OperatorId") String operatorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group) throws RefereceExtensionHasNotBeenPublished {


        List<ExtensionDai.Extension> extensions = extensionDai.selectExtensionsByOwnerGroup(ownerId, group);
        List<ModelPublicationDai.Publication> publications = new ArrayList<>();
        List<IntensionDai.Intension> allIntensions = new ArrayList<>();

        Date date = new Date();
        for (ExtensionDai.Extension extension : extensions) {
            AnPublicationExtractor.Publication snapshot;
            String extId = extensionExtractor.hashId(ownerId, group, extension.getName(), TREE_MASTER);
            try {
                snapshot = publicationExtractor.extractSnapshot(form, extId, operatorId, date);
            } catch (AnPublicationExtractor.MissingParamException e) {
                return ResponseFactory.badRequest(e.getMessage());
            }

            List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extId);
            allIntensions.addAll(intensions);

            String pubExtId = publicationExtractor.hashPubExtId(snapshot.getProviderId(), extId, snapshot.getPublication(), snapshot.getVersion());

            for (IntensionDai.Intension intension : intensions) {

                String id = publicationExtractor.hashId(pubExtId, intension.getId());

                ModelPublicationDai.Publication daiPublication = DataTools.copy(snapshot, ModelPublicationDai.Publication.class);
                daiPublication.setPublication(AnPublicationValidator.Publication.SNAPSHOT.name());
                daiPublication.setIntId(intension.getId());
                daiPublication.setId(id);
                daiPublication.setCreatedAt(snapshot.getCreatedAt());
                publications.add(daiPublication);
            }
        }


        modelPublicationDai.savePublications(publications);

        Receipt receipt = new Receipt();

        receipt.setVersion(form.getVersion());

        receipt.setCreatedAt(date);

        List<Intension> snapshotIntensions = DataTools.copy(allIntensions, Intension.class);

        receipt.setIntensions(snapshotIntensions);

        receipt.setProviderId(form.getProviderId());

        receipt.setOwnerId(ownerId);

        return ResponseFactory.accepted(receipt, form.getProviderId());

    }

}
