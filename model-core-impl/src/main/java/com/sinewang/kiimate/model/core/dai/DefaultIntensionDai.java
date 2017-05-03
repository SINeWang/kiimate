package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.IntensionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import one.kii.kiimate.model.core.dai.IntensionDai;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultIntensionDai implements IntensionDai {

    private final Logger logger = LoggerFactory.getLogger(DefaultIntensionDai.class);

    @Autowired
    private IntensionMapper intensionMapper;

    @Override
    public void insertIntension(Intension intension) throws IntensionDuplicated {
        Intension oldIntension = intensionMapper.selectLatestIntensionByExtIdField(intension.getExtId(), intension.getField());

        Date now = new Date();

        if (oldIntension != null) {
            throw new IntensionDai.IntensionDuplicated(intension.getId());
        }
        try {
            intensionMapper.insertIntension(
                    intension.getId(),
                    intension.getExtId(),
                    intension.getField(),
                    intension.isSingle(),
                    intension.getStructure(),
                    intension.getRefExtId(),
                    intension.getVisibility(),
                    now
            );
        } catch (DuplicateKeyException duplicated) {
            logger.error("Duplicated-Key:{}", intension.getId());
            throw new IntensionDai.IntensionDuplicated(intension.getId());
        }
    }

    @Override
    public List<Intension> selectIntensionsByExtId(String extId) {
        return intensionMapper.selectLatestIntensionsByExtId(extId);
    }

    @Override
    public void deleteIntensionsByExitId(String extId) {
        intensionMapper.deleteIntensionsByExtId(extId);
    }


}
