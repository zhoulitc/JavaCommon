package com.dgsoft.dts.web.common;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * AES加密类，线程安全
 * @author li.zhou 
 * @dts.date 2013-1-14 上午11:38:48 
 * @version 1.0 
 */
public final class AESHelper {
    
    private final static Logger log = LogManager.getLogger(AESHelper.class.getName());
    
    private final static String KEY ="draonsoftitabc321zb=ABCDEFGHIJKL";
    
    /**
     * AES-256-ECB加密方法，失败返回""
     * @param strEncrypt String 需要进行加密的字符串
     * @return String 进行加密之后的字符串
     */
    public static String Encrypt(String strEncrypt) { 
        String strResult = "";
        log.debug(String.format("method start String[strEncrypt:%s]", strEncrypt));
        if (strEncrypt != null) {
            try {
                SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes("utf-8"), "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
                byte[] encrypted = cipher.doFinal(strEncrypt.getBytes("utf-8"));
                strResult = new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
            } catch(Exception e) {
                log.debug(e.getMessage(), e);
                strResult = "";
            }
        }
        log.debug(String.format("method stop return:String[%s]", strResult));
        return strResult;
    }   
}