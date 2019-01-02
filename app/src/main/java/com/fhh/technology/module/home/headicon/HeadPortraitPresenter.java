package com.fhh.technology.module.home.headicon;

import android.content.Context;

/**
 * Created by fanhenghao
 */
public class HeadPortraitPresenter implements HeadPortraitContract.Presenter {

    private Context mContext;
    private HeadPortraitContract.View mView;

    public HeadPortraitPresenter(Context context, HeadPortraitContract.View view) {
        this.mContext = context;
        this.mView = view;
    }
}
