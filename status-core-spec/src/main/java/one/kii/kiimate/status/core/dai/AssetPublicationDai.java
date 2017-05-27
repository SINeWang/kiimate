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

        String pubSet;

        List<Entry> entries;

        LoadAssetsDai.Assets previous;
    }

    @Data
    class Entry {
        String id;

        String providerId;

        String modelSubId;

        String insId;

        String version;

        String visibility;

        String stability;
    }


}
