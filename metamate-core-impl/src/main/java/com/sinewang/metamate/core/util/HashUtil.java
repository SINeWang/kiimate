package com.sinewang.metamate.core.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public class HashUtil {

    public static String hashHex(String... messages) {
        byte[] digist = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            //ignore
        }
        for (String message : messages) {
            if (StringUtils.isEmpty(message)) {
                continue;
            }
            try {
                byte[] data = message.getBytes("UTF-8");
                md.update(data);
            } catch (UnsupportedEncodingException e) {
                //ignore;
            }
        }
        digist = md.digest();
        return Hex.encodeHexString(digist);
    }

}
