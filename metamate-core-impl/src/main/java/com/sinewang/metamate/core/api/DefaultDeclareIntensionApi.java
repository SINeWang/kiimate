package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.erest.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
            @RequestHeader("X-SUMMER-RequestId") String requestId,
            @ModelAttribute Form form,
            @RequestHeader("X-MM-OwnerId") String ownerId,
            @RequestHeader(value = "X-MM-OperatorId", required = false) String operatorId) {

        AnIntensionExtractor.Intension intension = anIntensionExtractor.parseForm(form);
        IntensionDai.Intension daiRecord = DataTools.copy(intension, IntensionDai.Intension.class);
        try {
            intensionDai.insertIntension(daiRecord);
            Receipt receipt = DataTools.copy(daiRecord, Receipt.class);
            return Response.accepted(requestId, receipt, ownerId);
        } catch (IntensionDai.IntensionDuplicated extensionDuplicated) {
            Receipt receipt = DataTools.copy(daiRecord, Receipt.class);
            return Response.accepted(requestId, receipt, ownerId);
        }

    }

}
