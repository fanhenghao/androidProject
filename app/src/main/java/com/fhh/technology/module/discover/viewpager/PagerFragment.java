package com.fhh.technology.module.discover.viewpager;

import android.os.Bundle;
import android.widget.ImageView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseFragment;

import butterknife.BindView;

/**
 * @author fhh
 * @time 2021/8/25 2:09 下午
 * @class describe：
 */
public class PagerFragment extends BaseFragment {
    private static final String KEY_POSITION = "position";

    @BindView(R.id.iv)
    ImageView mIv;

    public static PagerFragment newInstance(int position) {
        PagerFragment fragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int setContentLayout() {
        return R.layout.fragment_pager;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {
        int position = getArguments().getInt(KEY_POSITION);
        if (position == 0) {
            mIv.setImageResource(R.mipmap.ic_page1);
        } else if (position == 1) {
            mIv.setImageResource(R.mipmap.ic_page2);
        } else {
            mIv.setImageResource(R.mipmap.ic_page3);
        }
    }

    @Override
    public void onDestroyFragment() {

    }
}
