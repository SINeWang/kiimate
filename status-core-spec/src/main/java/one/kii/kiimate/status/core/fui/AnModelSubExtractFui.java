package one.kii.kiimate.status.core.fui;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.DeclareInstanceApi;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Panic;

/**
 * Created by WangYanJiong on 4/6/17.
 */

public interface AnModelSubExtractFui {

    ModelSubscriptionDai.Instance extract(DeclareInstanceApi.Form form, WriteContext context) throws Panic;
}
