package com.soaplat.cmsmgr.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class Encrypt {
	private static Logger logger = Logger.getLogger(Encrypt.class);
    /**
     * AES解密
     * @param enText 密文
     * @param key 密钥
     * @return 明文
     */
    public static String decryptAES(String enText, String key) {
        try {
            //判断Key是否正确
            if (key == null) {
            	logger.warn("Key为空null");
                return null;
            }
            //判断Key是否为16位
            if (key.length() != 16) {
            	logger.warn("Key长度不是16位");
                return null;
            }
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = hex2byte(enText);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
            	logger.error(e.toString());
                return null;
            }
        } catch (Exception ex) {
            logger.error(ex.toString());
            return null;
        }
    }
    /**
     * AES加密
     * @param text 待加密明文
     * @param key 密钥
     * @return 密文
     */
    public static String encryptAES(String text, String key) {
        if (key == null) {
        	logger.warn("Key为空null");
            return null;
        }
        //判断Key是否为16位
        if (null == key || 16 != key.length()) {
            logger.warn("Key长度不是16位");
            return null;
        }
        try {
			byte[] raw = key.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(text.getBytes());
			return byte2hex(encrypted).toLowerCase();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
    }
    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
}