package com.sinewang.statemate.core.spi;

import one.kii.statemate.core.spi.CreateIntensionSpi;
import one.kii.summer.io.exception.*;
import one.kii.summer.io.sender.ErestPost;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by WangYanJiong on 4/7/17.
 */
@ConfigurationProperties(prefix = "metamate")
@Component
public class DefaultCreateIntensionSpi implements CreateIntensionSpi {

    private static String TREE = "master";
    private static String URI = "/{ownerId}/intension";

    private String baseUrl;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String createPublicPrimitiveIntension(PrimitiveIntensionForm form) throws Panic {
        String url = baseUrl + URI;

        ErestPost erest = new ErestPost(form.getOwnerId());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("extId", form.getExtId());
        map.set("field", form.getField());
        map.set("single", String.valueOf(form.isSingle()));
        map.set("structure", form.getStructure());
        map.set("visibility", form.getVisibility());
        IntensionReceipt receipt;
        try {
            receipt = erest.execute(url, map, IntensionReceipt.class, form.getOwnerId());
            return receipt.getId();
        } catch (Conflict conflict) {
            return conflict.getKeys()[0];
        } catch (BadRequest | NotFound | Forbidden | Panic panic) {
            throw new Panic();
        }
    }

    @Override
    public String createPublicImportIntension(ImportIntensionForm form) throws Panic {
        String url = baseUrl + URI;

        ErestPost erest = new ErestPost(form.getOwnerId());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("extId", form.getExtId());
        map.set("field", form.getField());
        map.set("single", String.valueOf(form.isSingle()));
        map.set("structure", form.getStructure());
        map.set("refExtId", form.getRefExtId());
        map.set("visibility", form.getVisibility());
        try {
            IntensionReceipt receipt = erest.execute(url, map, IntensionReceipt.class, form.getOwnerId());
            return receipt.getExtId();
        } catch (Conflict conflict) {
            return conflict.getKeys()[0];
        } catch (BadRequest | NotFound | Forbidden | Panic panic) {
            throw new Panic();
        }
    }

    private List<String> toList(String string) {
        List<String> list = new ArrayList<>();
        list.add(string);
        return list;
    }

}
