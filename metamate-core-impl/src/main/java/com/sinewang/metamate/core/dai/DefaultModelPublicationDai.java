package com.sinewang.metamate.core.dai;

import com.sinewang.metamate.core.dai.mapper.ModelPublicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.dai.ModelPublicationDai;

import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
@Component
public class DefaultModelPublicationDai implements ModelPublicationDai {

    @Autowired
    private ModelPublicationMapper modelPublicationMapper;

    @Override
    public void savePublications(List<Publication> publications) {
        for (Publication publication : publications) {
            modelPublicationMapper.insertPublication(
                    publication.getId(),
                    publication.getProviderId(),
                    publication.getExtId(),
                    publication.getIntId(),
                    publication.getVersion(),
                    publication.getPublication(),
                    publication.getOperatorId(),
                    publication.getCreatedAt()
            );
        }
    }
}
