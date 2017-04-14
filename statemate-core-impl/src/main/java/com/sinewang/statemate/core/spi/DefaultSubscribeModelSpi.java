package com.sinewang.statemate.core.spi;

import one.kii.statemate.core.spi.SubscribeModelSpi;
import one.kii.summer.io.exception.*;
import one.kii.summer.io.sender.ErestPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 09/04/2017.
 */
@Component
@ConfigurationProperties(prefix = "metamate")
public class DefaultSubscribeModelSpi implements SubscribeModelSpi {

    private static Logger logger = LoggerFactory.getLogger(DefaultSubscribeModelSpi.class);

    private static String URI = "/{subscriberId}/subscribe";

    private String baseUrl;

    private String subscriberId;

    private String operatorId;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public Receipt subscribe(Form form) throws Panic {
        String url = baseUrl + URI;

        ErestPost erest = new ErestPost(operatorId);
        logger.info("url:{}, variables:{}", url, form.getSubscriberId());
        try {
            return erest.execute(url, form, Receipt.class, form.getSubscriberId());
        } catch (Forbidden forbidden) {
            forbidden.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        } catch (Panic panic) {
            panic.printStackTrace();
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        }
        throw new Panic();
    }
}