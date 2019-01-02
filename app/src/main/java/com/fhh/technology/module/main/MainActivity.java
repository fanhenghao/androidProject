package com.fhh.technology.module.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.event.DrawerLayoutEvent;
import com.fhh.technology.module.home.animation.AnimationActivity;
import com.fhh.technology.module.home.customview.ViewActivity;
import com.fhh.technology.module.home.theme.ThemeActivity;
import com.fhh.technology.module.home.headicon.HeadPortraitActivity;
import com.fhh.technology.utils.RoundHeadUtils;
import com.fhh.technology.utils.SharedPreferenceUtils;
import com.fhh.technology.utils.ToastUtil;
import com.fhh.technology.widget.BottomNavigationTab;
import com.jaeger.library.StatusBarUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final int TAB_POSITION = 0;//标记第0个位置（主页）

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation)
    NavigationView mNavigationView;
    ImageView mIvHead;


    private FragmentTabHost mTabHost;
    private View mHeaderView;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initToolBar() {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabContent);
        initNavigation();
    }

    private void initNavigation() {
        mHeaderView = mNavigationView.getHeaderView(0);
        mIvHead = mHeaderView.findViewById(R.id.iv_head);
        mTabHost.clearAllTabs();
        for (int i = 0; i < BottomNavigationTab.values().length; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getTabTitle(i)).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, getFragment(i), null);
        }
        mTabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            final int position = i;
            mTabHost.getTabWidget().getChildAt(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position != TAB_POSITION) {
                        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    } else {
                        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    }
                    mTabHost.setCurrentTab(position);
                }
            });
        }
    }

    private Class<?> getFragment(int position) {
        BottomNavigationTab tab = BottomNavigationTab.fromTabIndex(position);
        return tab.clazz;
    }

    private View getTabItemView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab_bottom, null);
        ImageView imageView = view.findViewById(R.id.image_view);
        TextView tvView = view.findViewById(R.id.text_view);
        imageView.setBackgroundResource(getIconRes(position));
        tvView.setText(getTabTitle(position));
        return view;
    }

    private int getIconRes(int position) {
        BottomNavigationTab tab = BottomNavigationTab.fromTabIndex(position);
        return tab != null ? tab.iconId : 0;
    }

    private String getTabTitle(int position) {
        BottomNavigationTab navigationTab = BottomNavigationTab.fromTabIndex(position);
        int titleId = navigationTab != null ? navigationTab.resId : 0;
        return titleId != 0 ? getString(titleId) : "";
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String path = SharedPreferenceUtils.getInstance(this).getString(Constant.PICTURE_URL_KEY);
        Picasso.with(this).load(new File(path)).transform(new RoundHeadUtils()).error(R.mipmap.ic_head_icon).into(mIvHead);
    }

    @Override
    public void initData() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        StatusBarUtil.setColorForDrawerLayout(this, mDrawerLayout, getResources().getColor(R.color.colorPrimaryDark), Constant.STATUS_BAR_ALPHA);
        mNavigationView.setNavigationItemSelectedListener(this);
        mHeaderView.findViewById(R.id.iv_head).setOnClickListener(this);
        String path = SharedPreferenceUtils.getInstance(this).getString(Constant.PICTURE_URL_KEY);
        Picasso.with(this).load(new File(path)).transform(new RoundHeadUtils()).error(R.mipmap.ic_head_icon).into(mIvHead);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MainEvent(DrawerLayoutEvent event) {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onDestroyActivity() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_animation:
                AnimationActivity.start(this);
                break;
            case R.id.nav_custom_view:
                ViewActivity.start(this);
                break;
            case R.id.nav_game:
                ToastUtil.showToast(this, "nav_game");
                break;
            case R.id.nav_theme:
                ThemeActivity.start(this);
                break;
            case R.id.nav_scan:
                ToastUtil.showToast(this, "nav_scan");
                break;
        }
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head:
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                HeadPortraitActivity.start(mActivity);
                break;
            case R.id.tv_user_info_desc:
                break;
        }
    }
}
