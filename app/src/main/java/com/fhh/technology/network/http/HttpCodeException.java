package com.fhh.technology.network.http;

/**
 * Created by fanhenghao
 */
public class HttpCodeException extends Exception {

    int code;
    public HttpCodeException(int code, String s) {
        super(s);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
