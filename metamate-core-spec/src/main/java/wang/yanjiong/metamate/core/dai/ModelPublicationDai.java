package wang.yanjiong.metamate.core.dai;

import lombok.Data;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
public interface ModelPublicationDai {

    @Transactional
    void savePublications(String pubSetHash, List<Publication> publication) throws DuplicatedPublication;

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
        Date beginTime;
    }

    class DuplicatedPublication extends Exception {

        @Getter
        private String pubSetHash;

        public DuplicatedPublication(String pubSetHash) {
            this.pubSetHash = pubSetHash;
        }
    }
}
