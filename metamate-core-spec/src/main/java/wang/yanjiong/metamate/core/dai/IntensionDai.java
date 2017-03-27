package wang.yanjiong.metamate.core.dai;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface IntensionDai {


    @Transactional
    void insertIntension(Intension intension) throws IntensionDuplicated;

    List<Intension> selectIntensionsByExtId(String extId);

    @Data
    class Intension {

        private String id;

        private String extId;

        private String name;

        private boolean single;

        private String structure;

        private String visibility;


    }

    class IntensionDuplicated extends Exception {
        public IntensionDuplicated(String message, Throwable e) {
            super(message, e);
        }
    }
}
