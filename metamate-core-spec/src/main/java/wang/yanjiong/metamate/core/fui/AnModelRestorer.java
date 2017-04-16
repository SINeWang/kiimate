package wang.yanjiong.metamate.core.fui;

import wang.yanjiong.metamate.core.dai.IntensionDai;

import java.util.Map;

/**
 * Created by WangYanJiong on 08/04/2017.
 */
public interface AnModelRestorer {

    Map<String, Object> fullRestoreAsMap(String extId);

    Map<String, IntensionDai.Intension> restoreAsFieldDict(String extId);
}
