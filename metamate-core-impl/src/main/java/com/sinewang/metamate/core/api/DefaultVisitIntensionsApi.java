package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.erest.ErestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import wang.yanjiong.metamate.core.api.VisitIntensionsApi;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

public class DefaultVisitIntensionsApi implements VisitIntensionsApi {

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnExtensionExtractor anExtensionExtractor;


    @Override
    public ResponseEntity<Extension> readIntensionsByGroupNameVersion(
            String requestId,
            String visitorId,
            String ownerId,
            String group,
            String name,
            String tree) {

        String extId = anExtensionExtractor.hashId(ownerId, group, name, tree, VISIBILITY_PUBLIC);
        Extension extension = new Extension();
        extension.setExtId(extId);

        List<IntensionDai.Intension> list = intensionDai.selectIntensionsByExtId(extId);

        List<Intension> intensions = DataTools.copy(list, Intension.class);

        extension.setIntensions(intensions);
        return ErestResponse.ok(requestId, extension);
    }
}
