package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.GlimpseMapper;
import one.kii.kiimate.status.core.dai.GlimpsesDai;
import one.kii.summer.beans.utils.UniqueFinder;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
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
    public void remember(Subscription subscription) throws BadRequest {
        NotBadRequest.from(subscription);
        glimpseMapper.insertSubscription(
                subscription.getId(),
                subscription.getSubscriberId(),
                subscription.getSet(),
                subscription.getGroup(),
                subscription.getName(),
                subscription.getTree(),
                subscription.getOperatorId(),
                subscription.getBeginTime()
        );
    }


    @Override
    public void remember(Glimpse glimpse, List<Entry> entries) throws Conflict {
        MultiValueMap<String, String> map = UniqueFinder.find(glimpse);
        Integer count = glimpseMapper.countByConflictKey(map.toSingleValueMap());
        if (count > 0) {
            throw new Conflict(map.keySet());
        }
        for (Entry entry : entries) {
            glimpseMapper.insertGlimpse(
                    entry.getId(),
                    glimpse.getSet(),
                    glimpse.getProviderId(),
                    glimpse.getModelSubId(),
                    entry.getInsId(),
                    glimpse.getVersion(),
                    glimpse.getStability(),
                    glimpse.getVisibility(),
                    glimpse.getOperatorId(),
                    glimpse.getBeginTime()
            );
        }
    }

    @Override
    public List<Glimpse> queryPublications(ClueGroup clue) throws BadRequest, Panic {
        NotBadRequest.from(clue);
        List<Glimpse> list = glimpseMapper.queryGlimpses(
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
    public Glimpse load(ZoomOutBySet channel) throws Panic {
        Glimpse glimpse = glimpseMapper.loadGlimpse(
                channel.getProviderId(),
                channel.getSet());
        return NotBadResponse.of(glimpse);
    }

}
