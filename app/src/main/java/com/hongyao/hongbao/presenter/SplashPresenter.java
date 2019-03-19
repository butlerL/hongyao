package com.hongyao.hongbao.presenter;

import com.hongyao.hongbao.iview.ISplashView;
import com.hongyao.hongbao.model.bean.SplashPageBean;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.presenter.BasePresenter;

/**
 * Created by wjf on 16/8/11.
 */
public class SplashPresenter {
    protected final NetworkRequest networkRequest = new NetworkRequest();
    private ISplashView splashView;

    public SplashPresenter(ISplashView splashView) {
        this.splashView = splashView;
    }

    public void loadData() {
        networkRequest.get("http://app2.hoyaoo.com/guidepage",
                null,
                false,
                new NetworkCallback<SplashPageBean>() {
                    @Override
                    public void onSuccess(SplashPageBean response) {
                        splashView.onLoadSuccess(response, true);
                    }

                    @Override
                    public void onFail(int code, String message) {
                        splashView.onError(code, message);
                    }
                });
    }
}
