package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;

import java.util.Map;

/**
 * Created by WangYanJiong on 23/05/2017.
 */
public interface VisitRawAssetApi {


    String STABILITY_LATEST = "latest";

    String VERSION_HEAD = "HEAD";

    Map<String, Object> visit(ReadContext context, PubSetForm form) throws NotFound;

    Map<String, Object> visit(ReadContext context, GroupNameForm form) throws NotFound;

    @Data
    class PubSetForm {
        String pubSet;
        String stability;
        String version;
    }

    @Data
    class GroupNameForm {
        String group;
        String name;
        String stability = STABILITY_LATEST;
        String version = VERSION_HEAD;
    }

}
