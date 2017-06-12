package com.sinewang.kiimate.model.core.fui;

import com.google.common.base.CaseFormat;
import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.api.DeclareIntensionApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnIntensionExtractor;
import one.kii.summer.beans.utils.HashTools;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by WangYanJiong on 25/03/2017.
 */

@Component
public class DefaultIntensionExtractor implements AnIntensionExtractor {

    private static final Eid64Generator idgen = new Eid64Generator(1);

    @Override
    public IntensionDai.Record extract(WriteContext context, DeclareIntensionApi.Form form) throws NotFound, Panic, BadRequest {
        NotBadRequest.from(form);

        form.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getField()));

        IntensionDai.Record record = ValueMapping.from(IntensionDai.Record.class, context, form);

        record.setId(idgen.born());
        record.setCommit(HashTools.hashHex(record));
        record.setBeginTime(new Date());
        return NotBadResponse.of(record);
    }

}
