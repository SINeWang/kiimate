package one.kii.kiimate.status.cases.spi;

import lombok.Data;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;

/**
 * Created by WangYanJiong on 23/05/2017.
 */
public interface VisitRawStatusSpi {

    String STABILITY_RELEASE = "release";

    String VERSION_HEAD = "HEAD";

    <T> T visit(Class<T> klass, LatestForm latestForm) throws BadRequest, Panic, NotFound;

    @Data
    class LatestForm {
        String stability = STABILITY_RELEASE;
        String version = VERSION_HEAD;
        String ownerId;
        String group;
        String name;
    }
}
