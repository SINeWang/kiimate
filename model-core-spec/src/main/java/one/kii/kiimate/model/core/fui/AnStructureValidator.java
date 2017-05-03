package one.kii.kiimate.model.core.fui;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface AnStructureValidator {


    boolean isValid(String structure);

    enum Structure {
        STRING,
        NATURE,
        BOOL,
        REAL,
        FLOAT,
        TIMESTAMP
    }


}
