package wang.yanjiong.metamate.core.model;

import lombok.Data;

import java.util.List;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Data
public class Context {

    String processId;

    String requestId;

    String responseId;

    String transactionId;

    List<String> forwardIds;

    List<String> replyIds;
}
