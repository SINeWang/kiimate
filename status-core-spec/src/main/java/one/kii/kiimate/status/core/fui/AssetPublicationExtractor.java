package one.kii.kiimate.status.core.fui;

import lombok.Data;
import one.kii.kiimate.status.core.api.PublishAssetApi;
import one.kii.summer.io.context.WriteContext;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
public interface AssetPublicationExtractor {

    String TREE_LATEST = "latest";

    String VERSION_HEAD = "HEAD";

    Informal extract(WriteContext context, PublishAssetApi.Form form);

    @Data
    class Informal {
        String providerId;
        long subId;
        String version;
        String visibility;
        String stability;
    }

    @Data
    class Formal {
        long id;
        long pubSet;
        String providerId;
        long subId;
        String version;
        String visibility;
        String stability;
    }
}
