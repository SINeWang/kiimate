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
    Date insert(String pubSet, List<Record> records);

    List<Owners> queryOwners(String ownerId);

    List<Assets> queryAssets(String ownerId, String group);

    @Data
    class Record {
        String id;
        String ownerId;
        String modelSubId;
        String insId;
        String version;
        String visibility;
        String stability;
    }

    @Data
    class Owners {
        String id;
    }

    @Data
    class Assets {
        String pubSet;
        String ownerId;
        String visibility;
        String group;
        String name;
        String stability;
        String version;
    }
}
