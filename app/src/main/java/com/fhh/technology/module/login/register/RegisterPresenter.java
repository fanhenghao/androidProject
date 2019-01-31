package com.fhh.technology.module.login.register;

import android.content.Context;
import android.text.TextUtils;

import com.fhh.technology.R;
import com.fhh.technology.network.bean.RegisterBean;

/**
 * Created by fanhenghao
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    private Context mContext;
    private RegisterContract.View mView;
    private final IRegisterModel mModel;
    private static final int mNumberLength = 11;
    private static final int mPasswordLength = 6;

    public RegisterPresenter(Context context, RegisterContract.View view) {
        this.mContext = context;
        this.mView = view;
        mModel = new RegisterModel();
    }

    @Override
    public void registerInfo(String key, String phoneNumber, String pwd) {

        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(pwd)) {
            mView.errorRemind(R.string.register_dialog_error, R.string.register_dialog_error_content);
            return;
        }
        if (phoneNumber.length() < mNumberLength || pwd.length() < mPasswordLength) {
            mView.errorRemind(R.string.login_dialog_remind, R.string.login_dialog_error_remind);
            return;
        }

        mModel.registerInfo(key, phoneNumber, pwd, new IRegisterModel.registerListener() {
            @Override
            public void responseRegisterSuccess(RegisterBean loginBean) {
                mView.registerSuccess();
            }

            @Override
            public void responseRegisterFail(String errorMsg) {
                mView.registerFail(errorMsg);
            }
        });
    }
}
