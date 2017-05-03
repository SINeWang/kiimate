package com.sinewang.kiimate.model.core.fui;

import org.springframework.stereotype.Component;
import one.kii.kiimate.model.core.fui.AnPublicationValidator;
import one.kii.kiimate.model.core.fui.AnStructureValidator;

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
