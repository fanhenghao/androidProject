package com.fhh.technology.network.http;

import com.fhh.technology.base.Constant;
import com.fhh.technology.utils.LoggerInterceptor;
import com.fhh.technology.utils.ResponseLog;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fanhenghao
 */

public class BaseHttp {

    private static final long TIME_OUT = 10000;


    public static NetService getBaseHttp() {
        return new Builder()
                .setBaseUrl(Constant.HTTP_BASE)
                .setLogInterceptor(true)
                .builder();
    }

    public static class Builder {

        private String mBaseUrl;
        private boolean mLogInterceptor;

        public Builder setBaseUrl(String baseUrl) {
            this.mBaseUrl = baseUrl;
            return this;
        }

        public Builder setLogInterceptor(boolean logInterceptor) {
            this.mLogInterceptor = logInterceptor;
            return this;
        }

        public NetService builder() {
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
            okHttpClient.readTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
            okHttpClient.writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS);

            okHttpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request builder = request.newBuilder()
                            .addHeader("client", "android")
                            .header("Content-Type", "application/json")
                            .method(request.method(), request.body())
                            .build();
                    return chain.proceed(builder);
                }
            });
            if (mLogInterceptor) {
//                okHttpClient.addInterceptor(new LoggerInterceptor("technology", true));//日志插值器
                okHttpClient.addInterceptor(new ResponseLog());//日志插值器
            }

            return new Retrofit.Builder()
                    .baseUrl(mBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient.build())
                    .build()
                    .create(NetService.class);
        }
    }

}
