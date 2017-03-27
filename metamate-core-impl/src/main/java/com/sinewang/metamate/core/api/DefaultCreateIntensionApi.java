package com.sinewang.metamate.core.api;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.magnet.xi.boundary.util.ResponseUtil;
import wang.yanjiong.metamate.core.api.CreateIntensionApi;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.fi.AnIntensionExtractor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@RestController
public class DefaultCreateIntensionApi implements CreateIntensionApi {


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
            return ResponseUtil.accepted(form, CreateIntensionApi.Receipt.class, intension);
        } catch (IntensionDai.IntensionDuplicated extensionDuplicated) {
            return ResponseUtil.rejected(form, CreateIntensionApi.Receipt.class, intension);
        }
    }
}
