package com.wjl.commom.util.third;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class TxyApi {
    /* Basic request URL */
    private static final String URL = "csec.api.qcloud.com/v2/index.php";

    /**
     * 编码
     * @param bstr
     * @return String
     */
    private static String encode(byte[] bstr){
        String sp = System.getProperty("line.separator");
        return (new BASE64Encoder().encode(bstr)).replaceAll(sp, "");
    }

    /* Signature algorithm using HMAC-SHA1 */
    public static String hmacSHA1(String key, String text, String charset) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException
    {
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(key.getBytes(charset), "HmacSHA1"));
        return encode(mac.doFinal(text.getBytes(charset)));
    }

    /* Assemble query string */
    private static String makeQueryString(Map<String, String> args, String charset) throws UnsupportedEncodingException
    {
        String url = "";

        for (Map.Entry<String, String> entry : args.entrySet())
            url += entry.getKey() + "=" + (charset == null ? entry.getValue() : URLEncoder.encode(entry.getValue(), charset)) + "&";

        return url.substring(0, url.length()-1);
    }

    /* Generates an available URL */
    public static String makeURL(
            String method,
            String action,
            String region,
            String secretId,
            String secretKey,
            Map<String, String> args,
            String charset)
            throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException
    {
        SortedMap<String, String> arguments = new TreeMap<String, String>();

        /* Sort all parameters, then calculate signature */
        arguments.putAll(args);
        arguments.put("Nonce", String.valueOf((int)(Math.random() * 0x7fffffff)));
        arguments.put("Action", action);
        arguments.put("Region", region);
        arguments.put("SecretId", secretId);
        arguments.put("Timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        arguments.put("Signature", hmacSHA1(secretKey, String.format("%s%s?%s", method, URL, makeQueryString(arguments, null)), charset));

        /* Assemble final request URL */
        return String.format("https://%s?%s", URL, makeQueryString(arguments, charset));
    }
}