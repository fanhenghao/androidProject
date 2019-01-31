package com.fhh.technology.module.wait;


import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseFragment;
import com.fhh.technology.utils.ToolBarOptions;

import butterknife.BindView;

/**
 * desc:
 * Created by fhh on 2018/9/17
 */

public class WaitFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_wait;
    }

    @Override
    public void initToolBar() {
        mToolbarTitle.setText(getString(R.string.tab_bottom_wait));
        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = false;
        options.backgroundColor = R.color.colorPrimary;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroyFragment() {

    }
}
