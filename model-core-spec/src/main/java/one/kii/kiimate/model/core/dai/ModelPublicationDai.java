package one.kii.kiimate.model.core.dai;

import lombok.Data;
import lombok.Getter;
import one.kii.summer.beans.annotations.KeyFactor;
import one.kii.summer.io.annotations.MustHave;
import one.kii.summer.io.exception.NotFound;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
public interface ModelPublicationDai {


    List<PublishedExtension> searchExtension(ClueGroup clue);

    List<PublishedSnapshot> loadSnapshot(ChannelId channel);

    Publication loadRootPublications(ChannelPubSet channel) throws NotFound;

    List<Provider> searchProviders(ClueId clue);


    @Transactional
    void save(List<Publication> publication) throws DuplicatedPublication;

    @Data
    class ClueGroup {
        String group;
    }

    @Data
    class ChannelPubSet {

        @MustHave
        long pubSet;
    }

    @Data
    class ChannelId {
        long id;
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
        Long id;

        Long pubSet;

        @KeyFactor
        String providerId;

        @KeyFactor
        Long extId;

        @KeyFactor
        Long intId;

        @KeyFactor
        String version;

        @KeyFactor
        String stability;

        String operatorId;

        Date beginTime;
    }

    @Data
    class PublishedExtension {
        Long id;

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
        Long pubSet;
        String stability;
        String version;
        Date beginTime;
    }


    class DuplicatedPublication extends Exception {

        @Getter
        private long pubSet;

        public DuplicatedPublication(long pubSet) {
            this.pubSet = pubSet;
        }
    }
}
