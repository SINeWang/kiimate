package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface CreateCrossReferenceApi {

    @Data
    @EqualsAndHashCode(callSuper=false)
    class CrossReference {

        private String id;

        private String excludeName;

        private String includeName;

        private String intensionId;

        private String referenceId;

    }

}
