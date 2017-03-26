package com.sinewang.metamate.core.fi;

import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.fi.AnVisibilityValidator;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
@Service
public class DefaultVisibilityValidator implements AnVisibilityValidator {

    @Override
    public boolean isValid(String visibility) {
        try {
            Visibility.valueOf(visibility);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

}
