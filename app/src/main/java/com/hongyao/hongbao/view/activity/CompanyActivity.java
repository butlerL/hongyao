package com.hongyao.hongbao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.adapter.CompanyContentAdapter;
import com.hongyao.hongbao.model.bean.CompanyContentResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaoma.common.activity.BaseActivity;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.route.UriDispatcher;

import org.apache.http.NameValuePair;

import java.util.HashMap;

/**
 * Created by liaolan on 16/3/26.
 */
public class CompanyActivity extends BaseActivity
        implements PullToRefreshBase.OnRefreshListener2<ListView> {
    private PullToRefreshListView ptrListView;
    private boolean isRefresh;
    private boolean isEnd;
    private String wp;
    private CompanyContentAdapter adapter;
    private String companyId;
    private String hbLink;
    private String payLink;
    private String shopLink;
    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("利市号内容列表");
        titleBar.setRightImage(R.drawable.ic_user);
        titleBar.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyActivity.this, CompanyInfoActivity.class);
                intent.putExtra(CompanyInfoActivity.COMPANY_ID, companyId);
                startActivity(intent);
            }
        });

        companyId = getQueryParameter("companyId");
        ptrListView = (PullToRefreshListView) findViewById(R.id.ptr_list_view);
        ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
        ptrListView.setOnRefreshListener(this);
        adapter = new CompanyContentAdapter(this);
        ListView listView = ptrListView.getRefreshableView();
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        listView.setDivider(null);
        listView.setAdapter(adapter);
        refreshData();
    }

    private void refreshData() {
        isRefresh = true;
        HashMap<String, String> params = new HashMap<>();
        params.put("companyId", companyId);
        networkRequest.get(NetworkApi.URL_COMPANY_CONTENT_LIST,
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
        params.put("companyId", companyId);
        params.put("companyId", companyId);
        networkRequest.get(NetworkApi.URL_COMPANY_CONTENT_LIST + "/more",
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

    public void goHb(View view) {
        if (!TextUtils.isEmpty(hbLink)) {
            UriDispatcher.getInstance().dispatch(this, hbLink);
        }
    }

    public void goPay(View view) {
        if (!TextUtils.isEmpty(payLink)) {
            UriDispatcher.getInstance().dispatch(this, payLink);
        }
    }

    public void goShop(View view) {
        if (!TextUtils.isEmpty(shopLink)) {
            UriDispatcher.getInstance().dispatch(this, shopLink);
        }
    }

    private NetworkCallback<CompanyContentResult> networkCallback = new NetworkCallback<CompanyContentResult>() {
        @Override
        protected void onSuccess(CompanyContentResult response) {
            wp = response.getContents().getWp();
            isEnd = response.getContents().isIsEnd();
            if (isRefresh) {
                adapter.clear();
            }
            adapter.addAll(response.getContents().getList());
            adapter.notifyDataSetChanged();
            ptrListView.onRefreshComplete();
            ptrListView.setMode(isEnd ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.BOTH);
            hbLink = response.getHbLink();
            payLink = response.getPayLink();
            shopLink = response.getShopLink();
        }

        @Override
        protected void onFail(int i, String s) {
            ptrListView.onRefreshComplete();
        }
    };
}
