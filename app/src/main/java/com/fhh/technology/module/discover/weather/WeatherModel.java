package com.fhh.technology.module.discover.weather;

import com.fhh.technology.network.bean.WeatherBean;
import com.fhh.technology.network.http.AppResponse;
import com.fhh.technology.network.http.AppSubscribe;
import com.fhh.technology.network.http.BaseHttp;

/**
 * Created by fanhenghao
 */
public class WeatherModel implements IWeatherModel {
    @Override
    public void cityWeather(String cityName, cityWeatherListener listener) {
        new AppSubscribe.Builder()
                .setFlowable(BaseHttp.getBaseHttp().cityWeather(cityName))
                .subscribe(new AppResponse<WeatherBean>() {
                    @Override
                    public void onNext(WeatherBean weatherBean) {
                        super.onNext(weatherBean);
                        if (listener != null) {
                            listener.responseWeatherSuccess(weatherBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (listener != null) {
                            listener.responseWeatherFail(e.getMessage());
                        }
                    }
                }).build();
    }
}
