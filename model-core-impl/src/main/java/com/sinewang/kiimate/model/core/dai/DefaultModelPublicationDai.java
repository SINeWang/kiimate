package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ModelPublicationMapper;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
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
    public Publication loadRootPublications(ChannelPubSet channel) {
        return modelPublicationMapper.selectRootPublicationsByPubSet(channel.getPubSet());
    }

    @Override
    public List<Provider> searchProviders(ClueId clue) {
        return modelPublicationMapper.selectProvidersByProviderQuery(clue.getId());
    }

    @Override
    public void save(List<Publication> publications) throws DuplicatedPublication {
        int count = modelPublicationMapper.countPublicationByPubSet(publications.get(0).getPubSet());
        if (count > 0) {
            throw new DuplicatedPublication(publications.get(0).getPubSet());
        }
        for (Publication publication : publications) {
            modelPublicationMapper.insertPublication(
                    publication.getId(),
                    publication.getPubSet(),
                    publication.getProviderId(),
                    publication.getExtId(),
                    publication.getIntId(),
                    publication.getVersion(),
                    publication.getStability(),
                    publication.getOperatorId(),
                    publication.getBeginTime()
            );
        }
    }

}
