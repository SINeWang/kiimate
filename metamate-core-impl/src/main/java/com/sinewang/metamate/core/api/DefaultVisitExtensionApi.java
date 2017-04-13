package com.sinewang.metamate.core.api;

import com.google.common.base.CaseFormat;
import one.kii.summer.erest.ErestHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.VisitExtensionApi;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnModelRestorer;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */
@RestController
@RequestMapping("/v1")
public class DefaultVisitExtensionApi implements VisitExtensionApi {

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Autowired
    private AnModelRestorer modelRestorer;

    @Override
    @RequestMapping(value = "/{ownerId}/extension/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    public Map<String, Object> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {
        group = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, group);
        name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, name);
        tree = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, tree);

        String extId = extensionExtractor.hashId(ownerId, group, name, tree, VISIBILITY_PUBLIC);

        return modelRestorer.fullRestoreAsMap(extId);
    }


    @Override
    @RequestMapping(value = "/{ownerId}/extension/{group}", method = RequestMethod.GET)
    public Map<String, Object> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group) {
        group = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, group);

        String extId = extensionExtractor.hashId(ownerId, group, NAME_ROOT, TREE_MASTER, VISIBILITY_PUBLIC);
        return modelRestorer.fullRestoreAsMap(extId);
    }

    @Override
    @RequestMapping(value = "/{ownerId}/extension/{group}/{name}", method = RequestMethod.GET)
    public Map<String, Object> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name) {
        group = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, group);
        name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, name);

        String extId = extensionExtractor.hashId(ownerId, group, name, TREE_MASTER, VISIBILITY_PUBLIC);
        return modelRestorer.fullRestoreAsMap(extId);
    }
}
