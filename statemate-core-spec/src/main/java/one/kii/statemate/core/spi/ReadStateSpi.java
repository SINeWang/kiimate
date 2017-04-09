package one.kii.statemate.core.spi;


import lombok.Data;
import one.kii.summer.annot.SummerSpi;

/**
 * Created by WangYanJiong on 02/04/2017.
 */


@SummerSpi
public interface ReadStateSpi {

    <T> State<T> getLatestState(String ownerId, String group, String name, Class<T> state);

    @Data
    class State<T> {

        String ownerId;

        String group;

        String name;

        String tag;

        T body;
    }
}
