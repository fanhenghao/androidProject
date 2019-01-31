package com.fhh.technology.module.discover.weather;

import com.fhh.technology.network.bean.WeatherBean;


/**
 * Created by fanhenghao
 */
public interface IWeatherModel {

    void cityWeather(String cityName, IWeatherModel.cityWeatherListener listener);

    interface cityWeatherListener {

        void responseWeatherSuccess(WeatherBean bean);

        void responseWeatherFail(String msg);
    }

}
