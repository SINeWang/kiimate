package one.kii.kiimate.model.core.dai;

import lombok.Data;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface IntensionDai {


    @Transactional
    void insertIntension(Intension intension) throws IntensionDuplicated;

    List<Intension> selectIntensionsByExtId(String extId);

    void deleteIntensionsByExitId(String extId);

    @Data
    class Intension {

        private String id;

        private String extId;

        private String field;

        private boolean single;

        private String structure;

        private String refExtId;

        private String visibility;

    }

    class IntensionDuplicated extends Exception {

        @Getter
        private String intId;

        public IntensionDuplicated(String intId) {
            this.intId = intId;
        }
    }
}
