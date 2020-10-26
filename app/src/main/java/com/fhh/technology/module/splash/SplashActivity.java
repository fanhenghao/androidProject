package com.fhh.technology.module.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fhh.technology.base.Constant;
import com.fhh.technology.base.TechnologyApplication;
import com.fhh.technology.module.login.LoginActivity;
import com.fhh.technology.module.main.MainActivity;
import com.fhh.technology.utils.SharedPreferenceUtils;

/**
 * desc:
 * Created by fhh on 2018/9/13
 */

public class SplashActivity extends AppCompatActivity implements Runnable {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        new Handler().postDelayed(this, 500);
    }

    @Override
    public void run() {
        String account = SharedPreferenceUtils.getInstance(this).getString(Constant.KEY_LOGIN_ACCOUNT);
        if (TextUtils.isEmpty(account)) {
            LoginActivity.start(this);
        } else {
            MainActivity.start(this);
        }
        finish();
    }
}
