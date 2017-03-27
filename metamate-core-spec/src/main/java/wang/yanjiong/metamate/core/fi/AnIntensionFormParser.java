package wang.yanjiong.metamate.core.fi;

import lombok.Data;
import wang.yanjiong.metamate.core.api.CreateIntensionApi;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnIntensionFormParser {

    Intension parse(CreateIntensionApi.Form form);

    @Data
    class Intension {

        private String id;

        private String extId;

        private String name;

        private String visibility;

        private String structure;

        private boolean single;
    }
}
