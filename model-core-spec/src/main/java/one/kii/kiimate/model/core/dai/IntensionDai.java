package one.kii.kiimate.model.core.dai;

import lombok.Data;
import lombok.Getter;
import one.kii.summer.beans.annotations.KeyFactor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface IntensionDai {


    @Transactional
    void insertIntension(Intension intension) throws IntensionDuplicated;

    List<Intension> loadLatestIntensions(ChannelExtension channel);

    List<Intension> loadLastIntensions(ChannelPubSet channel);

    void removeIntension(long intId);

    @Data
    class ChannelExtension {

        long id;

        Date beginTime;

        Date endTime;
    }

    @Data
    class ChannelPubSet {

        long id;

        long pubSet;

        Date beginTime;

        Date endTime;
    }


    @Data
    class Intension {

        private long id;

        @KeyFactor
        private long extId;

        @KeyFactor
        private String field;

        private boolean single;

        private String structure;

        private long refPubSet;

        private String visibility;

        private boolean required;
    }

    class IntensionDuplicated extends Exception {

        @Getter
        private long intId;

        public IntensionDuplicated(long intId) {
            this.intId = intId;
        }
    }
}
