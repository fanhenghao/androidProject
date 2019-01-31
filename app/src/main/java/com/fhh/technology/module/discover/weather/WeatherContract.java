package com.fhh.technology.module.discover.weather;

import com.fhh.technology.base.IBasePresenter;
import com.fhh.technology.base.IBaseView;
import com.fhh.technology.network.bean.WeatherBean;

/**
 * Created by fanhenghao
 */
public interface WeatherContract {
    interface View extends IBaseView {
        void getCityWeatherSuccess(WeatherBean bean);

        void getCityWeatherFail(String error);
    }

    interface Presenter extends IBasePresenter {
        void cityName(String cityName);
    }
}
