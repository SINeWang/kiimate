package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ModelPublicationMapper;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.validator.NotBadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        return modelPublicationMapper.selectPublishedExtensionByGroupQuery(clue.getGroup());
    }

    @Override
    public List<PublishedSnapshot> loadSnapshot(ChannelId channel) {
        return modelPublicationMapper.selectPublishedSnapshotsByExtId(channel.getId());

    }

    @Override
    public Record loadRootPublications(ChannelPubSet channel) throws NotFound {
        Record record = modelPublicationMapper.selectRootPublicationsByPubSet(channel.getPubSet());
        return NotBadResponse.of(Record.class, MayHave.class, record);
    }

    @Override
    public List<Provider> searchProviders(ClueId clue) {
        return modelPublicationMapper.selectProvidersByProviderQuery(clue.getId());
    }

    @Override
    public void save(List<Record> records) throws DuplicatedPublication {
        int count = modelPublicationMapper.countPublicationByPubSet(records.get(0).getPubSet());
        if (count > 0) {
            throw new DuplicatedPublication(records.get(0).getPubSet());
        }
        for (Record record : records) {
            modelPublicationMapper.insertPublication(
                    record.getId(),
                    record.getPubSet(),
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
