package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.erest.ErestHeaders;
import one.kii.summer.erest.ErestResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Receipt> declarePropViaFormUrlEncoded2(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable("ownerId") String ownerId,
            @ModelAttribute Form form) {

        AnIntensionExtractor.Intension intension = anIntensionExtractor.parseForm(form);
        IntensionDai.Intension daiRecord = DataTools.copy(intension, IntensionDai.Intension.class);
        try {
            intensionDai.insertIntension(daiRecord);
            Receipt receipt = DataTools.copy(daiRecord, Receipt.class);
            return ErestResponse.created(requestId, receipt);
        } catch (IntensionDai.IntensionDuplicated extensionDuplicated) {
            Receipt receipt = DataTools.copy(daiRecord, Receipt.class);
            return ErestResponse.created(requestId, receipt);
        }

    }

}
