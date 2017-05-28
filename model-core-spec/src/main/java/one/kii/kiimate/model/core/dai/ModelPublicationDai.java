package one.kii.kiimate.model.core.dai;

import lombok.Data;
import lombok.Getter;
import one.kii.summer.beans.annotations.KeyFactor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
public interface ModelPublicationDai {


    List<Publication> queryPublicationsByGroup(String group);

    List<Provider> getProviders(String query);

    @Transactional
    void save(List<Publication> publication) throws DuplicatedPublication;

    @Data
    class Provider {
        String id;
    }

    @Data
    class Publication {
        String id;

        String pubSet;

        @KeyFactor
        String providerId;

        @KeyFactor
        String extId;

        @KeyFactor
        String intId;

        @KeyFactor
        String version;

        @KeyFactor
        String stability;

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
