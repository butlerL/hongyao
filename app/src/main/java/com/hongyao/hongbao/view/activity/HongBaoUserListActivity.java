package com.hongyao.hongbao.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hongyao.hongbao.R;
import com.hongyao.hongbao.adapter.HongBaoUserListAdapter;
import com.hongyao.hongbao.model.bean.HongBaoUserListResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.xiaoma.common.activity.BaseActivity;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;

import java.util.HashMap;

/**
 * Created by liaolan on 16/3/25.
 */
public class HongBaoUserListActivity extends BaseActivity
        implements PullToRefreshBase.OnRefreshListener2<ListView> {
    private PullToRefreshListView ptrListView;
    private TextView tvState;
    private TextView tvMoney;
    private TextView tvTips;
    private HongBaoUserListAdapter adapter;
    private boolean isEnd;
    private String wp;
    private boolean isRefresh;
    private String hbId;
    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hong_bao_user_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("看看大家的手气");
        hbId = getQueryParameter("hbId");

        ptrListView = (PullToRefreshListView) findViewById(R.id.ptr_list_view);
        ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
        ptrListView.setOnRefreshListener(this);
        ListView listView = ptrListView.getRefreshableView();
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_hong_bao_user_list, listView, false);
        listView.addHeaderView(headerView);
        tvState = (TextView) findViewById(R.id.tv_hong_bao_current_state);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        tvTips = (TextView) findViewById(R.id.tv_tips);
        listView.setDivider(null);
        adapter = new HongBaoUserListAdapter(this);
        listView.setAdapter(adapter);
        refreshData();
    }

    private void refreshData() {
        isRefresh = true;
        HashMap<String, String> params = new HashMap<>();
        params.put("hbId", hbId);
        networkRequest.get(NetworkApi.URL_HONG_BAO_USER_LIST,
                params,
                true,
                networkCallback);
    }

    private void loadMore() {
        if (isEnd || TextUtils.isEmpty(wp)) {
            ptrListView.onRefreshComplete();
            return;
        }
        isRefresh = false;
        HashMap<String, String> params = new HashMap<>();
        params.put("hbId", hbId);
        params.put("wp", wp);
        networkRequest.get(NetworkApi.URL_HONG_BAO_USER_LIST + "more",
                params,
                true,
                networkCallback);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        refreshData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadMore();
    }

    private NetworkCallback<HongBaoUserListResult> networkCallback = new NetworkCallback<HongBaoUserListResult>() {
        @Override
        protected void onSuccess(HongBaoUserListResult response) {
            if (isRefresh) {
                adapter.clear();
                tvState.setText(response.getHbTotal());
                if (response.getHbMoney() != null) {
                    tvMoney.setText(response.getHbMoney());
                    tvTips.setText(response.getHbDesc());
                    tvMoney.setVisibility(View.VISIBLE);
                    tvTips.setVisibility(View.VISIBLE);
                    adapter.setGotten();
                } else {
                    tvMoney.setVisibility(View.GONE);
                    tvTips.setVisibility(View.GONE);
                }
            }
            isEnd = response.getHbUsers().isIsEnd();
            wp = response.getHbUsers().getWp();
            adapter.addAll(response.getHbUsers().getList());
            adapter.notifyDataSetChanged();
            ptrListView.onRefreshComplete();
            ptrListView.setMode(isEnd ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH);
        }

        @Override
        protected void onFail(int i, String s) {

        }
    };
}
