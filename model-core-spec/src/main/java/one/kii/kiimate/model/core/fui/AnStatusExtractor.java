package one.kii.kiimate.model.core.fui;

import one.kii.kiimate.model.core.api.SubscribeModelsApi;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Panic;

/**
 * Created by WangYanJiong on 4/6/17.
 */

public interface AnStatusExtractor {

    ModelSubscriptionDai.Instance extract(SubscribeModelsApi.Form form, WriteContext context) throws Panic;

}
