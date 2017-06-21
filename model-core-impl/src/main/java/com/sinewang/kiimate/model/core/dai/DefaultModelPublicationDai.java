package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ModelPublicationMapper;
import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.summer.beans.utils.UniqueFinder;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.OutsideView;
import one.kii.summer.zoom.ZoomOutBySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultModelPublicationDai implements ModelPublicationDai {

    @Autowired
    private ModelPublicationMapper modelPublicationMapper;

    @Override
    public List<PublishedExtension> searchExtension(ClueGroup clue) {
        return modelPublicationMapper.selectPublishedExtensionByGroupQuery( clue.getGroup());
    }

    @Override
    public List<PublishedSnapshot> loadSnapshot(ChannelId channel) {
        return modelPublicationMapper.selectPublishedSnapshotsByExtId(channel.getId());

    }

    @Override
    public List<Record> loadPublications(ChannelSet channel) throws NotFound, Panic {
        List<Record>  records = modelPublicationMapper.selectPublicationsBySet(channel.getSet());
        return NotBadResponse.of(records);
    }


    @Override
    public OutsideView selectModelBySet(ZoomOutBySet channel) throws Panic {
        OutsideView record = modelPublicationMapper.selectModelPubBySet(
                channel
        );
        return NotBadResponse.of(record);
    }

    @Override
    public List<Provider> searchProviders(ClueId clue) {
        return modelPublicationMapper.queryProviders(clue.getId());
    }

    @Override
    public void save(List<Record> records, PublishModelApi.Form form) throws Conflict {
        MultiValueMap<String, String> map = UniqueFinder.find(form);
        int count = modelPublicationMapper.countByConflictKey(map.toSingleValueMap());
        if (count > 0) {
            throw new Conflict(map.keySet());
        }
        for (Record record : records) {
            modelPublicationMapper.insertPublication(
                    record.getId(),
                    record.getSet(),
                    record.getProviderId(),
                    record.getExtId(),
                    record.getIntId(),
                    record.getVersion(),
                    record.getStability(),
                    record.getOperatorId(),
                    record.getBeginTime()
            );
        }
    }

}
