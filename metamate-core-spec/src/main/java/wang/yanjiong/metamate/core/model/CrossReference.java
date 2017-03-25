package wang.yanjiong.metamate.core.model;

import lombok.Data;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@Data
public class CrossReference {

    private String id;

    private String excludeName;

    private String includeName;

    private String intensionId;

    private String referenceId;

}
