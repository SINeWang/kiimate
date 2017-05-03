package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ModelPublicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;

import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultModelPublicationDai implements ModelPublicationDai {

    @Autowired
    private ModelPublicationMapper modelPublicationMapper;

    @Override
    public void savePublications(String pubSetHash, List<Publication> publications) throws DuplicatedPublication {
        int count = modelPublicationMapper.countPublicationByPubSetHash(pubSetHash);
        if (count > 0) {
            throw new DuplicatedPublication(pubSetHash);
        }
        for (Publication publication : publications) {
            modelPublicationMapper.insertPublication(
                    publication.getId(),
                    pubSetHash,
                    publication.getProviderId(),
                    publication.getExtId(),
                    publication.getIntId(),
                    publication.getVersion(),
                    publication.getPublication(),
                    publication.getOperatorId(),
                    publication.getBeginTime()
            );
        }
    }

    @Override
    public List<Publication> getPublicationsByPubSetHash(String pubSetHash) {
        return modelPublicationMapper.selectPublicationByPubSetHash(pubSetHash);
    }

    @Override
    public List<Publication> queryPublicationsByGroup(String query) {
        return modelPublicationMapper.selectPublicationByGroupQuery(query);
    }

    @Override
    public List<Provider> getProviders(String query) {
        return modelPublicationMapper.selectProvidersByProviderQuery(query);
    }
}
