package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.codec.utils.HashTools;
import one.kii.summer.erest.Response;
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
import java.util.Arrays;
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


    @RequestMapping(value = "{ownerId}/snapshot/{group:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Receipt> snapshot(
            @RequestHeader("X-SUMMER-RequestId") String requestId,
            @RequestHeader("X-MM-OperatorId") String operatorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @ModelAttribute Form form) throws RefereceExtensionHasNotBeenPublished {


        List<ExtensionDai.Extension> extensions = extensionDai.selectExtensionsByOwnerGroup(ownerId, group);
        List<ModelPublicationDai.Publication> publications = new ArrayList<>();
        List<IntensionDai.Intension> allIntensions = new ArrayList<>();

        Date date = new Date();
        List<String> ids = new ArrayList<>();
        for (ExtensionDai.Extension extension : extensions) {
            AnPublicationExtractor.Publication snapshot;
            String extId = extensionExtractor.hashId(ownerId, group, extension.getName(), TREE_MASTER, VISIBILITY_PUBLIC);
            try {
                snapshot = publicationExtractor.extractSnapshot(form, extId, operatorId, date);
            } catch (AnPublicationExtractor.MissingParamException e) {
                return Response.badRequest(requestId, e.getMessage());
            }

            List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extId);
            allIntensions.addAll(intensions);

            String pubExtId = publicationExtractor.hashPubExtId(snapshot.getProviderId(), extId, snapshot.getPublication(), snapshot.getVersion());

            for (IntensionDai.Intension intension : intensions) {

                String id = publicationExtractor.hashId(pubExtId, intension.getId());
                ids.add(id);
                ModelPublicationDai.Publication daiPublication = DataTools.copy(snapshot, ModelPublicationDai.Publication.class);
                daiPublication.setPublication(AnPublicationValidator.Publication.SNAPSHOT.name());
                daiPublication.setIntId(intension.getId());
                daiPublication.setId(id);
                daiPublication.setBeginTime(snapshot.getCreatedAt());
                publications.add(daiPublication);
            }
        }
        String[] idArray = ids.toArray(new String[0]);
        Arrays.sort(idArray);
        String pubSetHash = HashTools.hashHex(idArray);

        try {
            modelPublicationDai.savePublications(pubSetHash, publications);
        } catch (ModelPublicationDai.DuplicatedPublication duplicatedPublication) {
            Receipt receipt = DataTools.copy(duplicatedPublication, Receipt.class);
            receipt.setVersion(form.getVersion());
            receipt.setOwnerId(ownerId);
            return Response.conflict(requestId, receipt);
        }

        Receipt receipt = new Receipt();

        receipt.setVersion(form.getVersion());

        receipt.setCreatedAt(date);

        List<Intension> snapshotIntensions = DataTools.copy(allIntensions, Intension.class);

        receipt.setIntensions(snapshotIntensions);

        receipt.setProviderId(form.getProviderId());

        receipt.setOwnerId(ownerId);

        receipt.setPubSetHash(pubSetHash);

        return Response.created(requestId, receipt);

    }

}
