package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.SnapshotModelApi;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.dai.ModelPublicationDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnPublicationExtractor;
import wang.yanjiong.metamate.core.fi.AnPublicationValidator;
import wang.yanjiong.metamate.core.fi.AnStructureValidator;

import java.util.ArrayList;
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
    private ModelPublicationDai modelPublicationDai;

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @RequestMapping(value = "/snapshot/{group}/{name}/{tree:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Receipt> snapshot(
            @ModelAttribute Form form,
            @RequestHeader("X-SUMMER-ProviderId") String providerId,
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader("X-SUMMER-OperatorId") String operatorId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) throws RefereceExtensionHasNotBeenPublished {

        AnPublicationExtractor.Publication snapshot;

        String extId = extensionExtractor.hashId(ownerId, group, name, tree);
        try {
            snapshot = publicationExtractor.extractSnapshot(form, providerId, extId, operatorId);
        } catch (AnPublicationExtractor.MissingParamException e) {
            return ResponseFactory.badRequest(e.getMessage());
        }

        List<ModelPublicationDai.Publication> publications = new ArrayList<>();
        List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extId);

        String pubExtId = publicationExtractor.hashPubExtId(providerId, extId, snapshot.getPublication(), snapshot.getVersion());

        for (IntensionDai.Intension intension : intensions) {

            String id = publicationExtractor.hashId(pubExtId, intension.getId());

            ModelPublicationDai.Publication daiPublication = DataTools.copy(snapshot, ModelPublicationDai.Publication.class);
            daiPublication.setPublication(AnPublicationValidator.Publication.SNAPSHOT.name());
            daiPublication.setIntId(intension.getId());
            daiPublication.setId(id);
            daiPublication.setCreatedAt(snapshot.getCreatedAt());
            publications.add(daiPublication);

            if (intension.getStructure().equals(AnStructureValidator.Structure.IMPORT.name())) {

            }
        }


        modelPublicationDai.savePublications(publications);

        Receipt receipt = new Receipt();

        receipt.setVersion(snapshot.getVersion());

        receipt.setCreatedAt(snapshot.getCreatedAt());

        List<Intension> snapshotIntensions = DataTools.copy(intensions, Intension.class);

        receipt.setIntensions(snapshotIntensions);

        receipt.setProviderId(providerId);

        receipt.setOwnerId(ownerId);

        return ResponseFactory.accepted(receipt, providerId);

    }
}
