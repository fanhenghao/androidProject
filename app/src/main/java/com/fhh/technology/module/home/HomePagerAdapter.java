package com.fhh.technology.module.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * desc:
 * Created by fhh on 2018/9/26
 */

public class HomePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageView> mData;
    private ViewPager mViewPager;

    public HomePagerAdapter(ViewPager viewPager, Context context, List<ImageView> data) {
        this.mViewPager = viewPager;
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = mData.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);
        int currentItem = mViewPager.getCurrentItem();
        if (currentItem == mData.size() - 1) {
            mViewPager.setCurrentItem(1, false);
        } else if (currentItem == 0) {
            mViewPager.setCurrentItem(mData.size() - 2, false);
        }

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
