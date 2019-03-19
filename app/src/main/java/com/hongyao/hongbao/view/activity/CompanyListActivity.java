package com.hongyao.hongbao.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hongyao.hongbao.R;
import com.hongyao.hongbao.adapter.CompanyListAdapter;
import com.hongyao.hongbao.model.bean.CompanyListResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.xiaoma.common.activity.BaseActivity;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.util.KeyboardUtil;
import com.xiaoma.common.util.XMToast;
import com.xiaoma.common.widget.PtrRecyclerView;

import java.util.HashMap;

/**
 * Created by liaolan on 16/3/26.
 */
public class CompanyListActivity extends BaseActivity
        implements PtrRecyclerView.OnRefreshListener, CompanyListAdapter.OnSearchListener {
    private PtrRecyclerView ptrListView;
    private boolean isRefresh;
    private boolean isEnd;
    private String wp;
    private CompanyListAdapter adapter;
    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("利市号列表");
        ptrListView = (PtrRecyclerView) findViewById(R.id.ptr_list_view);
        ptrListView.setRefreshListener(this);
        adapter = new CompanyListAdapter(this, this);
        RecyclerView listView = ptrListView.getRefreshContentView();
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
        showProgress();
        refreshData();
    }

    private void refreshData() {
        isRefresh = true;
        networkRequest.get(NetworkApi.URL_COMPANY_LIST,
                null,
                true,
                networkCallback);
    }

    private void loadMore() {
        if (isEnd || TextUtils.isEmpty(wp)) {
            ptrListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrListView.refreshComplete();
                }
            }, 1000);
            return;
        }
        isRefresh = false;
        HashMap<String, String> params = new HashMap<>();
        params.put("wp", wp);
        networkRequest.get(NetworkApi.URL_COMPANY_LIST + "more",
                params,
                true,
                networkCallback);
    }

    private NetworkCallback<CompanyListResult> networkCallback = new NetworkCallback<CompanyListResult>() {
        @Override
        protected void onSuccess(CompanyListResult response) {
            wp = response.getCompanies().getWp();
            isEnd = response.getCompanies().isIsEnd();
            if (isRefresh) {
                adapter.clear();
            }
            adapter.addAll(response.getCompanies().getList());
            adapter.notifyDataSetChanged();
            ptrListView.refreshComplete();
            hideProgress();
        }

        @Override
        protected void onFail(int i, String s) {
            hideProgress();
        }
    };

    @Override
    public void onPullDown() {
        refreshData();
    }

    @Override
    public void onPullUp() {
        loadMore();
    }

    @Override
    public void onSearch(String key) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", key);
        isRefresh = true;
        showProgress();
        networkRequest.get(NetworkApi.URL_COMPANY_LIST,
                params,
                true,
                networkCallback);
    }
}
