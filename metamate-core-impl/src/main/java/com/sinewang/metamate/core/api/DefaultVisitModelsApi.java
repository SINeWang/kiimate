package com.sinewang.metamate.core.api;

import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.VisitModelsApi;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnStructureValidator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */
@RestController
@RequestMapping("/v1")
public class DefaultVisitModelsApi implements VisitModelsApi {

    @Autowired
    private ExtensionDai extensionDai;


    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Override
    @RequestMapping(value = "/{ownerId}/models/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader(value = "X-SUMMER-VisitorId", required = false) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree,
            @RequestParam(value = "tag", defaultValue = "LATEST") String tag) {

        String extId = extensionExtractor.hashId(ownerId, group, name, tree);


        return ResponseFactory.accepted(restoreModel(extId), ownerId);
    }

    private Map<String, Object> restoreModel(String extId) {
        if (extId == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> model = new HashMap<>();
        List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extId);
        for (IntensionDai.Intension intension : intensions) {
            if (AnStructureValidator.Structure.COMPLEX.name().equals(intension.getStructure().toUpperCase())) {
                String refExtId = intension.getRefExtId();
                model.put(intension.getField(), restoreModel(refExtId));
            } else {
                model.put(intension.getField(), intension.getStructure());
            }
        }
        return model;
    }
}
