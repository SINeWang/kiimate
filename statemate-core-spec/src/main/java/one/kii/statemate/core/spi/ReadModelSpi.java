package one.kii.statemate.core.spi;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface ReadModelSpi {

    String NAME_ROOT = "root";

    String readModel(String group);
}
