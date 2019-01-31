package com.fhh.technology.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fhh.technology.utils.ToastUtil;
import com.fhh.technology.utils.ToolBarOptions;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * desc:
 * Created by fhh on 2018/9/18
 */

public abstract class BaseFragment extends Fragment {
    private Unbinder mUnbinder;
    private Activity mActivity;
    private View mView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(setContentLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, mView);
        Log.e("TECHNOLOGY", this.getClass().getSimpleName() + "------onCreateView");
        initToolBar();
        initPresenter();
        initData();
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("TECHNOLOGY", this.getClass().getSimpleName() + "------onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("TECHNOLOGY", this.getClass().getSimpleName() + "------onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TECHNOLOGY", this.getClass().getSimpleName() + "------onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TECHNOLOGY", this.getClass().getSimpleName() + "------onPause");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("TECHNOLOGY", this.getClass().getSimpleName() + "------onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TECHNOLOGY", this.getClass().getSimpleName() + "------onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onDestroyFragment();
        mUnbinder.unbind();
        Log.e("TECHNOLOGY", this.getClass().getSimpleName() + "------onDestroy");
    }

    public void setToolBar(int toolBarId, ToolBarOptions options) {
        Toolbar toolbar = mView.findViewById(toolBarId);
        if (options.titleId != 0) {
            toolbar.setTitle(options.titleId);
        } else {
            toolbar.setTitle("");
        }
        if (!TextUtils.isEmpty(options.titleString)) {
            toolbar.setTitle(options.titleString);
        }
        if (options.backgroundColor != 0) {
            toolbar.setBackgroundResource(options.backgroundColor);
        }
        if (options.logoId != 0) {
            toolbar.setLogo(options.logoId);
        }
        ((AppCompatActivity) mActivity).setSupportActionBar(toolbar);

        if (options.isNeedNavigate) {
            toolbar.setNavigationIcon(options.navigateId);
            toolbar.setContentInsetStartWithNavigation(0);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.finish();
                }
            });
        }
    }

    public void showToast(String s) {
        ToastUtil.showToast(getContext(), s);
    }

    public void showToast(int resId) {
        ToastUtil.showToast(getContext(), resId + "");
    }


    public abstract int setContentLayout();

    public abstract void initToolBar();

    public abstract void initPresenter();

    public abstract void initData();

    public abstract void onDestroyFragment();
}
