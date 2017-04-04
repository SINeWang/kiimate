package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.DeclareNameApi;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnStructureValidator;
import wang.yanjiong.metamate.core.fi.AnVisibilityValidator;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RestController
public class DefaultDeclareNameApi implements DeclareNameApi {


    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Autowired
    private AnStructureValidator structureValidator;

    @Autowired
    private AnVisibilityValidator visibilityValidator;

    @Override
    public ResponseEntity<NameReceipt> declareByFormUrlEncoded(NameForm nameForm,
                                                               String ownerId,
                                                               String operatorId) {

        AnExtensionExtractor.Extension extension;
        try {
            extension = extensionExtractor.extract(nameForm);
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
            NameReceipt nameReceipt = DataTools.copy(daiExtension, NameReceipt.class);
            return new ResponseEntity<>(nameReceipt, HttpStatus.ACCEPTED);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            return ResponseFactory.badRequest(extensionDuplicated.getMessage());
        }
    }


}
