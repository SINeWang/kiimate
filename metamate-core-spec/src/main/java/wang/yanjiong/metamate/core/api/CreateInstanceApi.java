package wang.yanjiong.metamate.core.api;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface CreateInstanceApi {

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

}
