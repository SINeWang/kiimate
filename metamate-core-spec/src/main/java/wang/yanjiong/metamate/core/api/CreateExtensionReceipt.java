package wang.yanjiong.metamate.core.api;

import java.io.Serializable;

/**
 * Created by WangYanJiong on 3/24/17.
 */
public class CreateExtensionReceipt implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
