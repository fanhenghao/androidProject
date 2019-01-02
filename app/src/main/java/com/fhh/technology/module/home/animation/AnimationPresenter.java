package com.fhh.technology.module.home.animation;

import android.app.Activity;

/**
 * desc:
 * Created by fhh on 2018/9/25
 */

public class AnimationPresenter implements AnimationContract.Presenter {

    private Activity mActivity;
    private AnimationContract.View mView;

    public AnimationPresenter(Activity activity, AnimationContract.View view) {
        this.mActivity = activity;
        this.mView = view;
    }
}
