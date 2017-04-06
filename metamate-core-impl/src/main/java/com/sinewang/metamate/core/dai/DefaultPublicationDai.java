package com.sinewang.metamate.core.dai;

import com.sinewang.metamate.core.dai.mapper.PublicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.dai.PublicationDai;

import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
@Component
public class DefaultPublicationDai implements PublicationDai {

    @Autowired
    private PublicationMapper publicationMapper;

    @Override
    public void savePublications(List<Publication> publications) {
        for (Publication publication : publications) {
            publicationMapper.insertPublication(
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
