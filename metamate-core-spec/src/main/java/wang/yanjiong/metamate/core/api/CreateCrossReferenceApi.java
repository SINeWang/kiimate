package wang.yanjiong.metamate.core.api;

import lombok.Data;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface CreateCrossReferenceApi {

    @Data
    class CrossReference {

        private String id;

        private String excludeName;

        private String includeName;

        private String intensionId;

        private String referenceId;

    }

}
