package one.kii.kiimate.model.core.dai;

import lombok.Data;

import java.util.List;

/**
 * Created by WangYanJiong on 08/05/2017.
 */
public interface OwnersDai {


    List<Owners> queryOwners(String ownerId);

    @Data
    class Owners {
        String id;
    }
}
