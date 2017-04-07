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
        if(structure == null){
            return false;
        }
        try {
            String upperCase = structure.toUpperCase();
            Structure.valueOf(upperCase);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

}
