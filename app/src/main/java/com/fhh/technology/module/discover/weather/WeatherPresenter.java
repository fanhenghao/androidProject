package com.fhh.technology.module.discover.weather;

import android.app.Activity;

import com.fhh.technology.network.bean.WeatherBean;

/**
 * Created by fanhenghao
 */
public class WeatherPresenter implements WeatherContract.Presenter {

    private Activity mActivity;
    private WeatherContract.View mView;
    private final WeatherModel mModel;

    WeatherPresenter(Activity activity, WeatherContract.View view) {
        this.mActivity = activity;
        this.mView = view;
        mModel = new WeatherModel();
    }

    @Override
    public void cityName(String cityName) {
        mModel.cityWeather(cityName, new IWeatherModel.cityWeatherListener() {
            @Override
            public void responseWeatherSuccess(WeatherBean bean) {
                mView.getCityWeatherSuccess(bean);
            }

            @Override
            public void responseWeatherFail(String msg) {
                mView.getCityWeatherFail(msg);
            }
        });
    }
}
