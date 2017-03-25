package wang.yanjiong.metamate.core.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import wang.yanjiong.metamate.core.model.Extension;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by WangYanJiong on 3/24/17.
 */
public class MessageDigestUtil {

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
            digist = md.digest();
        }
        return Hex.encodeHexString(digist);
    }


}
