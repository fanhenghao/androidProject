package com.fhh.technology.network.http;

import com.fhh.technology.network.bean.BaseBean;
import com.fhh.technology.network.bean.HumorBean;
import com.fhh.technology.network.bean.LoginBean;
import com.fhh.technology.network.bean.RegisterBean;
import com.fhh.technology.network.bean.WeatherBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * desc:接口请求类
 * Created by fanhenghao
 */

public interface NetService {

    /**
     * 登陆
     *
     * @param key   三方默认的key
     * @param phone 登录的手机号
     * @param pwd   登录密码
     */
    @FormUrlEncoded
    @POST("login")
    Flowable<BaseBean<LoginBean>> loginInfo(@Field("key") String key, @Field("phone") String phone, @Field("passwd") String pwd);

    /**
     * 应用注册
     *
     * @param key   三方默认的key
     * @param phone 注册手机号
     * @param pwd   注册密码
     */
    @GET("createUser")
    Flowable<BaseBean<RegisterBean>> registerInfo(@Query("key") String key, @Query("phone") String phone, @Query("passwd") String pwd);

    /**
     * 请求笑话接口
     *
     * @param type 笑话类型（1=全部，2=文字，3=图片，4=视频）
     * @param page 页数
     */
    @FormUrlEncoded
    @POST("satinApi")
    Flowable<HumorBean> humorInfo(@Field("type") int type, @Field("page") int page);

    /**
     * 请求天气接口
     *
     * @param cityName 城市名称
     */
    @GET("weatherApi")
    Flowable<BaseBean<WeatherBean>> cityWeather(@Query("city") String cityName);

}
