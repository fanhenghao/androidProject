package com.fhh.technology.module.login;

import com.fhh.technology.base.Constant;
import com.fhh.technology.network.bean.BaseBean;
import com.fhh.technology.network.http.AppResponse;
import com.fhh.technology.network.http.AppSubscribe;
import com.fhh.technology.network.http.BaseHttp;
import com.fhh.technology.network.bean.LoginBean;
import com.fhh.technology.network.http.HttpCodeException;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by fanhenghao
 */
public class LoginModel implements  ILoginModel{

    @Override
    public void loginInfo(String phone, String pwd, final ILoginModel.loginListener listener) {

        new AppSubscribe.Builder()
                .setFlowable(BaseHttp.getBaseHttp().loginInfo(Constant.HTTP_KEY, phone, pwd))
                .subscribe(new AppResponse<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        super.onNext(loginBean);
                        if (listener != null) {
                            listener.responseLoginSuccess(loginBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (listener != null) {
                            listener.responseLoginFail(e.getMessage());
                        }
                    }
                }).build();

//        BaseHttp.getBaseHttp().loginInfo(Constant.HTTP_KEY, phone, pwd)
//                .flatMap(new AppException())
//                .subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
//                .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程UI
//                .subscribe(new AppResponse<BaseBean<LoginBean>>() {
//                    @Override
//                    public void onNext(BaseBean<LoginBean> loginBean) {
//                        super.onNext(loginBean);
//                        if (listener != null) {
//                            listener.loginSuccess(loginBean.getData());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        if (listener != null) {
//                            listener.loginFail(e.getMessage());
//                        }
//                    }
//                });
    }
}
