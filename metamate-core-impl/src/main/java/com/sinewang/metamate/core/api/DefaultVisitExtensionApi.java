package com.sinewang.metamate.core.api;

import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.VisitExtensionApi;
import wang.yanjiong.metamate.core.fui.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fui.AnModelRestorer;

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
    public Map<String, Object> readExtensionByGroupNameVersion(ReadContext context, Form form) {
        String extId = extensionExtractor.hashId(context.getOwnerId(), form.getGroup(), form.getName(), form.getTree(), VISIBILITY_PUBLIC);
        return modelRestorer.fullRestoreAsMap(extId);
    }


    @Override
    public Map<String, Object> readExtensionByGroupNameVersion(ReadContext context, TinyForm form) {

        String extId = extensionExtractor.hashId(context.getOwnerId(), form.getGroup(), NAME_ROOT, TREE_MASTER, VISIBILITY_PUBLIC);
        return modelRestorer.fullRestoreAsMap(extId);
    }

    @Override
    public Map<String, Object> readExtensionByGroupNameVersion(ReadContext context, SimpleForm form) {
        String extId = extensionExtractor.hashId(context.getOwnerId(), form.getGroup(), form.getName(), TREE_MASTER, VISIBILITY_PUBLIC);
        return modelRestorer.fullRestoreAsMap(extId);
    }
}
