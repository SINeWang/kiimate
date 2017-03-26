package wang.yanjiong.magnet.xi.boundary;

import lombok.Data;

import java.util.List;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Data
public class Context {

    String providerId;

    String consumerId;

    String processId;

    String requestId;

    String responseId;

    String transactionId;

    List<String> forwardIds;

    List<String> replyIds;

}
