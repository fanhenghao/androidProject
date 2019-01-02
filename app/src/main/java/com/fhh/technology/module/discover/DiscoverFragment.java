package com.fhh.technology.module.discover;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseFragment;
import com.fhh.technology.base.Constant;
import com.fhh.technology.http.bean.DiscoverDataBean;
import com.fhh.technology.module.discover.annular.AnnularPercentageActivity;
import com.fhh.technology.module.discover.cardview.CardViewActivity;
import com.fhh.technology.module.discover.edit.EditTextActivity;
import com.fhh.technology.module.discover.progress.ProgressStepActivity;
import com.fhh.technology.module.discover.sqlite.SQLiteActivity;
import com.fhh.technology.utils.ToastUtil;
import com.fhh.technology.utils.ToolBarOptions;

import butterknife.BindView;

/**
 * desc:
 * Created by fhh on 2018/9/17
 */

public class DiscoverFragment extends BaseFragment implements DiscoverContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private DiscoverPresenter mPresenter;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_discover;
    }

    @Override
    public void initToolBar() {
        mToolbarTitle.setText(getString(R.string.tab_bottom_discover));
        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = false;
        options.backgroundColor = R.color.colorPrimary;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {
        if (mPresenter == null) {
            mPresenter = new DiscoverPresenter(getContext(), this);
        }
    }

    @Override
    public void initData() {
        DiscoverDataBean bean = new DiscoverDataBean();
        DiscoverAdapter adapter = new DiscoverAdapter(getContext(), bean);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
        adapter.setListener(new DiscoverAdapter.OnListener() {
            @Override
            public void onClick(int position) {
                if (position == Constant.CARD_VIEW) {
                    CardViewActivity.start(getContext());
                } else if (position == Constant.ANNULAR_PERCENTAGE) {
                    AnnularPercentageActivity.start(getActivity());
                } else if (position == Constant.EDIT_TEXT) {
                    EditTextActivity.start(getContext());
                } else if (position == Constant.SQLITE_DATA_BASE) {
                    SQLiteActivity.start(getActivity());
                } else if (position == Constant.PROGRESS_STEP) {
                    ProgressStepActivity.start(getActivity());
                } else {
                    ToastUtil.showToast(getContext(), "" + position);
                }

            }
        });
    }

    @Override
    public void onDestroyFragment() {
        mPresenter = null;
    }
}
