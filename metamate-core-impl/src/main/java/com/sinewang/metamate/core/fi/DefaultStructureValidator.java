package com.sinewang.metamate.core.fi;

import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.fi.AnStructureValidator;

/**
 * Created by WangYanJiong on 26/03/2017.
 */

@Service
public class DefaultStructureValidator implements AnStructureValidator {


    @Override
    public boolean isValid(String structure) {
        try {
            Structure.valueOf(structure);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

}
