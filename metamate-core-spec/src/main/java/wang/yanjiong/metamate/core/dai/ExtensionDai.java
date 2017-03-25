package wang.yanjiong.metamate.core.dai;

import wang.yanjiong.metamate.core.model.Extension;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface ExtensionDai {

    Extension selectExtensionById(String id);

    void insertExtension(Extension extension);


}
