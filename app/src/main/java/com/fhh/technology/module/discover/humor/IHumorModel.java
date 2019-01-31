package com.fhh.technology.module.discover.humor;

import com.fhh.technology.network.bean.HumorBean;

import java.util.List;

/**
 * Created by fanhenghao
 */
public interface IHumorModel {

    void humorInfo(int type,int page,IHumorModel.humInfoListener listener);

    interface humInfoListener{

        void responseHumorSuccess(List<HumorBean.DataBean> bean);

        void responseHumorFail(String msg);
    }
}
