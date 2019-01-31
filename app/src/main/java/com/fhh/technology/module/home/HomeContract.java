package com.fhh.technology.module.home;

import com.fhh.technology.base.IBasePresenter;
import com.fhh.technology.base.IBaseView;

/**
 * desc:
 * Created by fhh on 2018/9/18
 */

public interface HomeContract {

    interface View extends IBaseView {

        void startCarousel();

        void stopCarousel();
    }

    interface Presenter extends IBasePresenter {

    }
}
