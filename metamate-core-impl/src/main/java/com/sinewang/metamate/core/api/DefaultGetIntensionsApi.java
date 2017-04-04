package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.bound.Context;
import one.kii.summer.bound.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.GetIntensionsApi;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@RestController
public class DefaultGetIntensionsApi implements GetIntensionsApi {

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnExtensionExtractor anExtensionExtractor;


    @Override
    public Receipt readIntensionsByExiId(@PathVariable("extId") String extId) {
        List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extId);

        Receipt receipt = new Receipt();

        List<GetIntensionsApi.Intension> intensions1 = DataTools.copy(intensions, GetIntensionsApi.Intension.class);


        Summary summary = new Summary();
        summary.setStatus(Summary.Status.ACCEPTED);
        summary.setTime(new Date());


        Context context = new Context();

        context.setProcessId(UUID.randomUUID().toString());

        receipt.setIntensions(intensions1);
        receipt.setExtId(extId);
        receipt.setSummary(summary);

        receipt.setContext(context);

        return receipt;
    }

    @Override
    public Receipt readIntensionsByGroupNameVersion(
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader(value = "X-SUMMER-VisitorId", required = false) String visitorId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {

        String extId = anExtensionExtractor.hashId(ownerId, group, name, tree);
        return readIntensionsByExiId(extId);
    }
}
