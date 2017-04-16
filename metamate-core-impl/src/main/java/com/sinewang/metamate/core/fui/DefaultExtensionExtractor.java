package com.sinewang.metamate.core.fui;

import com.google.common.base.CaseFormat;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.codec.utils.HashTools;
import one.kii.summer.io.exception.BadRequest;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.api.DeclareExtensionApi;
import wang.yanjiong.metamate.core.fui.AnExtensionExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Service
public class DefaultExtensionExtractor implements AnExtensionExtractor {

    @Override
    public Extension extract(DeclareExtensionApi.CommitForm commitForm) throws BadRequest {

        List<String> fields = new ArrayList<>();

        if (commitForm.getGroup() == null || commitForm.getGroup().isEmpty()) {
            fields.add("group");
        }
        if (commitForm.getName() == null || commitForm.getName().isEmpty()) {
            fields.add("name");
        }
        if (commitForm.getTree() == null || commitForm.getTree().isEmpty()) {
            fields.add("tree");
        }
        if (commitForm.getVisibility() == null || commitForm.getVisibility().isEmpty()) {
            fields.add("visibility");
        }
        if (!fields.isEmpty()) {
            throw new BadRequest(fields.toArray(new String[0]));
        }
        commitForm.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, commitForm.getGroup()));
        commitForm.setName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, commitForm.getName()));
        commitForm.setTree(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, commitForm.getTree()));

        Extension extension = DataTools.copy(commitForm, Extension.class);
        String id = hashExtension(extension);
        extension.setId(id);
        return extension;
    }

    @Override
    public String hashId(String ownerId, String group, String name, String tree, String visibility) {
        group = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, group);
        name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, name);
        tree = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, tree);

        return HashTools.hashHex(ownerId, group, name, tree, visibility);
    }

    private String hashExtension(Extension extension) {
        return hashId(extension.getOwnerId(), extension.getGroup(), extension.getName(), extension.getTree(), extension.getVisibility());
    }


}
