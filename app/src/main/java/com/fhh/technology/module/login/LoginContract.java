package com.fhh.technology.module.login;


/**
 * desc:
 * Created by fhh on 2018/9/14
 */

public interface LoginContract {
    interface View {
        void errorRemind(int title, int reeId);

        void loginSuccess(int successRemind);
    }

    interface Presenter {
        void manageNumberAndPassword(String number, String password);
    }
}
