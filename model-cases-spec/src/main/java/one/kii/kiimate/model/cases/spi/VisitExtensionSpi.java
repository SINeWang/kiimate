package one.kii.kiimate.model.cases.spi;

import lombok.Data;
import one.kii.summer.io.exception.Panic;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface VisitExtensionSpi {

    String NAME_ROOT = "root";

    String visit(GroupForm form) throws Panic;

    String visit(GroupNameForm form) throws Panic;

    @Data
    class GroupForm {
        String group;
    }

    @Data
    class GroupNameForm {
        String group;
        String name;
    }
}