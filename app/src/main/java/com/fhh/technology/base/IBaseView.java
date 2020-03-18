package com.fhh.technology.base;

import android.app.Activity;

/**
 * desc:
 * Created by fanhenghao
 */
public interface IBaseView {

    void showToast(String s);

    void showToast(int resId);

    Activity getActivity();
}
