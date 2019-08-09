package com.wjl.commom.util.third;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Http Get/Post 工具
 *
 * @author 秦瑞华
 */
@Slf4j
public class HttpUtil_Tondun {

    private static final long DEFAULT_CONNECT_TIMEOUT = 30;
    private static final long DEFAULT_READ_TIMEOUT = 30;
    private static OkHttpClient client;

    static {
    	client = new OkHttpClient.Builder()
    		.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS).build();
    }
    
    /**
     * Http Get
     *
     * @param url
     * @param parms
     * @return
     */
    public static String doGet(String url, Map<String, String> parms) {
        StringBuilder paramBuilder = new StringBuilder();
        if (parms != null && parms.size() > 0) {
            for (String name : parms.keySet()) {
                paramBuilder.append("&").append(name).append("=").append(parms.get(name));
            }
        }
        String paramStr = paramBuilder.toString();

        String okUrl = url + ((url.indexOf("?") != -1) ? paramStr : ("?" + paramStr));
        System.out.println(okUrl);
        Request request = new Request.Builder()
                .url(okUrl).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * Http Post
     *
     * @param url
     * @param form
     * @return
     */
    public static String doPost(String url, Map<String, String> form) {
    	long start = System.currentTimeMillis();
        Builder formBodyBuilder = new Builder();
        if (form != null) {
            form.forEach((name, value) -> {
                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
                    return;
                }
                formBodyBuilder.add(name, value);
            });
        }
        FormBody formBody = formBodyBuilder.build();
        //RequestBody postBody = RequestBody.create(MediaType.parse("text/json; charset=utf-8"), formBody.);
        Request request = new Request.Builder()
                .url(url)
                .post(formBody).build();
        try {
            Response response = client.newCall(request).execute();
            long use = System.currentTimeMillis() - start;
            log.info("OkHttp Post, use="+ use);
            return response.body().string();
        } catch (Exception e) {
            long use = System.currentTimeMillis() - start;
            log.error(String.format("【调用第三方异常】：use=%s, %s", use, e.getMessage()));
        }
        return "";
    }
}

