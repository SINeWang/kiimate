package com.sinewang.kiimate.model.cases.spi;

import one.kii.kiimate.model.cases.spi.DeclareExtensionSpi;
import one.kii.kiimate.model.cases.spi.DeclareIntensionSpi;
import one.kii.kiimate.model.cases.spi.PublishModelSpi;
import one.kii.summer.io.exception.Panic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/7/17.
 */
@Component
public class DefaultPublishModelSpi implements PublishModelSpi {

    private static Logger logger = LoggerFactory.getLogger(DefaultPublishModelSpi.class);

    @Autowired
    private DeclareExtensionSpi createExtensionSpi;

    @Autowired
    private DeclareIntensionSpi declareIntensionSpi;

    @Override
    public <T> Receipt commit(Form<T> form) throws Panic {
        return publish(form.getOwnerId(), form.getGroup(), NAME_ROOT, form.getKlass());
    }

    private <T> Receipt publish(String ownerId, String group, String name, Class<T> klass) throws Panic {
        if (String.class.getName().equals(klass.getName()) || klass.isPrimitive()) {
            throw new IllegalArgumentException("Class is ILLEGAL:" + klass.getName());
        }
        logger.debug("[begin] group:[{}] createModel:[{}]", klass.getName());
        logger.debug("[before] createExtensionSpi.commit:group=[{}],name=[{}]", group, name);
        DeclareExtensionSpi.Form extForm = new DeclareExtensionSpi.Form();
        extForm.setGroup(group);
        if (name.equals(NAME_ROOT)) {
            extForm.setName(NAME_ROOT);
        } else {
            extForm.setName(name);
        }
        extForm.setOwnerId(ownerId);
        String extId = createExtensionSpi.commit(extForm).getId();
        logger.debug("[after] createExtensionSpi.commit:extId=[{}]", extId);

        Map<String, Receipt> refs = new HashMap<>();

        List<String> ints = new ArrayList<>();

        for (Field field : klass.getDeclaredFields()) {
            String fieldName = field.getName();
            if (fieldName.startsWith("this$")) {
                continue;
            }
            Class type = field.getType();
            boolean single = type.getComponentType() == null;
            if (!single) {
                type = type.getComponentType();
            }
            if (String.class.getName().equals(type.getName()) || type.isPrimitive()) {
                DeclareIntensionSpi.PrimitiveIntensionForm form = new DeclareIntensionSpi.PrimitiveIntensionForm();
                form.setSingle(single);
                form.setField(fieldName);
                form.setStructure(type.getSimpleName());
                form.setExtId(extId);
                form.setOwnerId(ownerId);
                String intId = declareIntensionSpi.commit(form);
                ints.add(intId);
            } else {
                Receipt receipt = publish(ownerId, group, fieldName, type);
                DeclareIntensionSpi.ImportIntensionForm form = new DeclareIntensionSpi.ImportIntensionForm();
                form.setSingle(single);
                form.setField(fieldName);
                form.setRefExtId(receipt.getExtId());
                form.setExtId(extId);
                form.setOwnerId(ownerId);
                form.setStructure(type.getSimpleName());
                String intId = declareIntensionSpi.commit(form);
                refs.put(intId, receipt);
            }

        }
        Receipt receipt = new Receipt();
        receipt.setExtId(extId);
        receipt.setInts(ints);
        receipt.setRefs(refs);
        logger.debug("[end] createModel:[{}]", klass.getName());
        return receipt;
    }


}
