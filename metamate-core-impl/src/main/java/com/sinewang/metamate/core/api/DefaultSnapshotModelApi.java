package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.SnapshotModelApi;
import wang.yanjiong.metamate.core.dai.PublicationDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnPublicationExtractor;
import wang.yanjiong.metamate.core.fi.AnPublicationValidator;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@RestController
@RequestMapping("/v1")
public class DefaultSnapshotModelApi implements SnapshotModelApi {


    @Autowired
    private PublicationDai publicationDai;

    @Autowired
    private AnPublicationExtractor publicationExtractor;

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @RequestMapping(value = "/snapshot/{group}/{name}/{tree:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Receipt> snapshot(
            @ModelAttribute Form form,
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader("X-SUMMER-OperatorId") String operatorId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {

        AnPublicationExtractor.Publication extratorPublication;
        try {
            String extId = extensionExtractor.hashId(ownerId, group, name, tree);
            extratorPublication = publicationExtractor.extractSnapshot(form, ownerId, extId, operatorId);
        } catch (AnPublicationExtractor.MissingParamException e) {
            return ResponseFactory.badRequest(e.getMessage());
        }


        PublicationDai.Publication daiPublication = DataTools.copy(extratorPublication, PublicationDai.Publication.class);

        daiPublication.setPublication(AnPublicationValidator.Publication.SNAPSHOT.name());
        publicationDai.savePublication(daiPublication);

        Receipt receipt = new Receipt();


        return ResponseFactory.accepted(receipt, ownerId);

    }
}
