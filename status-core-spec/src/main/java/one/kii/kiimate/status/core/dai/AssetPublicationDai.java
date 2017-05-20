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
}
