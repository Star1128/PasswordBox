package com.ethan.ethanutils;

import okhttp3.*;

/**
 * NOTE:
 *
 * @author wxc 2021/11/22
 * @version 1.0.0
 */
public class OkHttpUtil {

    public static void sendRequestWithOkHttp(String address, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);//在enqueue方法里已开好子线程，最终的请求结果会回调到callback中
    }
}
