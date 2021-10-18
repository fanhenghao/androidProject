package com.fhh.technology.module.discover.humor;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.network.bean.HumorBean;
import com.fhh.technology.utils.ToolBarOptions;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

/**
 * Created by fanhenghao
 */
public class HumorActivity extends BaseActivity implements HumorContract.View {

    private static final int NORMAL_TYPE = 1;
    private static final int LOAD_MORE_TYPE = 2;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.humor_recycler_view)
    RecyclerView mHumorRecyclerView;

    private HumorPresenter mPresenter;
    private HumorAdapter mAdapter;
    private int mPage = 1;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, HumorActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_humor;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_find_title), Constant.STATUS_BAR_ALPHA);
        mToolbarTitle.setText(getString(R.string.humor_title));
        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = true;
        options.backgroundColor = R.color.bg_find_title;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {
        mPresenter = new HumorPresenter(this, this);
    }

    @Override
    public void initData() {
        swipeRefresh();
        recyclerViewScroll();
        mPresenter.humorInfo(Constant.SATIN_TYPE_TEXT, 1, Constant.REFRESH_NORMAL);
    }

    @Override
    public void swipeRefresh() {
        //设置下拉进度的背景色
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置下拉刷新的主题颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_dark, android.R.color.holo_orange_dark);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.humorInfo(Constant.SATIN_TYPE_TEXT, 0, Constant.REFRESH_PULL_DOWN));
    }

    @Override
    public void recyclerViewScroll() {
        mHumorRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {//处于静止状态
                    if (mHumorRecyclerView != null && mHumorRecyclerView.computeVerticalScrollOffset() > 0) {//有滚动距离，之后才可显示加载更多
//                        Log.e("RecyclerView", "computeVerticalScrollExtent---" + mRecyclerView.computeVerticalScrollExtent());//当前屏幕显示的区域高度
//                        Log.e("RecyclerView", "computeVerticalScrollOffset---" + mRecyclerView.computeVerticalScrollOffset());//当前屏幕之前滑过的距离
//                        Log.e("RecyclerView", "computeVerticalScrollRange---" + mRecyclerView.computeVerticalScrollRange());//整个View控件的高度
                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        boolean canScroll = recyclerView.canScrollVertically(1);//1代表能否向上滑动，-1代表能否向下滑动
                        if (!canScroll && mAdapter != null) {
                            mPresenter.humorInfo(Constant.SATIN_TYPE_TEXT, mPage, Constant.REFRESH_UP_LOAD_MORE);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void getHumorSuccess(List<HumorBean.DataBean> bean, int refreshType) {

        if (mAdapter == null) {
            mHumorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new HumorAdapter(this, bean);
            mHumorRecyclerView.setAdapter(mAdapter);
        }
        if (refreshType == Constant.REFRESH_PULL_DOWN) {
            mSwipeRefreshLayout.setRefreshing(false);
            mAdapter.setPullDown(NORMAL_TYPE, bean);
            mPage = 1;
        } else if (refreshType == Constant.REFRESH_UP_LOAD_MORE) {
            mAdapter.setLoadMore(LOAD_MORE_TYPE, bean);
            mPage++;
        } else {
            mPage++;
        }

        if (mAdapter != null) {
            mAdapter.setListener((position, reLoad) -> {
                if (reLoad) {
                    mAdapter.startLoad();
                    mPresenter.humorInfo(Constant.SATIN_TYPE_TEXT, mPage, Constant.REFRESH_UP_LOAD_MORE);
                } else {
                    showToast(position + "");
                }
            });
        }
    }

    @Override
    public void getHumorFail(String msg, int refreshType) {
        if (refreshType == Constant.REFRESH_PULL_DOWN) {
            mSwipeRefreshLayout.setRefreshing(false);
        } else if (refreshType == Constant.REFRESH_UP_LOAD_MORE) {
            if (mAdapter != null) {
                mAdapter.loadMoreError(refreshType, true);
            }
        }
        showToast(msg);
    }

    @Override
    public void onDestroyActivity() {
        mPresenter = null;
    }
}
