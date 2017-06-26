package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.OutsideView;
import one.kii.summer.zoom.ZoomOutByName;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
public interface StatusDai {

    OutsideView load(ZoomOutByName name) throws Panic, BadRequest;

    @Data
    class ClueGroup {
        String ownerId;
        String group;
    }

}