package com.sinewang.statemate.core.spi;

import one.kii.statemate.core.spi.CreateIntensionSpi;
import one.kii.summer.erest.ErestPost;
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
    private static String VISIBILITY_PUBLIC = "public";
    private static String URI = "/{ownerId}/intension";

    private String baseUrl;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String createPublicPrimitiveIntension(PrimitiveIntensionForm form) {
        String url = baseUrl + URI;

        ErestPost erest = new ErestPost(form.getOwnerId());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("extId", toList(form.getExtId()));
        map.put("field", toList(form.getField()));
        map.put("single", toList(String.valueOf(form.isSingle())));
        map.put("structure", toList(form.getStructure()));
        map.put("visibility", toList(VISIBILITY_PUBLIC));
        IntensionReceipt receipt = erest.execute(url, map, IntensionReceipt.class, form.getOwnerId());
        return receipt.getId();
    }

    @Override
    public String createPublicImportIntension(ImportIntensionForm form) {
        String url = baseUrl + URI;

        ErestPost erest = new ErestPost(form.getOwnerId());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("extId", toList(form.getExtId()));
        map.put("field", toList(form.getField()));
        map.put("single", toList(String.valueOf(form.isSingle())));
        map.put("structure", toList(form.getStructure()));
        map.put("refExtId", toList(form.getRefExtId()));
        map.put("visibility", toList(VISIBILITY_PUBLIC));
        IntensionReceipt receipt = erest.execute(url, map, IntensionReceipt.class, form.getOwnerId());
        return receipt.getExtId();
    }

    private List<String> toList(String string) {
        List<String> list = new ArrayList<>();
        list.add(string);
        return list;
    }

}
