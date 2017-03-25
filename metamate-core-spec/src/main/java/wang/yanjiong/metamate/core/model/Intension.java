package wang.yanjiong.metamate.core.model;

import lombok.Data;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@Data
public class Intension {

    private String id;

    private String extensionId;

    private String referenceId;

    private String rename;

    private boolean single;

    private String dataTypeId;

    private String visibility;

    private List<CrossReference> crossReferences;


}
