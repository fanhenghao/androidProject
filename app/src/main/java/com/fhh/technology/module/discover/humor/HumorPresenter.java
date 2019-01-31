package com.fhh.technology.module.discover.humor;

import android.app.Activity;

import com.fhh.technology.network.bean.HumorBean;

import java.util.List;

/**
 * Created by fanhenghao
 */
public class HumorPresenter implements HumorContract.Presenter {

    private Activity mActivity;
    private HumorContract.View mView;
    private final IHumorModel mModel;

    public HumorPresenter(Activity activity, HumorContract.View view) {
        this.mActivity = activity;
        this.mView = view;
        mModel = new HumorModel();
    }

    @Override
    public void humorInfo(int type, int page, final int refreshType) {

        mModel.humorInfo(type, page, new IHumorModel.humInfoListener() {
            @Override
            public void responseHumorSuccess(List<HumorBean.DataBean> bean) {
                mView.getHumorSuccess(bean, refreshType);
            }

            @Override
            public void responseHumorFail(String msg) {
                mView.getHumorFail(msg, refreshType);
            }
        });
    }
}
