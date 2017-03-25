package wang.yanjiong.metamate.core.model;

import lombok.Data;

/**
 * Created by WangYanJiong on 25/03/2017.
 */

@Data
public class Receipt<T> {

    private Context context;

    private T data;
}
