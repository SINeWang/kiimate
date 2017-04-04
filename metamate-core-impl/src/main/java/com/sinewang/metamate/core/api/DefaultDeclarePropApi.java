package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.DeclarePropApi;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.fi.AnIntensionExtractor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@RestController
public class DefaultDeclarePropApi implements DeclarePropApi {


    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnIntensionExtractor anIntensionExtractor;

    @Override
    public ResponseEntity<PropReceipt> declarePropViaFormUrlEncoded(PropForm propForm, String ownerId, HttpServletRequest request) {

        AnIntensionExtractor.Intension intension = anIntensionExtractor.parse(propForm);

        IntensionDai.Intension daiRecord = DataTools.copy(intension, IntensionDai.Intension.class);

        try {
            intensionDai.insertIntension(daiRecord);
            PropReceipt propReceipt = DataTools.copy(daiRecord, PropReceipt.class);
            return ResponseFactory.accepted(propReceipt, ownerId);
        } catch (IntensionDai.IntensionDuplicated extensionDuplicated) {
            return ResponseFactory.badRequest(extensionDuplicated.getMessage());
        }
    }
}
