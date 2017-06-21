package one.kii.kiimate.model.core.dai;

import lombok.Data;
import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.summer.beans.annotations.Unique;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.OutsideView;
import one.kii.summer.zoom.ZoomOutBySet;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
public interface ModelPublicationDai {


    List<PublishedExtension> searchExtension(ClueGroup clue);

    List<PublishedSnapshot> loadSnapshot(ChannelId channel);

    List<Record> loadPublications(ChannelSet channel) throws NotFound, Panic;

    List<Provider> searchProviders(ClueId clue);

    OutsideView selectModelBySet(ZoomOutBySet channel) throws Panic;


    @Transactional
    void save(List<Record> record, PublishModelApi.Form form) throws Conflict;

    @Data
    class ClueGroup {
        String group;
    }

    @Data
    class ChannelSet {

        Long set;
    }

    @Data
    class ChannelId {
        Long id;
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
    class Record {
        Long id;

        Long set;

        @Unique
        String providerId;

        @Unique
        Long extId;

        @Unique
        Long intId;

        @Unique
        String version;

        @Unique
        String stability;

        String operatorId;

        Date beginTime;

        @Unique
        @MayHave
        Date endTime;
    }

    @Data
    class PublishedExtension {
        Long id;

        String providerId;

        String group;

        String name;

        String operatorId;

        Date beginTime;
    }

    @Data
    class PublishedSnapshot {
        Long set;
        String stability;
        String version;
        Date beginTime;
    }


}
