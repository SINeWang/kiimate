package wang.yanjiong.metamate.core.fi;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface AnPublicationValidator {


    boolean isValid(String publication);

    enum Publication {
        SNAPSHOT,
        RELEASE
    }


}
