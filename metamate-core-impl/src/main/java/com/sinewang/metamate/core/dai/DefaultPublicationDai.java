package com.sinewang.metamate.core.dai;

import com.sinewang.metamate.core.dai.mapper.IntensionMapper;
import com.sinewang.metamate.core.dai.mapper.PublicationMapper;
import one.kii.summer.codec.utils.HashTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.dai.PublicationDai;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
@Component
public class DefaultPublicationDai implements PublicationDai {

    @Autowired
    private PublicationMapper publicationMapper;

    @Autowired
    private IntensionMapper intensionMapper;

    @Override
    public void savePublication(Publication publication) {
        Date now = new Date();
        List<IntensionDai.Intension> intensions = intensionMapper.selectLatestIntensionsByExtId(publication.getExtId());
        for (IntensionDai.Intension intension : intensions) {
            String id = HashTools.hashHex(
                    publication.getOwnerId(),
                    publication.getExtId(),
                    intension.getId(),
                    publication.getVersion(),
                    publication.getPublication());

            publicationMapper.insertPublication(
                    id,
                    publication.getOwnerId(),
                    publication.getExtId(),
                    intension.getId(),
                    publication.getVersion(),
                    publication.getPublication(),
                    publication.getOperatorId(),
                    now
            );
        }
    }
}
