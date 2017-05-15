package one.kii.kiimate.model.core.dai;

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
    void savePublications(String pubSet, List<Publication> publication, List<ExtensionDai.Extension> extensions, List<IntensionDai.Intension> intensions) throws DuplicatedPublication;

    List<Publication> getPublicationsByPubSetHash(String pubSetHash);

    List<Publication> queryPublicationsByGroup(String group);

    List<Provider> getProviders(String query);

    @Data
    class Provider {
        String providerId;
    }

    @Data
    class Publication {
        String id;
        String pubSet;
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
        private String pubSet;

        public DuplicatedPublication(String pubSet) {
            this.pubSet = pubSet;
        }
    }
}
