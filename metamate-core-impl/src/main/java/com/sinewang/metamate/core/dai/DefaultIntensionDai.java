package com.sinewang.metamate.core.dai;

import com.sinewang.metamate.core.dai.mapper.IntensionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.dai.IntensionDai;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Service
public class DefaultIntensionDai implements IntensionDai {

    private final Logger logger = LoggerFactory.getLogger(DefaultIntensionDai.class);

    @Autowired
    private IntensionMapper intensionMapper;

    @Override
    public void insertIntension(Intension intension) throws IntensionDuplicated {
        Date beginTime = new Date();

        try {
            intensionMapper.insertIntension(
                    intension.getId(),
                    intension.getExtId(),
                    intension.getField(),
                    intension.isSingle(),
                    intension.getStructure(),
                    intension.getVisibility(),
                    beginTime
            );
        } catch (DuplicateKeyException duplicated) {
            logger.error("Duplicated-Key:{}", intension.getId());
            throw new IntensionDai.IntensionDuplicated(intension.getId(), duplicated);
        }
    }

    @Override
    public List<Intension> selectIntensionsByExtId(String extId) {
        List<Intension> intensions = intensionMapper.selectIntensionsByExtId(extId);
        return intensions;
    }


}
