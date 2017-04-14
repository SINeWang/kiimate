package one.kii.statemate.core.spi;

import lombok.Data;
import one.kii.summer.context.exception.Panic;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface ReadExtensionSpi {

    String NAME_ROOT = "root";

    String readMasterExtension(GroupForm form) throws Panic;

    String readMasterExtension(GroupNameForm form) throws Panic;

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