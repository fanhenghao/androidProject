package com.fhh.technology.module.discover.weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.network.bean.WeatherBean;
import com.fhh.technology.utils.StringUtils;
import com.fhh.technology.utils.ToolBarOptions;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

/**
 * Created by fanhenghao
 */
public class WeatherActivity extends BaseActivity implements WeatherContract.View, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.et_input_city)
    EditText mEtInputCity;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_wind)
    TextView mTvWind;
    @BindView(R.id.tv_temperature)
    TextView mTvTemperature;
    @BindView(R.id.tv_weather)
    TextView mTvWeather;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    private WeatherPresenter mPresenter;


    public static void start(Context context) {
        Intent intent = new Intent(context, WeatherActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_weather;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_find_title), Constant.STATUS_BAR_ALPHA);
        mToolbarTitle.setText(getString(R.string.weather_title));
        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = true;
        options.backgroundColor = R.color.bg_find_title;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {
        mPresenter = new WeatherPresenter(this, this);
    }

    @Override
    public void initData() {
        mTvSearch.setOnClickListener(this);
    }

    @Override
    public void onDestroyActivity() {
        mPresenter = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                String cityName = mEtInputCity.getText().toString().trim();
                if (TextUtils.isEmpty(cityName)) {
                    showToast(getString(R.string.weather_city_empty_remind));
                    return;
                }
                if (mPresenter != null) {
                    mPresenter.cityName(cityName);
                }
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getCityWeatherSuccess(WeatherBean bean) {
        if (bean != null && bean.getForecast() != null) {
            List<WeatherBean.ForecastBean> forecast = bean.getForecast();
            mTvTime.setText(forecast.get(0).getDate());
            mTvWind.setText(forecast.get(0).getFengxiang() + " " + forecast.get(0).getFengli());
            mTvTemperature.setText(StringUtils.splitString(forecast.get(0).getLow()) + " ～" + StringUtils.splitString(forecast.get(0).getHigh()));
            mTvWeather.setText(forecast.get(0).getType());
            mTvDesc.setText(bean.getGanmao());
        }
    }

    @Override
    public void getCityWeatherFail(String error) {
        showToast(error);
    }

}
