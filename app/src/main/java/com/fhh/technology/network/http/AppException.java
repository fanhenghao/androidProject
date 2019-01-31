package com.fhh.technology.network.http;

import com.fhh.technology.base.Constant;
import com.fhh.technology.network.bean.BaseBean;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;


/**
 * Created by fanhenghao
 */
public class AppException<T> implements Function<BaseBean<T>, Flowable<T>> {
    @Override
    public Flowable<T> apply(BaseBean<T> baseBean) {
        if (baseBean.getCode() != Constant.HTTP_CODE) {
            return Flowable.error(new HttpCodeException(baseBean.getCode(), baseBean.getMsg()));
        }
        return Flowable.just(baseBean.getData());
    }
}
