package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.OutsideView;
import one.kii.summer.zoom.ZoomOutByName;

import java.util.List;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
public interface StatusDai {


    List<OutsideView> searchDownstream(ClueGroup clue) throws BadRequest, Panic;

    OutsideView loadDownstream(ZoomOutByName providerXyz) throws Panic, BadRequest;

    @Data
    class ClueGroup {
        String ownerId;
        String group;
    }

}