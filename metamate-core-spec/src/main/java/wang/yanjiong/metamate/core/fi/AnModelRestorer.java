package wang.yanjiong.metamate.core.fi;

import wang.yanjiong.metamate.core.dai.IntensionDai;

import java.util.Map;

/**
 * Created by WangYanJiong on 08/04/2017.
 */
public interface AnModelRestorer {

    Map<String, Object> fullRestoreAsMap(String extId);

    void restoreAsFieldDict(String extId, Map<String, IntensionDai.Intension> map);
}
