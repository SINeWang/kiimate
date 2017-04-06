package com.sinewang.metamate.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.VisitEntitiesApi;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@RestController
@RequestMapping("/v1")
public class DefaultVisitEntitiesApi implements VisitEntitiesApi {

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Override
    @RequestMapping(value = "/{ownerId}/entities/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader(value = "X-SUMMER-VisitorId", required = false) String visitorId,
            @RequestHeader("X-MM-ExtId") String extId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {

        return null;
    }
}
