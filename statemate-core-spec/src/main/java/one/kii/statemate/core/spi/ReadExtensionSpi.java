package one.kii.statemate.core.spi;

import lombok.Data;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface ReadExtensionSpi {

    String NAME_ROOT = "ROOT";

    String readMasterExtension(GroupForm form);

    String readMasterExtension(GroupNameForm form);

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