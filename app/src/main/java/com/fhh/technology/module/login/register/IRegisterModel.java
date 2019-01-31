package com.fhh.technology.module.login.register;


import com.fhh.technology.network.bean.RegisterBean;

/**
 * Created by fanhenghao
 */
public interface IRegisterModel {

    void registerInfo(String key, String phone, String pwd, IRegisterModel.registerListener listener);

    interface registerListener {

        void responseRegisterSuccess(RegisterBean loginBean);

        void responseRegisterFail(String errorMsg);
    }
}
