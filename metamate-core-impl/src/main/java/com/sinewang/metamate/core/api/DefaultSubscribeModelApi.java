package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.erest.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.SubscribeModelApi;
import wang.yanjiong.metamate.core.dai.ModelPublicationDai;
import wang.yanjiong.metamate.core.dai.ModelSubscriptionDai;
import wang.yanjiong.metamate.core.fi.AnSubscribeModelExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangYanJiong on 4/6/17.
 */
@RestController
public class DefaultSubscribeModelApi implements SubscribeModelApi {

    @Autowired
    private AnSubscribeModelExtractor subscribeModelExtractor;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private ModelPublicationDai modelPublicationDai;

    @Override
    @RequestMapping(value = "/subscribe/{pubSetHash}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Receipt> subscribe(
            @RequestHeader("X-SUMMER-RequestId") String requestId,
            @RequestHeader("X-MM-SubscriberId") String subscriberId,
            @RequestHeader("X-MM-OperatorId") String operatorId,
            @PathVariable("pubSetHash") String pubSetHash,
            @ModelAttribute Form form) {

        List<ModelPublicationDai.Publication> publicationList = modelPublicationDai.getPublicationsByPubSetHash(pubSetHash);

        List<ModelSubscriptionDai.ModelSubscription> modelSubscriptions = new ArrayList<>();
        for (ModelPublicationDai.Publication publication : publicationList) {
            AnSubscribeModelExtractor.ModelSubscription modelSubscription = subscribeModelExtractor.extract(
                    form, pubSetHash, publication.getPubExtId(), subscriberId, operatorId);
            ModelSubscriptionDai.ModelSubscription subscription = DataTools.copy(modelSubscription, ModelSubscriptionDai.ModelSubscription.class);
            modelSubscriptions.add(subscription);
        }

        modelSubscriptionDai.save(modelSubscriptions);

        List<ModelSubscription> subscriptions = DataTools.copy(modelSubscriptions, ModelSubscription.class);

        Receipt receipt = new Receipt();

        receipt.setSubscriptions(subscriptions);

        receipt.setPubSetHash(pubSetHash);

        return Response.accepted(requestId, receipt, subscriberId);
    }


}
