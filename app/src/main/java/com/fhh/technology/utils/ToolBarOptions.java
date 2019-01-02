package com.fhh.technology.utils;

import com.fhh.technology.R;

/**
 * desc:
 * Created by fhh on 2018/9/16
 */

public class ToolBarOptions {
    /**
     * toolbar的title资源id
     */
    public int titleId;
    /**
     * toolbar的title
     */
    public String titleString;
    /**
     * toolbar的logo资源id
     */
    public int logoId;
    /**
     * toolbar的返回按钮资源id
     */
    public int navigateId = R.mipmap.ic_toolbar_arrow;
    /**
     * 背景color
     */
    public int backgroundColor;
    /**
     * toolbar的返回按钮
     */
    public boolean isNeedNavigate;
}
