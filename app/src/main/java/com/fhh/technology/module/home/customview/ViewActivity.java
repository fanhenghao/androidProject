package com.fhh.technology.module.home.customview;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.utils.ToolBarOptions;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;

/**
 * desc:自定义view类
 * Created by fhh on 2018/9/21
 */

public class ViewActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.custom_draw)
    CustomDraw mCustomDraw;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ViewActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_view;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_animation), Constant.STATUS_BAR_ALPHA);
        ToolBarOptions options = new ToolBarOptions();
        options.backgroundColor = R.color.bg_animation;
        options.isNeedNavigate = true;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroyActivity() {

    }
}
