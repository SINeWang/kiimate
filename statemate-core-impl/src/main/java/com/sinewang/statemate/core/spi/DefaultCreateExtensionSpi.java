package com.sinewang.statemate.core.spi;

import one.kii.statemate.core.spi.CreateExtensionSpi;
import one.kii.summer.erest.ErestPostForm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
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
public class DefaultCreateExtensionSpi implements CreateExtensionSpi {

    private static String URI = "/extension";
    private static String TREE = "master";
    private static String VISIBILITY_PUBLIC = "public";
    private String baseUrl;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Receipt createMasterPublicExtension(Form form) {
        String url = baseUrl + URI;

        ErestPostForm erest = new ErestPostForm();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-MM-OwnerId", form.getOwnerId());
        httpHeaders.set("X-MM-OperatorId", form.getOwnerId());
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("group", toList(form.getGroup()));
        map.put("name", toList(form.getName()));
        map.put("tree", toList(TREE));
        map.put("visibility", toList(VISIBILITY_PUBLIC));
        return erest.doPost(url, httpHeaders, map, Receipt.class);

    }

    private List<String> toList(String string) {
        List<String> list = new ArrayList<>();
        list.add(string);
        return list;
    }


}
