package wang.yanjiong.metamate.core.fi;

import lombok.Data;
import wang.yanjiong.metamate.core.api.CreateExtensionApi;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface ExtensionFi {

    Extension accept(CreateExtensionApi.Form form);

    @Data
    class Extension {

        private String id;

        private String group;

        private String name;

        private String version;

        private String visibility;

        private String dataStructure;
    }


}
