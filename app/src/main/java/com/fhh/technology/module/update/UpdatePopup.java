package com.fhh.technology.module.update;

import android.content.Context;
import android.view.View;

import com.fhh.technology.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author fanhenghao
 * @time 2019/6/24 4:11 PM
 * @class describe：
 */
public class UpdatePopup extends BasePopupWindow {

    public UpdatePopup(Context context) {
        super(context);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_update);
    }
}
