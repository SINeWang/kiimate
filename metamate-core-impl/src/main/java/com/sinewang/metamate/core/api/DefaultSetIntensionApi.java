package com.sinewang.metamate.core.api;

import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Receipt createIntensionViaFormUrlEncoded(Form form, HttpServletRequest request) {

        AnIntensionExtractor.Intension intension = anIntensionExtractor.parse(form);

        IntensionDai.Intension daiRecord = new IntensionDai.Intension();
        BeanUtils.copyProperties(intension, daiRecord);

        try {
            intensionDai.insertIntension(daiRecord);
            return ResponseFactory.accepted(form, SetIntensionApi.Receipt.class, intension);
        } catch (IntensionDai.IntensionDuplicated extensionDuplicated) {
            return ResponseFactory.rejected(form, SetIntensionApi.Receipt.class, intension);
        }
    }
}
