package com.fhh.technology.module.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseFragment;
import com.fhh.technology.base.Constant;
import com.fhh.technology.event.DrawerLayoutEvent;
import com.fhh.technology.utils.ToastUtil;
import com.fhh.technology.utils.ToolBarOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * desc:主页
 * Created by fhh on 2018/9/17
 */

public class HomeFragment extends BaseFragment implements HomeContract.View, View.OnTouchListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ib_status)
    ImageButton mIbStatus;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private int[] images = {R.drawable.ic_home_carouse_4, R.drawable.ic_home_carouse_1, R.drawable.ic_home_carouse_2,
            R.drawable.ic_home_carouse_3, R.drawable.ic_home_carouse_4, R.drawable.ic_home_carouse_1};
    private List<ImageView> imageList = new ArrayList<>();
    private HomePresenter mPresenter;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler();
    private int mCurrent;
    private float mDowm_x;
    private float mMove_x;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initToolBar() {
        mToolbarTitle.setText(getString(R.string.tab_bottom_home));
        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = false;
        options.backgroundColor = R.color.colorPrimary;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {
        if (mPresenter == null) {
            mPresenter = new HomePresenter(getActivity(), this);
        }
    }

    @Override
    public void initData() {
        mIbStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DrawerLayoutEvent());
            }
        });
        initViewPager();

    }

    private void initViewPager() {
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(images[i]);
            imageList.add(imageView);
        }
        HomePagerAdapter adapter = new HomePagerAdapter(getContext(), imageList);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imageList.size() - 1) {
                    mCurrent = 1;
                } else if (position == 0) {
                    mCurrent = imageList.size() - 2;
                } else {
                    mCurrent = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOnTouchListener(this);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
//            mCurrent = mViewPager.getCurrentItem() % 4;
//            if (mCurrent >= 3) {
//                mViewPager.setCurrentItem(0, false);
//                mHandler.postDelayed(mRunnable, 2000);
//            } else {
            if (mCurrent == imageList.size()) {
                mViewPager.setCurrentItem(mCurrent, false);
                mHandler.post(mRunnable);
            } else {
                mViewPager.setCurrentItem(mCurrent);
                mHandler.postDelayed(mRunnable, 2000);
            }
            if (mCurrent < imageList.size() - 1) {
                mCurrent++;
            }
//            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        startCarousel();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopCarousel();

    }

    @Override
    public void onDestroyFragment() {
        mPresenter = null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDowm_x = event.getX();
                stopCarousel();
                break;
            case MotionEvent.ACTION_MOVE:
                mMove_x = event.getX();
                stopCarousel();
                break;
            case MotionEvent.ACTION_UP:
                startCarousel();
                if (Math.abs(mDowm_x - mMove_x) < Constant.ACTION_TOUCH_DISTANCE) {
                    ToastUtil.showToast(getContext(), "点击了：" + mViewPager.getCurrentItem());
                }
                break;
        }
        return false;
    }

    @Override
    public void startCarousel() {
        mHandler.post(mRunnable);
    }

    @Override
    public void stopCarousel() {
        mHandler.removeCallbacks(mRunnable);
    }
}
