package com.fhh.technology.module.discover;

import android.content.Context;

/**
 * desc:
 * Created by fhh on 2018/10/14
 */

public class DiscoverPresenter implements DiscoverContract.Presenter {
    private Context mContext;
    private DiscoverContract.View mView;

    public DiscoverPresenter(Context context, DiscoverContract.View view) {
        this.mContext = context;
        this.mView = view;
    }
}
