package com.fhh.technology.module.discover.humor;

import com.fhh.technology.base.IBasePresenter;
import com.fhh.technology.base.IBaseView;
import com.fhh.technology.network.bean.HumorBean;

import java.util.List;

/**
 * Created by fanhenghao
 */
public interface HumorContract {

    interface View extends IBaseView {

        void swipeRefresh();

        void getHumorSuccess(List<HumorBean.DataBean> bean, int refreshType);

        void getHumorFail(String msg, int refreshType);

        void recyclerViewScroll();
    }

    interface Presenter extends IBasePresenter {

        /**
         * @param type        笑话类型（1=全部，2=文字，3=图片，4=视频）
         * @param page        页数
         * @param refreshType 刷新的类型（1=初始化进入刷新，2=下拉刷新，3=上拉加载更多刷新）
         */
        void humorInfo(int type, int page, int refreshType);

    }
}
