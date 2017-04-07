package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.erest.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.DeclareExtensionApi;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnStructureValidator;
import wang.yanjiong.metamate.core.fi.AnVisibilityValidator;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RestController
@RequestMapping("/v1")
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
    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<ExtensionReceipt> declareByFormUrlEncoded(
            @RequestHeader("X-SUMMER-RequestId") String requestId,
            @ModelAttribute ExtensionForm extensionForm,
            @RequestHeader("X-MM-OwnerId") String ownerId,
            @RequestHeader(value = "X-MM-OperatorId", required = false) String visitorId) {

        AnExtensionExtractor.Extension extension;
        try {
            extension = extensionExtractor.extract(extensionForm, ownerId);
        } catch (AnExtensionExtractor.MissingParamException e) {
            return Response.badRequest(e.getMessage());
        }


        boolean isValidVisibility = visibilityValidator.isValid(extension.getVisibility());
        if (!isValidVisibility) {
            return Response.badRequest("invalid Visibility, given [" + extension.getVisibility() + "]");
        }

        ExtensionDai.Extension daiExtension = DataTools.copy(extension, ExtensionDai.Extension.class);

        try {
            extensionDai.insertExtension(daiExtension);
            ExtensionReceipt extensionReceipt = DataTools.copy(daiExtension, ExtensionReceipt.class);
            return new ResponseEntity<>(extensionReceipt, HttpStatus.ACCEPTED);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            return Response.badRequest(extensionDuplicated.getMessage());
        }
    }


}
