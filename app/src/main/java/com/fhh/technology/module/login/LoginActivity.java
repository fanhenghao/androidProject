package com.fhh.technology.module.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import android.content.pm.PackageManager;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.module.login.register.RegisterActivity;
import com.fhh.technology.module.main.MainActivity;
import com.fhh.technology.module.update.DownloadService;
import com.fhh.technology.module.update.UpdatePopup;
import com.fhh.technology.utils.SharedPreferenceUtils;
import com.fhh.technology.utils.StringUtils;
import com.fhh.technology.utils.ToastUtil;

import androidx.core.app.ActivityCompat;
import butterknife.BindView;

/**
 * desc:登录页
 * Created by fhh on 2018/9/14
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.View {

    private static final int REQUEST_PERMISSION = 1;

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
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_version)
    TextView mTvVersion;

    private String[] mPermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private boolean showPassword;
    private LoginPresenter mPresenter;
    private UpdatePopup mUpdatePopup;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initPresenter() {
        if (mPresenter == null) {
            mPresenter = new LoginPresenter(this, this);
        }
    }

    @Override
    public void initData() {
        initListener();
        mTvVersion.setText("v" + StringUtils.getAppVersionName(this));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, mPermission, REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                //权限
                boolean permission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (!permission) {
                    ToastUtil.showToast(mActivity, "请前往设置给予存储权限");
                    finish();
                } else {
//                    String version = StringUtils.getAppVersionName(mActivity);
//                    if (TextUtils.equals(version, "1.0.0")) {//版本更新
//                        mUpdatePopup = new UpdatePopup(mActivity);
//                        mUpdatePopup.setOutSideDismiss(false);
//                        mUpdatePopup.showPopupWindow();
//                        mUpdatePopup.findViewById(R.id.tv_skip).setOnClickListener(v -> mUpdatePopup.dismiss());
//                        mUpdatePopup.findViewById(R.id.tv_update).setOnClickListener(v -> {
//                            Intent intent = new Intent(mActivity, DownloadService.class);
//                            intent.putExtra("url", "https://github.com/fanhenghao/androidProject/raw/master/technology_rel1.0.1.apk");
////                            intent.putExtra("url", "http://yunchudian.oss-cn-shanghai.aliyuncs.com/mobiletest/yunchudianManagerOnline.apk");
//                            mActivity.startService(intent);
//                            ToastUtil.showToast(mActivity, "后台更新下载中...");
//                            mUpdatePopup.dismiss();
//                        });
//                    }
                }
                break;
        }
    }

    @Override
    public void onDestroyActivity() {
        mPresenter = null;
    }

    private void initListener() {
        mIbDeletePassword.setOnClickListener(this);
        mIbHidePassword.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
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
        mEtNumbers.setText("13592127810");
        mEtNumbers.setSelection(mEtNumbers.length());
        mEtPasswords.setText("123456");
        mIbDeleteNumber.setOnClickListener(this);
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
            case R.id.btn_login:
                String number = mEtNumbers.getText().toString().trim();
                String password = mEtPasswords.getText().toString().trim();
                SharedPreferenceUtils.getInstance(mActivity).put(Constant.KEY_LOGIN_ACCOUNT,number);
                MainActivity.start(this);
//                showLoading();
//                if (mPresenter != null) {
//                    mPresenter.manageNumberAndPassword(number, password);
//                }
                break;
            case R.id.tv_register:
                RegisterActivity.start(this);
                break;
        }
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
    public void loginSuccess(int successRemind) {
        hideLoading();
        ToastUtil.showToast(this, successRemind);
        MainActivity.start(this);
        finish();
    }

    @Override
    public void loginError(String errorMsg) {
        hideLoading();
        new MaterialDialog.Builder(this)
                .title(R.string.login_dialog_net_error_title)
                .content(R.string.login_dialog_net_error_content)
                .positiveColor(getResources().getColor(R.color.btn_login_color))
                .positiveText(R.string.login_dialog_confirm)
                .negativeText(R.string.login_dialog_cancel)
                .negativeColor(getResources().getColor(R.color.text_tab_n))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        loginSuccess(R.string.login_location);
                    }
                })
                .show();
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
