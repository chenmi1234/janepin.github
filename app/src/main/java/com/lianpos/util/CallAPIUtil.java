package com.lianpos.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 调用API共同类
 * Created by wangshuai on 2017/12/7 0007.
 */

public class CallAPIUtil {

    public static final MediaType JSONTYPE = MediaType.parse("application/json; charset=utf-8");
    static String result = "";
    public static String ObtainFun(String json, String url) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSONTYPE, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            result = response.body().string();
            result = URLDecoder.decode(result, "UTF-8");

            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
