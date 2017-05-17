package one.kii.kiimate.model.core.dai;

import lombok.Data;
import lombok.Getter;
import one.kii.summer.codec.annotations.HashFactor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface IntensionDai {


    @Transactional
    void insertIntension(Intension intension) throws IntensionDuplicated;

    List<Intension> selectIntensionsByExtId(String extId);

    void removeIntension(String intId);

    @Data
    class Intension {

        private String id;

        @HashFactor
        private String extId;

        @HashFactor
        private String field;

        private boolean single;

        private String structure;

        private String refExtId;

        private String visibility;

        private boolean required;
    }

    class IntensionDuplicated extends Exception {

        @Getter
        private String intId;

        public IntensionDuplicated(String intId) {
            this.intId = intId;
        }
    }
}
