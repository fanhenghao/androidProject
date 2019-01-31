package com.fhh.technology.module.login.register;


import com.fhh.technology.network.bean.RegisterBean;
import com.fhh.technology.network.http.AppResponse;
import com.fhh.technology.network.http.AppSubscribe;
import com.fhh.technology.network.http.BaseHttp;

/**
 * Created by fanhenghao
 */
public class RegisterModel implements IRegisterModel {
    @Override
    public void registerInfo(String key, String phone, String pwd, final registerListener listener) {
        new AppSubscribe.Builder().setFlowable(BaseHttp.getBaseHttp().registerInfo(key, phone, pwd))
                .subscribe(new AppResponse<RegisterBean>() {
                    @Override
                    public void onNext(RegisterBean registerBean) {
                        super.onNext(registerBean);
                        if (listener != null) {
                            listener.responseRegisterSuccess(registerBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (listener != null) {
                            listener.responseRegisterFail(e.getMessage());
                        }
                    }
                }).build();
    }
}
