package com.fhh.technology.network.http;

import android.net.ParseException;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.adapter.rxjava.HttpException;

/**
 * desc:http请求结果
 * Created by fanhenghao
 */

public class AppResponse<T> extends ResourceSubscriber<T> {
    @Override
    public void onStart() {
        super.onStart();
        Log.e("AppResponse", "onStart");
    }

    @Override
    public void onComplete() {
        Log.e("AppResponse", "onComplete");
    }

    @Override
    public void onError(Throwable e) {
        Log.e("AppResponse", "onError");
        Throwable throwable = e;
        //获取根源 异常
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }
        if (e instanceof HttpCodeException) {//服务器返回的错误
            Log.e("TECHNOLOGY", "http_code:" + ((HttpCodeException) e).getCode());
        } else if (e instanceof HttpException) {//对网络异常 打出相应的log
            HttpException httpException = (HttpException) e;
            String errorMsg = httpException.getMessage();
            if (TextUtils.isEmpty(errorMsg)) {
                errorMsg = "网络错误...";
                Log.e("TECHNOLOGY", errorMsg);
            }
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {//解析异常
            Log.e("TECHNOLOGY", "解析异常...");
        } else if (e instanceof UnknownHostException) {
            Log.e("TECHNOLOGY", "域名解析错误...");
        } else if (e instanceof SocketTimeoutException) {
            Log.e("TECHNOLOGY", "网络链接超时...");
        } else {
            Log.e("TECHNOLOGY", "网络错误...");
        }
    }

    @Override
    public void onNext(T t) {
        Log.e("AppResponse", "onNext");
    }

}
