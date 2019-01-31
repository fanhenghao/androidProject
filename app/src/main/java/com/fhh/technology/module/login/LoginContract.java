package com.fhh.technology.module.login;


import com.fhh.technology.base.IBasePresenter;
import com.fhh.technology.base.IBaseView;

/**
 * desc:
 * Created by fhh on 2018/9/14
 */

public interface LoginContract {
    interface View extends IBaseView {
        void errorRemind(int title, int reeId);

        void loginSuccess(int successRemind);

        void loginError(String errorMsg);
    }

    interface Presenter extends IBasePresenter {
        void manageNumberAndPassword(String number, String password);
    }
}
