package one.kii.kiimate.status.core.fui;

import lombok.Data;
import one.kii.kiimate.status.core.api.PublishStatusApi;
import one.kii.summer.io.context.WriteContext;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
public interface AssetPublicationExtractor {

    String TREE_LATEST = "latest";

    String VERSION_HEAD = "HEAD";

    Informal extract(WriteContext context, PublishStatusApi.Form form);

    @Data
    class Informal {
        String providerId;
        Long subId;
        String version;
        String visibility;
        String stability;
    }

    @Data
    class Formal {
        Long id;
        Long pubSet;
        String providerId;
        Long subId;
        String version;
        String visibility;
        String stability;
    }
}
