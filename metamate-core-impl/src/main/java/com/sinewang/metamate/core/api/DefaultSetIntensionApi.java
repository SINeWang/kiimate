package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.SetIntensionApi;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.fi.AnIntensionExtractor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@RestController
public class DefaultSetIntensionApi implements SetIntensionApi {


    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnIntensionExtractor anIntensionExtractor;

    @Override
    public ResponseEntity<Receipt> createIntensionViaFormUrlEncoded(Form form, HttpServletRequest request) {

        AnIntensionExtractor.Intension intension = anIntensionExtractor.parse(form);

        IntensionDai.Intension daiRecord = DataTools.copy(intension, IntensionDai.Intension.class);

        try {
            intensionDai.insertIntension(daiRecord);
            Receipt receipt = DataTools.copy(daiRecord, Receipt.class);
            return ResponseFactory.accepted(receipt);
        } catch (IntensionDai.IntensionDuplicated extensionDuplicated) {
            return ResponseFactory.badRequest(extensionDuplicated.getMessage());
        }
    }
}
