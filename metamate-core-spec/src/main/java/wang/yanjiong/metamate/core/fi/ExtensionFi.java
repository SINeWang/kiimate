package wang.yanjiong.metamate.core.fi;

import wang.yanjiong.metamate.core.api.CreateExtensionApi;
import wang.yanjiong.metamate.core.model.Extension;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface ExtensionFi {

    Extension accept(CreateExtensionApi.Request request);


}
