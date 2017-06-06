package com.sinewang.kiimate.model.core.fui;

import com.google.common.base.CaseFormat;
import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.summer.beans.utils.HashTools;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by WangYanJiong on 25/03/2017.
 */

@Component
public class DefaultExtensionExtractor implements AnExtensionExtractor {

    private static final Eid64Generator idgen = new Eid64Generator(0);

    @Override
    public ExtensionDai.Record extract(WriteContext context, DeclareExtensionApi.CommitForm form) throws BadRequest, Panic {
        NotBadRequest.from(form);
        form.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getGroup()));
        form.setName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getName()));
        form.setTree(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getTree()));
        form.setVisibility(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getVisibility()));
        ExtensionDai.Record record = ValueMapping.from(ExtensionDai.Record.class, form, context);
        try {
            String visibility = form.getVisibility();
            Visibility.valueOf(visibility.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequest("visibility");
        }
        hash(record);
        record.setId(idgen.born());
        return NotBadResponse.of(record);
    }

    private void hash(ExtensionDai.Record record) {
        record.setBeginTime(new Date());
        record.setOwnerId(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, record.getOwnerId()));
        record.setOperatorId(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, record.getOperatorId()));
        record.setCommit(HashTools.hashHex(record));
    }


}
