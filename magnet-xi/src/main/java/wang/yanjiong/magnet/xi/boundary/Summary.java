package wang.yanjiong.magnet.xi.boundary;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 26/03/2017.
 */

@Data
public class Summary {

    private Status status;

    private Date time;

    private List<String> reasons;

    public enum Status {
        ACCEPTED,
        REJECTED
    }
}
