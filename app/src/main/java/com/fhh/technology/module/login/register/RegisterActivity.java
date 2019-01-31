package com.fhh.technology.module.login.register;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.module.main.MainActivity;

import butterknife.BindView;

/**
 * Created by fanhenghao
 */
public class RegisterActivity extends BaseActivity implements RegisterContract.View, View.OnClickListener {


    @BindView(R.id.et_numbers)
    EditText mEtNumbers;
    @BindView(R.id.et_passwords)
    EditText mEtPasswords;
    @BindView(R.id.ib_delete_number)
    ImageButton mIbDeleteNumber;
    @BindView(R.id.ib_delete_password)
    ImageButton mIbDeletePassword;
    @BindView(R.id.ib_hide_password)
    ImageButton mIbHidePassword;
    @BindView(R.id.btn_register)
    Button mBtnRegister;

    private RegisterPresenter mPresenter;
    private boolean showPassword;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initPresenter() {
        mPresenter = new RegisterPresenter(this, this);
    }

    @Override
    public void initData() {
        mIbDeleteNumber.setOnClickListener(this);
        mIbDeletePassword.setOnClickListener(this);
        mIbHidePassword.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mEtNumbers.addTextChangedListener(new MyWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    mIbDeleteNumber.setVisibility(View.VISIBLE);
                } else {
                    mIbDeleteNumber.setVisibility(View.GONE);
                }
            }
        });
        mEtPasswords.addTextChangedListener(new MyWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    mIbDeletePassword.setVisibility(View.VISIBLE);
                    mIbHidePassword.setVisibility(View.VISIBLE);
                } else {
                    mIbDeletePassword.setVisibility(View.GONE);
                    mIbHidePassword.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void onDestroyActivity() {
        mPresenter = null;
    }

    @Override
    public void registerSuccess() {
        showToast("注册成功！");
        MainActivity.start(this);
        finish();
    }

    @Override
    public void registerFail(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void errorRemind(int title, int resId) {
        new MaterialDialog.Builder(this)
                .title(title)
                .content(resId)
                .positiveColor(getResources().getColor(R.color.btn_login_color))
                .positiveText(R.string.login_dialog_confirm)
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_delete_number:
                mEtNumbers.setText(null);
                mIbDeleteNumber.setVisibility(View.GONE);
                break;
            case R.id.ib_delete_password:
                mEtPasswords.setText(null);
                mIbDeletePassword.setVisibility(View.GONE);
                mIbHidePassword.setVisibility(View.GONE);
                break;
            case R.id.ib_hide_password:
                if (showPassword) {
                    mIbHidePassword.setBackground(getResources().getDrawable(R.drawable.ic_login_eye_open));
                    mEtPasswords.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPassword = false;
                    mEtPasswords.setSelection(mEtPasswords.getText().length());
                } else {
                    mIbHidePassword.setBackground(getResources().getDrawable(R.drawable.ic_login_eye_close));
                    mEtPasswords.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassword = true;
                    mEtPasswords.setSelection(mEtPasswords.getText().length());
                }
                break;
            case R.id.btn_register:
                String number = mEtNumbers.getText().toString().trim();
                String password = mEtPasswords.getText().toString().trim();
                if (mPresenter != null) {
                    mPresenter.registerInfo(Constant.HTTP_KEY, number, password);
                }
                break;
        }
    }

    class MyWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
