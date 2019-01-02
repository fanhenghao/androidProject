package com.fhh.technology.module.home.customview;

import android.app.Activity;
import android.content.Intent;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;

/**
 * desc:自定义view类
 * Created by fhh on 2018/9/21
 */

public class ViewActivity extends BaseActivity {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ViewActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_view;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroyActivity() {

    }
}
