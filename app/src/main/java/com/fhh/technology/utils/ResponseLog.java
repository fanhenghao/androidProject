package com.fhh.technology.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by fanhenghao
 */

public class ResponseLog implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();

        long t1 = System.nanoTime();//请求发起的时间
//        Log.e("FHH",String.format("Send Request: %s on %s%n%s", request.url(), chain.connection(), request.headers()));
        Log.e("TECHNOLOGY", "------------> " + request.method() + " <------------");
        Log.e("TECHNOLOGY", String.format("Send Request----------->: %s", request.url()));
        Log.e("TECHNOLOGY", String.format("Request Connection----->: %s", chain.connection()));
        Log.e("TECHNOLOGY", String.format("Request Header--------->: %s", request.headers()));
        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Log.e("TECHNOLOGY", String.format("Response Result----->: %s", response.request().url()));
        Log.e("TECHNOLOGY", String.format("Response Json------->:%s", responseBody.string()));
        Log.e("TECHNOLOGY", String.format("Response Time------->:%.1fms", (t2 - t1) / 1e6d));
        Log.e("TECHNOLOGY", String.format("Response Header----->:%s", response.headers()));
//        Log.e("FHH",String.format("Response Result: [%s] %n return json:%s %.1fms%n%s",
//                response.request().url(),
//                responseBody.string(),
//                (t2 - t1) / 1e6d,
//                response.headers()));

        return response;
    }
}
