package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.OwnersMapper;
import one.kii.kiimate.model.core.dai.OwnersDai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 08/05/2017.
 */

@Component
public class DefaultOwnersDai implements OwnersDai {


    @Autowired
    private OwnersMapper ownersMapper;

    @Override
    public List<Owners> queryOwners(ClueId clue) {
        return ownersMapper.queryOwners(
                clue.getId()
        );
    }
}
