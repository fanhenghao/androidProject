package com.fhh.technology.module.discover.cardview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.utils.ToolBarOptions;

import butterknife.BindView;

/**
 * desc:
 * Created by fhh on 2018/10/17
 */

public class CardViewActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.card_view)
    CardView mCardView;
    @BindView(R.id.seek_bar_1)
    SeekBar mSeekBar1;
    @BindView(R.id.seek_bar_2)
    SeekBar mSeekBar2;
    @BindView(R.id.seek_bar_3)
    SeekBar mSeekBar3;

    public static void start(Context context) {
        Intent intent = new Intent(context, CardViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_card_view;
    }

    @Override
    public void initToolBar() {
        mToolbarTitle.setText(R.string.text_card_view);
        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = true;
        options.backgroundColor = R.color.colorPrimary;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {
        mSeekBar1.setOnSeekBarChangeListener(new MySeekBarListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                super.onProgressChanged(seekBar, progress, fromUser);
                mCardView.setRadius(progress);
            }
        });
        mSeekBar2.setOnSeekBarChangeListener(new MySeekBarListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                super.onProgressChanged(seekBar, progress, fromUser);
                mCardView.setCardElevation(progress);
            }
        });
        mSeekBar3.setOnSeekBarChangeListener(new MySeekBarListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                super.onProgressChanged(seekBar, progress, fromUser);
                mCardView.setContentPadding(progress, progress, progress, progress);
            }
        });
    }

    @Override
    public void onDestroyActivity() {

    }

    private class MySeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
