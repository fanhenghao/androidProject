package com.fhh.technology.module.home.animation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.module.home.animation.button.AnimationButtonActivity;
import com.fhh.technology.module.home.animation.other.AnimationOtherActivity;
import com.fhh.technology.utils.ToastUtil;
import com.fhh.technology.utils.ToolBarOptions;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * desc:动画类
 * Created by fhh on 2018/9/21
 */

public class AnimationActivity extends BaseActivity implements AnimationContract.View, AnimationAdapter.ButtonListener {

    @BindView(R.id.animation_rv)
    RecyclerView mAnimationRv;

    private AnimationPresenter mPresenter;
    private AnimationAdapter mAdapter;
    public static final List<String> animationList = new ArrayList<>();

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AnimationActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_animation;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_animation), Constant.STATUS_BAR_ALPHA);
        ToolBarOptions options = new ToolBarOptions();
        options.backgroundColor = R.color.bg_animation;
        options.isNeedNavigate = true;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {
        if (mPresenter == null) {
            mPresenter = new AnimationPresenter(this, this);
        }
    }

    @Override
    public void initData() {
        if (mAdapter == null) {
            if (animationList.size() == 0) {
                animationList.add("animation button style");
                animationList.add("animation custom style");
                animationList.add("animation else style");
            }
            mAdapter = new AnimationAdapter(this, animationList);
        }
        mAnimationRv.setLayoutManager(new LinearLayoutManager(this));
        mAnimationRv.setAdapter(mAdapter);
        mAdapter.setListener(this);
    }

    @Override
    public void onDestroyActivity() {
        mPresenter = null;
    }

    @Override
    public void onClick(int position) {
        if (position == Constant.ANIMATION_BUTTON_STYLE) {
            AnimationButtonActivity.start(this);
        } else if (position == Constant.ANIMATION_CUSTOM_STYLE) {
            ToastUtil.showToast(this, "ANIMATION_CUSTOM_STYLE");
        } else if (position == Constant.ANIMATION_OTHER_STYLE) {
            AnimationOtherActivity.start(this);
        }
    }
}
