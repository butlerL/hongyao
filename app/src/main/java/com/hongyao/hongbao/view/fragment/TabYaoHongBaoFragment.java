package com.hongyao.hongbao.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hongyao.hongbao.R;
import com.hongyao.hongbao.adapter.YaoHongBaoAdapter;
import com.hongyao.hongbao.eventBus.YaoHongBaoEvent;
import com.hongyao.hongbao.model.bean.DayGetKeyResult;
import com.hongyao.hongbao.model.bean.HongBaoFavorBean;
import com.hongyao.hongbao.model.bean.TimeLineResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.xiaoma.common.eventBus.LoginEvent;
import com.xiaoma.common.eventBus.LogoutEvent;
import com.xiaoma.common.fragment.BaseFragment;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.util.XMToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liaolan on 16/3/23.
 */
public class TabYaoHongBaoFragment extends BaseFragment
        implements PullToRefreshBase.OnRefreshListener2<ListView>,
        YaoHongBaoAdapter.OnFavorListener {

    private PullToRefreshListView pullToRefreshListView;
    private YaoHongBaoAdapter adapter;
    private TimeLineResult response;
    private NetworkRequest networkRequest = new NetworkRequest();
    private boolean isRefresh;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yao_hong_bao;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView(View rootView) {
        rootView.findViewById(R.id.iv_back).setVisibility(View.GONE);
        ((TextView) rootView.findViewById(R.id.title)).setText("摇红包");
        pullToRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_to_refresh);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(this);
        adapter = new YaoHongBaoAdapter(getContext(), this);
        ListView listView = pullToRefreshListView.getRefreshableView();
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        listView.setDivider(null);
        listView.setVerticalScrollBarEnabled(false);
        listView.setAdapter(adapter);

        networkRequest.get(NetworkApi.URL_ME_DAYGETKEY,
                null,
                false,
                new NetworkCallback<DayGetKeyResult>() {

                    @Override
                    protected void onSuccess(DayGetKeyResult dayGetKeyResult) {
                        XMToast.makeText(dayGetKeyResult.getTip(), XMToast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onFail(int i, String s) {

                    }
                });
        showProgress();
        refreshData();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        refreshData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (response != null && !response.getTimeline().isIsEnd()) {
            loadMore();
        } else {
            pullToRefreshListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pullToRefreshListView.onRefreshComplete();
                }
            }, 1000);

        }
    }

    private void refreshData() {
        isRefresh = true;
        networkRequest.get(NetworkApi.URL_TIME_LINE, null, false, networkCallback);
    }

    private void loadMore() {
        if (response != null
                && !response.getTimeline().isIsEnd()
                && !TextUtils.isEmpty(response.getTimeline().getWp())) {
            isRefresh = false;
            HashMap<String, String> params = new HashMap<>();
            params.put("wp", response.getTimeline().getWp());
            networkRequest.get(NetworkApi.URL_TIME_LINE_MORE, params, false, networkCallback);
        }
    }

    @Subscribe
    public void onEvent(YaoHongBaoEvent event) {
        if (adapter == null || pullToRefreshListView == null) {
            return;
        }
        List<TimeLineResult.TimelineBean.ListBean> datas = adapter.getDatas();
        if (datas != null && !datas.isEmpty()) {
            for (TimeLineResult.TimelineBean.ListBean item : datas) {
                String hbId = item.getId();
                if (event.hbId.equals(hbId)) {
                    int total = item.getHongbao().getTotal() + 1;
                    item.getHongbao().setTotal(total);
                    item.getHongbao().setStatus(event.status);
                    break;
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private NetworkCallback<TimeLineResult> networkCallback = new NetworkCallback<TimeLineResult>() {
        @Override
        protected void onSuccess(TimeLineResult response) {
            TabYaoHongBaoFragment.this.response = response;
            if (isRefresh) {
                adapter.clear();
            }
            adapter.addAll(response.getTimeline().getList());
            adapter.notifyDataSetChanged();
            pullToRefreshListView.onRefreshComplete();
            hideProgress();
        }

        @Override
        protected void onFail(int i, String s) {
            hideProgress();
        }
    };

    @Override
    public void onFavor(final String hbId) {
        Map<String, String> params = new HashMap<>();
        params.put("hbId", hbId);
        showProgress();
        new NetworkRequest().get(NetworkApi.URL_HONGBAO_FAVOR,
                params,
                true,
                new NetworkCallback<HongBaoFavorBean>() {

                    @Override
                    protected void onSuccess(HongBaoFavorBean hongBaoFavorBean) {
                        if (!TextUtils.isEmpty(hongBaoFavorBean.getMsg())) {
                            XMToast.makeText(hongBaoFavorBean.getMsg(), XMToast.LENGTH_LONG).show();
                        }

                        if (adapter == null || pullToRefreshListView == null) {
                            hideProgress();
                            return;
                        }
                        List<TimeLineResult.TimelineBean.ListBean> datas = adapter.getDatas();
                        if (datas != null && !datas.isEmpty()) {
                            for (TimeLineResult.TimelineBean.ListBean item : datas) {
                                if (hbId.equals(item.getId())) {
                                    item.setFavored(true);
                                    item.setFavorCount(hongBaoFavorBean.getFavorCount());
                                    break;
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                        hideProgress();
                    }

                    @Override
                    protected void onFail(int i, String s) {
                        hideProgress();
                    }
                });
    }

    @Subscribe
    public void onEvent(LoginEvent event) {
        showProgress();
        refreshData();
    }

    @Subscribe
    public void onEvent(LogoutEvent event) {
        showProgress();
        refreshData();
    }
}
