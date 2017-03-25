package wang.yanjiong.metamate.core.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@Data
public class Instance {

    private String id;

    private Timestamp currentTimestamp;

    private String currentValue;

    private String currentReferenceId;

    private String intensionId;

    private String currentCommit;

    private String previousCommit;

    private Timestamp previousTimestamp;

}
