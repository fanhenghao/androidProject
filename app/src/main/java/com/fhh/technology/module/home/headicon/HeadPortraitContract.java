package com.fhh.technology.module.home.headicon;

import com.fhh.technology.base.IBasePresenter;
import com.fhh.technology.base.IBaseView;

/**
 * Created by fanhenghao
 */
public interface HeadPortraitContract {

    interface View extends IBaseView {

        void loadPath(String path);

    }

    interface Presenter extends IBasePresenter {

    }
}
