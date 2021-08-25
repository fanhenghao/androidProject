package com.fhh.technology.base;


/**
 * desc:常量配置的类
 * Created by fhh on 2018/9/16
 */

public interface Constant {

    //app theme alpha
    int STATUS_BAR_ALPHA = 50;
    int STATUS_BAR_COLOR = 255;

    //animation skip
    int ANIMATION_BUTTON_STYLE = 0;
    int ANIMATION_CUSTOM_STYLE = 1;
    int ANIMATION_OTHER_STYLE = 2;

    float ACTION_TOUCH_DISTANCE = 30.0f;//30px

    //discover
    int CARD_VIEW = 0;//cardView
    int ANNULAR_PERCENTAGE = 1;//annular_percentage
    int EDIT_TEXT = 2;//EditText
    int SQLITE_DATA_BASE = 3;//SQLite
    int PROGRESS_STEP = 4;//进度步骤
    int HUMOROUS_JOKES = 5;//Humorous jokes
    int WEATHER = 6;//weather
    int VIEWPAGER = 7;//viewpager

    //SQLite type
    int ADD = 1;
    int DELETE = 2;
    int UPDATE = 3;
    int QUERY = 4;
    String SQLITE_CREATE = "TECHNOLOGY_STORE.db";
    String SQLITE_CHART = "technology";//数据库表

    //sharedPreference
    String SHAREPRE_NAME = "heng";//存储文件的名称
    String PICTURE_URL_KEY = "picture_url_key";

    //http
    String HTTP_BASE = "https://www.apiopen.top/";
    String HTTP_KEY = "00d91e8e0cca2b76f515926a36db68f5";//服务器需要的key
    int HTTP_CODE = 200;

    //幽默笑话段子类型
    int SATIN_TYPE_ALL = 1;
    int SATIN_TYPE_TEXT = 2;
    int SATIN_TYPE_PICTURE = 3;
    int SATIN_TYPE_VIDEO = 4;
    //请求幽默笑话接口刷新类型
    int REFRESH_NORMAL = 1;//普通刷新
    int REFRESH_PULL_DOWN = 2;//下拉刷新
    int REFRESH_UP_LOAD_MORE = 3;//加载更多

    String KEY_LOGIN_ACCOUNT = "login_account";


}
