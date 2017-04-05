package com.sinewang.metamate.core.fi;

import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.fi.AnPublicationValidator;
import wang.yanjiong.metamate.core.fi.AnStructureValidator;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultPublicationValidator implements AnPublicationValidator {

    @Override
    public boolean isValid(String publication) {
        try {
            String upperCase = publication.toUpperCase();
            AnStructureValidator.Structure.valueOf(upperCase);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
