package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.VisitIntensionsApi;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@RestController
public class DefaultVisitIntensionsApi implements VisitIntensionsApi {

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnExtensionExtractor anExtensionExtractor;


    @Override
    public ResponseEntity<Extension> readIntensionsByGroupNameVersion(
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader(value = "X-SUMMER-VisitorId", required = false) String visitorId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {

        String extId = anExtensionExtractor.hashId(ownerId, group, name, tree);
        Extension extension = new Extension();
        extension.setExtId(extId);

        List<IntensionDai.Intension> list = intensionDai.selectIntensionsByExtId(extId);

        List<Intension> intensions = DataTools.copy(list, Intension.class);

        extension.setIntensions(intensions);
        return ResponseFactory.accepted(extension, ownerId);
    }
}
