package com.sinewang.metamate.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.CreateExtensionApi;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.fi.ExtensionFi;
import wang.yanjiong.metamate.core.model.Extension;

import java.util.UUID;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RestController
public class DefaultCreateExtensionApi implements CreateExtensionApi {


    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private ExtensionFi extensionFi;

    @Override
    public Response<Extension> createExtensionViaFormUrlEncoded(Request request) {

        Extension extension = extensionFi.accept(request);

        extensionDai.insertExtension(extension);

        Response<Extension> response = new Response<>();

        response.setContext(request.getContext());

        request.getContext().setResponseId(UUID.randomUUID().toString());

        response.setData(extension);

        return response;
    }

    @Override
    public Response createExtensionViaJson(Request request) {
        return null;
    }
}
