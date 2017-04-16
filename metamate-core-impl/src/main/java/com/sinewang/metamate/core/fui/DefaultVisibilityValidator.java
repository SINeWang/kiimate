package com.sinewang.metamate.core.fui;

import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.fui.AnVisibilityValidator;

/**
 * Created by WangYanJiong on 26/03/2017.
 */


@Component
public class DefaultVisibilityValidator implements AnVisibilityValidator {

    @Override
    public boolean isValid(String visibility) {
        try {
            String upperCase = visibility.toUpperCase();
            Visibility.valueOf(upperCase);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

}
