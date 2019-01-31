package com.fhh.technology.module.home.theme;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.utils.ToolBarOptions;
import com.jaeger.library.StatusBarUtil;

import java.util.Random;

import butterknife.BindView;

/**
 * desc:theme
 * Created by fhh on 2018/9/25
 */

public class ThemeActivity extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_seek_count)
    TextView mTvSeekCount;
    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.btn_random_switcher)
    Button mBtnRandomSwitcher;
    private int mColor = R.color.theme_color;
    private int mAlpha;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ThemeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_theme;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(mColor), mAlpha);
        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = true;
        options.backgroundColor = mColor;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {
        mBtnRandomSwitcher.setOnClickListener(this);
        mTvSeekCount.setText(mAlpha + "");
        mSeekBar.setMax(Constant.STATUS_BAR_COLOR);
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setProgress(mAlpha);
    }

    @Override
    public void onDestroyActivity() {

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_random_switcher:
                Random random = new Random();
                mColor = 0xff000000 | random.nextInt(0xffffff);
                mToolbar.setBackgroundColor(mColor);
                StatusBarUtil.setColor(this, mColor, mAlpha);
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mAlpha = progress;
        mTvSeekCount.setText(mAlpha + "");
        if (mColor == R.color.theme_color) {
            StatusBarUtil.setColor(this, getResources().getColor(mColor), mAlpha);
        } else {
            StatusBarUtil.setColor(this, mColor, mAlpha);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
