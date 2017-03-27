package wang.yanjiong.metamate.core.fi;

import lombok.Data;
import wang.yanjiong.metamate.core.api.CreateIntensionApi;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnIntensionExtractor {

    Intension parse(CreateIntensionApi.Form form);

    String hashId(String extId, String field);

    @Data
    class Intension {

        private String id;

        private String extId;

        private String field;

        private String visibility;

        private String structure;

        private boolean single;
    }
}
