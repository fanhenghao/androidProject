package com.fhh.technology.module.home;

import android.app.Activity;

/**
 * desc:
 * Created by fhh on 2018/9/18
 */

public class HomePresenter implements HomeContract.Presenter {
    private Activity mActivity;
    private HomeContract.View mView;

    public HomePresenter(Activity activity, HomeContract.View view) {
        this.mActivity = activity;
        this.mView = view;
    }
}
