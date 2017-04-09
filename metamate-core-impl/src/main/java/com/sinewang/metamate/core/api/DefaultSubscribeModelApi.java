package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.erest.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @RequestMapping(value = "/subscribe", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Receipt> subscribe(
            @RequestHeader("X-SUMMER-RequestId") String requestId,
            @RequestHeader("X-MM-SubscriberId") String subscriberId,
            @RequestHeader("X-MM-OperatorId") String operatorId,
            @ModelAttribute Form form) {

        AnSubscribeModelExtractor.ModelSubscription modelSubscription = subscribeModelExtractor.extract(
                form, subscriberId, operatorId, NAME_DEFAULT, TREE_MASTER);

        ModelSubscriptionDai.ModelSubscription subscription = DataTools.copy(modelSubscription, ModelSubscriptionDai.ModelSubscription.class);

        try {
            modelSubscriptionDai.save(subscription);
            Receipt receipt = DataTools.copy(modelSubscription, Receipt.class);
            return Response.accepted(requestId, receipt, subscriberId);
        } catch (ModelSubscriptionDai.DuplicatedSubscription duplicatedSubscription) {
            Receipt receipt = DataTools.copy(duplicatedSubscription, Receipt.class);
            return Response.conflict(requestId, receipt);
        }
    }


}
