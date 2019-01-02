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

}
