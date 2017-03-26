package wang.yanjiong.metamate.core.fi;

import lombok.Data;
import wang.yanjiong.metamate.core.api.CreateExtensionApi;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnExtensionFormParser {

    Extension parse(CreateExtensionApi.Form form);

    enum DataStructures {
        STRING,
        NATURE_BASE0,
        NATURE_BASE1,
        REAL,
        FLOAT,
        TIMESTAMP
    }

    @Data
    class Extension {

        private String id;

        private String group;

        private String name;

        private String version;

        private String visibility;

        private String structure;
    }
}
