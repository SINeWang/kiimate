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


    @Override
    public ResponseEntity<IntensionReceipt> declarePropViaFormUrlEncoded2(
            @RequestHeader("X-SUMMER-RequestId") String requestId,
            @ModelAttribute IntensionForm intensionForm,
            @RequestHeader("X-MM-OwnerId") String ownerId,
            @RequestHeader(value = "X-MM-OperatorId", required = false) String operatorId) {

        AnIntensionExtractor.Intension intension = anIntensionExtractor.parseForm(intensionForm);

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
