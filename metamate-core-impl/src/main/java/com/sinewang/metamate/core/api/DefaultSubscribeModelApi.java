package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.SubscribeModelApi;
import wang.yanjiong.metamate.core.dai.ModelSubscriptionDai;
import wang.yanjiong.metamate.core.fi.AnSubscribeModelExtractor;

/**
 * Created by WangYanJiong on 4/6/17.
 */
@RestController
public class DefaultSubscribeModelApi implements SubscribeModelApi {

    @Autowired
    private AnSubscribeModelExtractor subscribeModelExtractor;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    public ResponseEntity<Receipt> subscribe(
            @ModelAttribute Form form,
            @RequestHeader("X-SUMMER-SubscriberId") String subscriberId,
            @RequestHeader("X-SUMMER-OperatorId") String operatorId,
            @PathVariable("providerId") String providerId,
            @PathVariable("extId") String extId,
            @PathVariable("publication") String publication,
            @PathVariable("version") String version) {

        AnSubscribeModelExtractor.ModelSubscription modelSubscription = subscribeModelExtractor.extract(
                form, providerId, extId, publication, version, subscriberId, operatorId);

        ModelSubscriptionDai.ModelSubscription subscription = DataTools.copy(modelSubscription, ModelSubscriptionDai.ModelSubscription.class);

        modelSubscriptionDai.save(subscription);

        Receipt receipt = DataTools.copy(subscription, Receipt.class);

        return ResponseFactory.accepted(receipt, subscriberId);
    }


}
