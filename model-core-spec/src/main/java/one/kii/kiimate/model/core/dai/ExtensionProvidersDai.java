package one.kii.kiimate.model.core.dai;

import lombok.Data;

import java.util.List;

/**
 * Created by WangYanJiong on 08/05/2017.
 */
public interface ExtensionProvidersDai {


    List<Providers> queryProviders(ClueId clue);

    @Data
    class ClueId {
        String id;
    }

    @Data
    class Providers {
        String id;
    }
}
