package wang.yanjiong.metamate.core.dai;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
public interface PublicationDai {

    @Transactional
    void savePublication(Publication publication);

    @Data
    class Publication {
        String id;
        String ownerId;
        String extId;
        String intId;
        String version;
        String publication;
        String operatorId;
        Date createdAt;
    }
}
