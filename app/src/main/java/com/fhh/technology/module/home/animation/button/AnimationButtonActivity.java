package com.fhh.technology.module.home.animation.button;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.utils.ToolBarOptions;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;

/**
 * desc:animation button style
 * Created by fhh on 2018/9/25
 */

public class AnimationButtonActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.btn_custom)
    Button mBtnCustom;
    @BindView(R.id.btn_style_1)
    Button mBtnStyle1;
    @BindView(R.id.btn_style_2)
    Button mBtnStyle2;
    @BindView(R.id.btn_style_3)
    Button mBtnStyle3;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AnimationButtonActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_animation_button;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_animation), Constant.STATUS_BAR_ALPHA);
        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = true;
        options.backgroundColor = R.color.bg_animation;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {
        mBtnCustom.setOnClickListener(this);
        mBtnStyle1.setOnClickListener(this);
        mBtnStyle2.setOnClickListener(this);
        mBtnStyle3.setOnClickListener(this);
    }

    @Override
    public void onDestroyActivity() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_custom:
                //问题：由于显示的是一个圆形，所以当createCircularReveal的第二和第三个参数centerX，centerY在拐角处时，涉及到一个正方形的四个拐角和圆形的宽高问题，mBtnCustom.getHeight()=240，
                // mBtnCustom.getWidth()=240，v.getRight()=480， v.getLeft()=240.2R=240,正方形对角线=240*根号2，
                // createCircularReveal方法的最后一个参数endRadius应该取（(对角线-2R)/2+2R）,才能保证执行时间和setDuration方法设置的时间一致
                clickAnimation(0, 0, 0, (float) ((mBtnCustom.getWidth() * Math.sqrt(2) - mBtnCustom.getWidth()) / 2 + mBtnCustom.getWidth()));
//                Log.e("FHH", "getMeasuredWidth:" + v.getMeasuredWidth());
//                Log.e("FHH", "getWidth:" + v.getWidth() + "---getHeight:" + v.getHeight());
//                Log.e("FHH", "getLeft:" + v.getLeft());
//                Log.e("FHH", "getRight:" + v.getRight());
                break;
            case R.id.btn_style_1:
                clickAnimation(mBtnCustom.getWidth() / 2, mBtnCustom.getHeight() / 2, 0, mBtnCustom.getWidth());
                break;
            case R.id.btn_style_2:
                clickAnimation(mBtnCustom.getWidth() / 2, mBtnCustom.getHeight() / 2, mBtnCustom.getWidth(), 0);
                break;
            case R.id.btn_style_3:
                clickAnimation(mBtnCustom.getWidth(), mBtnCustom.getHeight(), 0, (float) ((mBtnCustom.getWidth() * Math.sqrt(2) - mBtnCustom.getWidth()) / 2 + mBtnCustom.getWidth()));
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void clickAnimation(int centerX, int centerY, float startRadius, float endRadius) {
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(mBtnCustom, centerX, centerY, startRadius, endRadius);
        circularReveal.setDuration(1000);
        circularReveal.start();
    }
}
