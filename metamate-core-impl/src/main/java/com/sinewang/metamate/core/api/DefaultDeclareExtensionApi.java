package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.DeclareExtensionApi;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnStructureValidator;
import wang.yanjiong.metamate.core.fi.AnVisibilityValidator;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RestController
public class DefaultDeclareExtensionApi implements DeclareExtensionApi {


    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Autowired
    private AnStructureValidator structureValidator;

    @Autowired
    private AnVisibilityValidator visibilityValidator;

    @Override
    public ResponseEntity<ExtensionReceipt> declareByFormUrlEncoded(
            @ModelAttribute ExtensionForm extensionForm,
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader(value = "X-SUMMER-VisitorId", required = false) String visitorId) {

        AnExtensionExtractor.Extension extension;
        try {
            extension = extensionExtractor.extract(extensionForm, ownerId);
        } catch (AnExtensionExtractor.MissingParamException e) {
            return ResponseFactory.badRequest(e.getMessage());
        }

        boolean isValidStructure = structureValidator.isValid(extension.getStructure());
        if (!isValidStructure) {
            return ResponseFactory.badRequest("invalid Structure");
        }

        boolean isValidVisibility = visibilityValidator.isValid(extension.getVisibility());
        if (!isValidVisibility) {
            return ResponseFactory.badRequest("invalid visibility");
        }

        ExtensionDai.Extension daiExtension = DataTools.copy(extension, ExtensionDai.Extension.class);

        try {
            extensionDai.insertExtension(daiExtension);
            ExtensionReceipt extensionReceipt = DataTools.copy(daiExtension, ExtensionReceipt.class);
            return new ResponseEntity<>(extensionReceipt, HttpStatus.ACCEPTED);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            return ResponseFactory.badRequest(extensionDuplicated.getMessage());
        }
    }


}
