package one.kii.kiimate.model.core.fui;

import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;


/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnExtensionExtractor {


    ExtensionDai.Record extract(WriteContext context, DeclareExtensionApi.CommitForm commitForm) throws BadRequest;

    enum Visibility {
        PRIVATE,
        PROTECTED,
        PUBLIC
    }

}
