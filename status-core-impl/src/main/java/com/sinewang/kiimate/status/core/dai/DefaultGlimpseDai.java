package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.GlimpseMapper;
import one.kii.kiimate.status.core.dai.GlimpsesDai;
import one.kii.summer.beans.utils.UniqueFinder;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.OutsideView;
import one.kii.summer.zoom.ZoomOutBySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Created by WangYanJiong on 27/05/2017.
 */
@Component
public class DefaultGlimpseDai implements GlimpsesDai {

    @Autowired
    private GlimpseMapper glimpseMapper;


    @Override
    public void remember(Glimpse glimpse) throws BadRequest {
        NotBadRequest.from(glimpse);
        glimpseMapper.insertGlimpse(
                glimpse.getId(),
                glimpse.getSubscriberId(),
                glimpse.getSet(),
                glimpse.getOperatorId(),
                glimpse.getBeginTime()
        );
    }


    @Override
    public void remember(Publication publication, List<Entry> entries) throws Conflict {
        MultiValueMap<String, String> map = UniqueFinder.find(publication);
        Integer count = glimpseMapper.countByConflictKey(map.toSingleValueMap());
        if (count > 0) {
            throw new Conflict(map.keySet());
        }
        for (Entry entry : entries) {
            glimpseMapper.insertPublication(
                    entry.getId(),
                    publication.getSet(),
                    publication.getProviderId(),
                    publication.getModelSubId(),
                    entry.getInsId(),
                    publication.getVersion(),
                    publication.getStability(),
                    publication.getVisibility(),
                    publication.getOperatorId(),
                    publication.getBeginTime()
            );
        }
    }

    @Override
    public List<Publication> queryPublications(ClueGroup clue) throws BadRequest, Panic {
        NotBadRequest.from(clue);
        List<Publication> list = glimpseMapper.queryStatusPub(
                clue.getOwnerId(),
                clue.getGroup());
        return NotBadResponse.of(list);
    }

    @Override
    public List<OutsideView> queryGlimpses(ClueGroup clue) throws BadRequest, Panic {
        NotBadRequest.from(clue);
        List<OutsideView> list = glimpseMapper.queryGlimpses(
                clue.getOwnerId(),
                clue.getGroup());
        return NotBadResponse.of(list);
    }


    @Override
    public List<Providers> queryProviders(ClueId clue) throws BadRequest {
        NotBadRequest.from(clue);
        return glimpseMapper.queryProviders(clue.getId());
    }

    @Override
    public Publication load(ZoomOutBySet channel) throws Panic {
        Publication publication = glimpseMapper.loadStatusPub(
                channel.getProviderId(),
                channel.getSet());
        return NotBadResponse.of(publication);
    }

}
