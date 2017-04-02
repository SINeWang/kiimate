package wang.yanjiong.statemate.core.spi;


import one.kii.summer.annot.SummerSpi;
import one.kii.summer.bound.Receipt;

/**
 * Created by WangYanJiong on 02/04/2017.
 */


@SummerSpi
public interface GetStateSpi {

    <T> Receipt<T> getLatestState(String ownerId, String group, String name, String tag, Class<T> stateClass);
}
