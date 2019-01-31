package com.fhh.technology.module.login.register;

import com.fhh.technology.base.IBasePresenter;
import com.fhh.technology.base.IBaseView;

/**
 * Created by fanhenghao
 */
public interface RegisterContract {

    interface View extends IBaseView {

        void registerSuccess();

        void registerFail(String errorMsg);

        void errorRemind(int title, int resId);

    }

    interface Presenter extends IBasePresenter {

        void registerInfo(String key, String phoneNumber, String pwd);

    }
}
