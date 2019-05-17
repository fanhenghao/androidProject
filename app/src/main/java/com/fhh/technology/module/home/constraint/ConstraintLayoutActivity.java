package com.fhh.technology.module.home.constraint;

import android.app.Activity;
import android.content.Intent;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;

/**
 * Created by fanhenghao
 * desc:关于布局ConstraintLayout的使用介绍（主要关注布局）
 */
public class ConstraintLayoutActivity extends BaseActivity {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ConstraintLayoutActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_constraint_layout;
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
