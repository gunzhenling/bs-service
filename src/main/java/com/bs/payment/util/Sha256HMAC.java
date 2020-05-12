package com.bs.payment.util;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * HMAC-SHA256 加密类
 * @author zhenling
 *
 */
@Slf4j
@Component
public class Sha256HMAC {
	/**
     * 微信证书HMAC-SHA256签名
     *
     * @param params
     * @param secret
     * @return
     */
    public static String wechatCertficatesSignBySHA256(Map<String, String> params, String secret) {
        // 需要保证排序
        SortedMap<String, String> sortedMap = new TreeMap<>(params);
        // 将参数拼接成字符串
        StringBuilder toSign = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            String value = params.get(key);
            if (StringUtils.isNotEmpty(value) && !"sign".equals(key) && !"key".equals(key)) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }
        toSign.append("key=").append(secret);
        String sign = sha256_HMAC(toSign.toString(), secret);
        
        log.info("Sha256HMAC-wechatCertficatesSignBySHA256-info: sign content : {}, sign={}",toSign.toString(),sign);

        return sign;
    }

    /**
     * 加密HMAC-SHA256
     *
     * @param message
     * @param secret
     * @return
     */
    private static String sha256_HMAC(String message, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            String sign = byteArrayToHexString(bytes);
            sign = sign.toUpperCase();
            return sign;
        } catch (Exception e) {
            log.error("sha256_HMAC加密异常", e);
        }
        return null;
    }

    /**
     * 加密后的字节转字符串
     *
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp = null;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
}
