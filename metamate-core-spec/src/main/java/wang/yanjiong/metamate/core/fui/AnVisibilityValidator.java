package wang.yanjiong.metamate.core.fui;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface AnVisibilityValidator {


    boolean isValid(String visibility);

    enum Visibility {
        PRIVATE,
        PROTECTED,
        PUBLIC
    }
}
