package com.sinewang.metamate.core.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.VisitEntitiesApi;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@RestController
@RequestMapping("/v1")
public class DefaultVisitEntitiesApi implements VisitEntitiesApi {

    @Override
    @RequestMapping(value = "/{ownerId}/entities/{group}/{name}/{version:.+}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader(value = "X-SUMMER-VisitorId", required = false) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("version") String version,
            @RequestParam(value = "tag", defaultValue = "LATEST") String tag) {

        return null;
    }
}
