package wang.yanjiong.metamate.core.fi;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface AnStructureValidator {


    boolean isValid(String structure);

    enum Structure {
        STRING,
        NATURE_BASE0,
        NATURE_BASE1,
        REAL,
        FLOAT,
        TIMESTAMP
    }


}
