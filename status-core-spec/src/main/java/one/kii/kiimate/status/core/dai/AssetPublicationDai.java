package one.kii.kiimate.status.core.dai;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
public interface AssetPublicationDai {

    @Transactional
    Date save(Record record);

    List<Providers> queryProviders(ClueId clue);

    @Data
    class ClueId{
        String id;
    }

    @Data
    class Providers {
        String id;

    }

    @Data
    class Record {

        Long pubSet;

        List<Entry> entries;

        LoadAssetsDai.Assets previous;

        String operatorId;
    }

    @Data
    class Entry {
        Long id;

        String providerId;

        Long subId;

        Long insId;

        String version;

        String visibility;

        String stability;

    }


}
