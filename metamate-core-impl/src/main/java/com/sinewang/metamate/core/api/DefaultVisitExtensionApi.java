package com.sinewang.metamate.core.api;

import com.google.common.base.CaseFormat;
import one.kii.summer.erest.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.VisitExtensionApi;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

import java.util.*;

/**
 * Created by WangYanJiong on 4/5/17.
 */
@RestController
@RequestMapping("/v1")
public class DefaultVisitExtensionApi implements VisitExtensionApi {

    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Override
    @RequestMapping(value = "/{ownerId}/extension/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            @RequestHeader("X-MM-VisitorId") String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {
        group = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, group);
        name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, name);
        tree = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, tree);

        String extId = extensionExtractor.hashId(ownerId, group, name, tree);
        return Response.accepted(restoreModel(extId), ownerId);
    }

    private List toArray(Object o) {
        List list = new ArrayList();
        list.add(o);
        return list;
    }

    private Map<String, Object> restoreModel(String extId) {
        if (extId == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> model = new HashMap<>();
        List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extId);
        for (IntensionDai.Intension intension : intensions) {
            String refExtId = intension.getRefExtId();
            if (refExtId != null) {
                if (intension.isSingle()) {
                    model.put(intension.getField(), restoreModel(refExtId));
                } else {
                    model.put(intension.getField(), toArray(restoreModel(refExtId)));
                }
            } else {
                if (intension.isSingle()) {
                    model.put(intension.getField(), intension.getStructure());
                } else {
                    model.put(intension.getField(), toArray(intension.getStructure()));
                }
            }
        }
        return model;
    }

    @Override
    @RequestMapping(value = "/{ownerId}/extension/{group}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            @RequestHeader("X-MM-VisitorId") String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group) {
        group = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, group);

        String extId = extensionExtractor.hashId(ownerId, group, NAME_ROOT, TREE_MASTER);
        return Response.accepted(restoreModel(extId), ownerId);
    }

    @Override
    @RequestMapping(value = "/{ownerId}/extension/{group}/{name}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            @RequestHeader("X-MM-VisitorId") String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name) {
        group = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, group);
        name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, name);

        String extId = extensionExtractor.hashId(ownerId, group, name, TREE_MASTER);
        return Response.accepted(restoreModel(extId), ownerId);
    }
}
