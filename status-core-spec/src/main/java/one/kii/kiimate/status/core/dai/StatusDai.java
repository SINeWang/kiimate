package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.xyz.ViewDownInsight;
import one.kii.summer.xyz.ViewDownWithXyz;

import java.util.List;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
public interface StatusDai {


    List<ViewDownInsight> searchDownstream(ClueGroup clue) throws BadRequest, Panic;

    ViewDownInsight loadDownstream(ViewDownWithXyz providerXyz) throws Panic, BadRequest;

    @Data
    class ClueGroup {
        String ownerId;
        String group;
    }

}