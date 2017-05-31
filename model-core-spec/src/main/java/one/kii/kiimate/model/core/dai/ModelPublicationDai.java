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


    List<PublishedExtension> queryPublications(ClueGroup clue);

    List<PublishedSnapshot> fetchPublishedSnapshotsByExtId(ChannelId channel);

    Publication fetchPublications(ChannelPubSet channel);

    List<Provider> getProviders(ClueId clue);


    @Transactional
    void save(List<Publication> publication) throws DuplicatedPublication;

    @Data
    class ClueGroup {
        String group;
    }

    @Data
    class ChannelPubSet {
        String pubSet;
    }

    @Data
    class ChannelId {
        String id;
    }

    @Data
    class ClueId {
        String id;
    }


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

    @Data
    class PublishedExtension {
        String id;

        @KeyFactor
        String providerId;

        @KeyFactor
        String group;

        @KeyFactor
        String name;

        String operatorId;

        Date beginTime;
    }

    @Data
    class PublishedSnapshot {
        String pubSet;
        String stability;
        String version;
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
