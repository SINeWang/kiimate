package one.kii.kiimate.status.core.fui;

import lombok.Data;
import one.kii.kiimate.status.core.api.PublishAssetApi;
import one.kii.summer.io.context.WriteContext;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
public interface AssetPublicationExtractor {

    Informal extract(WriteContext context, PublishAssetApi.Form form);

    Formal supplement(Informal informal);

    @Data
    class Informal {
        String ownerId;
        String modelSubId;
        String version;
        String visibility;
        String stability;
    }

    @Data
    class Formal {
        String id;
        String pubSet;
        String ownerId;
        String modelSubId;
        String version;
        String visibility;
        String stability;
    }
}
