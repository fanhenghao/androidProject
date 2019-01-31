package com.fhh.technology.module.person;


import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseFragment;
import com.fhh.technology.module.login.LoginActivity;
import com.fhh.technology.module.person.author.AboutAuthorActivity;
import com.fhh.technology.utils.ToolBarOptions;

import butterknife.BindView;

/**
 * desc:
 * Created by fhh on 2018/9/17
 */

public class PersonFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_about_author)
    TextView mTvAboutAuthor;
    @BindView(R.id.tv_login_out)
    TextView mTvLoginOut;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_person;
    }

    @Override
    public void initToolBar() {
        mToolbarTitle.setText(getString(R.string.tab_bottom_person));
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
        mTvAboutAuthor.setOnClickListener(this);
        mTvLoginOut.setOnClickListener(this);
    }

    @Override
    public void onDestroyFragment() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_about_author:
                AboutAuthorActivity.start(getActivity());
                break;
            case R.id.tv_login_out:
                LoginActivity.start(getContext());
                if (getActivity() != null) {
                    getActivity().finish();
                }
                break;
        }
    }
}
