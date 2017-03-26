package wang.yanjiong.metamate.core.api;

import lombok.Data;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface CreateIntensionApi {

    @Data
    class Intension {

        private String id;

        private String extensionId;

        private String referenceId;

        private String rename;

        private boolean single;

        private String dataTypeId;

        private String visibility;

    }
}
