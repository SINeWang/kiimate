package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ExtensionMapper;
import com.sinewang.kiimate.model.core.dai.mapper.IntensionMapper;
import com.sinewang.kiimate.model.core.dai.mapper.ModelPublicationMapper;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultModelPublicationDai implements ModelPublicationDai {

    @Autowired
    private ModelPublicationMapper modelPublicationMapper;

    @Autowired
    private ExtensionMapper extensionMapper;

    @Autowired
    private IntensionMapper intensionMapper;

    @Override
    public void savePublications(String pubSet, List<Publication> publications,
                                 List<ExtensionDai.Extension> extensions,
                                 List<IntensionDai.Intension> intensions) throws DuplicatedPublication {
        int count = modelPublicationMapper.countPublicationByPubSet(pubSet);
        if (count > 0) {
            throw new DuplicatedPublication(pubSet);
        }
        for (Publication publication : publications) {
            modelPublicationMapper.insertPublication(
                    publication.getId(),
                    pubSet,
                    publication.getProviderId(),
                    publication.getExtId(),
                    publication.getIntId(),
                    publication.getVersion(),
                    publication.getPublication(),
                    publication.getOperatorId(),
                    publication.getBeginTime()
            );
        }

        Date now = new Date();
        for (IntensionDai.Intension intension : intensions) {
            intensionMapper.insertIntension(
                    intension.getId(),
                    intension.getExtId(),
                    intension.getField(),
                    intension.isSingle(),
                    intension.getStructure(),
                    intension.getRefExtId(),
                    intension.getVisibility(),
                    intension.isRequired(),
                    now
            );
        }

        for (ExtensionDai.Extension extension : extensions) {
            extensionMapper.insertExtension(
                    extension.getId(),
                    extension.getOwnerId(),
                    extension.getGroup(),
                    extension.getName(),
                    extension.getTree(),
                    extension.getVisibility(),
                    now
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
