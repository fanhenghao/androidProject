package com.fhh.technology.module.discover.humor;

import com.fhh.technology.base.Constant;
import com.fhh.technology.network.bean.HumorBean;
import com.fhh.technology.network.http.AppResponse;
import com.fhh.technology.network.http.AppSubscribe;
import com.fhh.technology.network.http.BaseHttp;


/**
 * Created by fanhenghao
 */
public class HumorModel implements IHumorModel {

    @Override
    public void humorInfo(int type, int page, final humInfoListener listener) {
        new AppSubscribe.Builder()
                .filtrationException()
                .setFlowable(BaseHttp.getBaseHttp().humorInfo(type, page))
                .subscribe(new AppResponse<HumorBean>() {
                    @Override
                    public void onNext(HumorBean bean) {
                        super.onNext(bean);
                        if (listener != null && bean.getCode() == Constant.HTTP_CODE) {
                            listener.responseHumorSuccess(bean.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (listener != null) {
                            listener.responseHumorFail(e.getMessage());
                        }
                    }
                }).build();
    }
}
