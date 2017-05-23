package com.sinewang.kiimate.status.cases.spi;

import one.kii.kiimate.status.cases.spi.VisitAssetsSpi;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.sender.ErestGetBasic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 23/05/2017.
 */
@Component
public class DefaultVisitAssetsSpi implements VisitAssetsSpi {

    private static String URI = "/{owner-id}/assets/{group}/{name}/{stability}/{version}";
    @Value("${kiimate.url}")
    private String url;

    @Override
    public <T> T visit(Class<T> klass, LatestForm latestForm) throws BadRequest, Panic, NotFound {
        String urlTemplate = url + URI;
        ErestGetBasic erestGet = new ErestGetBasic(latestForm.getOwnerId());
        return erestGet.execute(urlTemplate, klass, latestForm.getOwnerId(), latestForm.getGroup(), latestForm.getName(), latestForm.getStability(), latestForm.getVersion());
    }


}
