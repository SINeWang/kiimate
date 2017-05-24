package one.kii.kiimate.status.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import org.springframework.util.MultiValueMap;

import java.util.List;


/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface RefreshStatusApi {

    List<Instance> commit(WriteContext context, SubIdForm form) throws NotFound, Conflict;

    List<Instance> commit(WriteContext context, GroupNameTreeForm form) throws NotFound, Conflict;

    @Data
    class SubIdForm {
        String subId;
        MultiValueMap<String, String> map;
    }

    @Data
    class GroupNameTreeForm {
        String group;
        String name;
        String tree;
        MultiValueMap<String, String> map;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Instance {

        private String id;

        private String extId;

        private String intId;

        private String ownerId;

        private String operatorId;

        private String field;

        private String[] value;

    }

}
