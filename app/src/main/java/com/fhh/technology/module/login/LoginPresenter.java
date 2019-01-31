package com.fhh.technology.module.login;

import android.app.Activity;
import android.text.TextUtils;

import com.fhh.technology.R;
import com.fhh.technology.network.bean.LoginBean;

/**
 * desc:
 * Created by fhh on 2018/9/14
 */

public class LoginPresenter implements LoginContract.Presenter {

    private static final int mNumberLength = 11;
    private static final int mPasswordLength = 6;

    private Activity mActivity;
    private LoginContract.View mView;
    private final ILoginModel mModel;

    public LoginPresenter(Activity activity, LoginContract.View view) {
        this.mActivity = activity;
        this.mView = view;
        mModel = new LoginModel();
    }

    @Override
    public void manageNumberAndPassword(String number, String password) {
        if (mView == null) {
            return;
        }
        if (TextUtils.isEmpty(number) || TextUtils.isEmpty(password)) {
            mView.errorRemind(R.string.login_dialog_error, R.string.login_dialog_error_content);
            return;
        }
        if (number.length() < mNumberLength || password.length() < mPasswordLength) {
            mView.errorRemind(R.string.login_dialog_remind, R.string.login_dialog_error_remind);
            return;
        }

        mModel.loginInfo(number, password, new ILoginModel.loginListener() {
            @Override
            public void responseLoginSuccess(LoginBean loginBean) {
                mView.loginSuccess(R.string.login_success);
            }

            @Override
            public void responseLoginFail(String errorMsg) {
                //请求失败
                mView.loginError(errorMsg);
            }
        });

    }
}
