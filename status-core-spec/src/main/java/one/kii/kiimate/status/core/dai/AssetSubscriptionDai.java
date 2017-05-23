package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.KeyFactor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 22/05/2017.
 */
public interface AssetSubscriptionDai {


    @Transactional
    void save(Record record);

    @Data
    class Record {
        String id;

        @KeyFactor
        String subscriberId;

        @KeyFactor
        String subSet;

        String operatorId;

        @KeyFactor
        Date beginTime;
    }

    @Data
    class Subscribers {
        String id;
    }
}
