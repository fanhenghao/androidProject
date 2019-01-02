package com.fhh.technology.base;

import android.app.Application;

/**
 * desc:Technology的Application类
 * Created by fanhenghao
 */
public class TechnologyApplication extends Application {
    private static Application mApplication;

    public static Application getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
}
