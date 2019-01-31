package com.fhh.technology.module.login;

import com.fhh.technology.network.bean.LoginBean;

/**
 * Created by fanhenghao
 */
public interface ILoginModel {

    void loginInfo(String phone, String pwd, ILoginModel.loginListener listener);


    interface loginListener {

        void responseLoginSuccess(LoginBean loginBean);

        void responseLoginFail(String errorMsg);
    }
}
