package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.DeclareIntensionApi;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.fi.AnIntensionExtractor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@RestController
@RequestMapping("/v1")
public class DefaultDeclareIntensionApi implements DeclareIntensionApi {


    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnIntensionExtractor anIntensionExtractor;

    @RequestMapping(value = "/intension/{group}/{name}/{tree:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<IntensionReceipt> declarePropViaFormUrlEncoded1(
            @ModelAttribute IntensionForm1 intensionForm,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree,
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader(value = "X-SUMMER-OperatorId", required = false) String operatorId,
            HttpServletRequest request) {
        AnIntensionExtractor.Intension intension = anIntensionExtractor.parseForm1(ownerId, group, name, tree, intensionForm);

        return getIntensionReceiptResponseEntity(ownerId, intension);
    }

    @Override
    public ResponseEntity<IntensionReceipt> declarePropViaFormUrlEncoded2(
            @ModelAttribute IntensionForm2 intensionForm,
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader(value = "X-SUMMER-OperatorId", required = false) String operatorId,
            HttpServletRequest request) {

        AnIntensionExtractor.Intension intension = anIntensionExtractor.parseForm2(intensionForm);

        return getIntensionReceiptResponseEntity(ownerId, intension);
    }


    private ResponseEntity<IntensionReceipt> getIntensionReceiptResponseEntity(String ownerId, AnIntensionExtractor.Intension intension) {
        IntensionDai.Intension daiRecord = DataTools.copy(intension, IntensionDai.Intension.class);
        try {
            intensionDai.insertIntension(daiRecord);
            IntensionReceipt intensionReceipt = DataTools.copy(daiRecord, IntensionReceipt.class);
            return ResponseFactory.accepted(intensionReceipt, ownerId);
        } catch (IntensionDai.IntensionDuplicated extensionDuplicated) {
            return ResponseFactory.badRequest(extensionDuplicated.getMessage());
        }
    }

}
