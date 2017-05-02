package wang.yanjiong.metamate.core.api;

import lombok.Data;

import java.util.List;

/**
 * Created by WangYanJiong on 02/05/2017.
 */
public interface ExploreModelsApi {


    List<Provider> getProviders(String query);

    @Data
    class Provider {
        String providerId;
    }
}
