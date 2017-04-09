package wang.yanjiong.metamate.core.dai;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
public interface ModelPublicationDai {

    @Transactional
    void savePublications(List<Publication> publication);

    List<Publication> getPublicationsByPubSetHash(String pubSetHash);

    @Data
    class Publication {
        String id;
        String pubSetHash;
        String providerId;
        String extId;
        String intId;
        String version;
        String publication;
        String operatorId;
        Date createdAt;
    }
}
