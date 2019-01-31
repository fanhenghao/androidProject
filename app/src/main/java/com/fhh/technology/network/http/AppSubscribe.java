package com.fhh.technology.network.http;


import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * desc:封装observable订阅处理
 * Created by fanhenghao
 */
public class AppSubscribe {

    public static class Builder {

        private Scheduler mSubscribeOn;
        private Scheduler mObserveOn;
        private Flowable mFlowable;
        private AppResponse mApiResponse;
        private boolean isFiltration = false;

        public Builder subscribeOn(Scheduler scheduler) {
            this.mSubscribeOn = scheduler;
            return this;
        }

        public Builder observeOn(Scheduler scheduler) {
            this.mObserveOn = scheduler;
            return this;
        }

        public Builder setFlowable(Flowable flowable) {
            this.mFlowable = flowable;
            return this;
        }

        public Builder subscribe(AppResponse apiResponse) {
            this.mApiResponse = apiResponse;
            return this;
        }

        //过滤掉http的code，msg，data封装（只有BaseBean不适用的时候才重写此方法，交由外面处理code状态码）
        public Builder filtrationException() {
            isFiltration = true;
            return this;
        }

        public void build() {
            if (mSubscribeOn == null) {//默认请求数据的事件处理在io线程
                mSubscribeOn = Schedulers.io();
            }
            if (mObserveOn == null) {//默认请求完成后在主线程更新UI
                mObserveOn = AndroidSchedulers.mainThread();
            }
            if (mApiResponse != null) {
                if (isFiltration) {
                    mFlowable.subscribeOn(mSubscribeOn)
                            .observeOn(mObserveOn)
                            .subscribe(mApiResponse);
                } else {
                    mFlowable.flatMap(new AppException())
                            .subscribeOn(mSubscribeOn)
                            .observeOn(mObserveOn)
                            .subscribe(mApiResponse);
                }
            }
        }
    }
}
