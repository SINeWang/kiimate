package wang.yanjiong.magnet.xi.boundary;

import lombok.Data;

import java.util.Date;

/**
 * Created by WangYanJiong on 26/03/2017.
 */

@Data
public class Summary {

    private Status status;

    private Date time;

    public enum Status {
        ACCEPTED,
        BAD_REQUEST
    }
}
