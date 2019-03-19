package com.hongyao.hongbao.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hongyao.hongbao.R;
import com.hongyao.hongbao.adapter.YuEBillAdapter;
import com.hongyao.hongbao.model.bean.WoDeYuEResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.xiaoma.common.activity.BaseActivity;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.route.UriDispatcher;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wjf on 2016-03-29.
 */
public class WoDeYuEActivity extends BaseActivity implements View.OnClickListener,
        PullToRefreshBase.OnRefreshListener2 {
    private final String TAG = "WoDeYuEActivity";

    private TextView tvYuECount = null;
    private TextView tvYuEHongBao = null;
    private RecyclerView rvBill = null;

    private PullToRefreshScrollView pullToRefreshScrollView = null;

    private boolean isEnd = false;
    private String wp = null;

    private ArrayList<WoDeYuEResult.BillContent> bills = new ArrayList<>();
    private YuEBillAdapter yueBillAdapter = null;
    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setTitle("我的余额");

        tvYuECount = (TextView) findViewById(R.id.tv_wode_yue_count);
        tvYuEHongBao = (TextView) findViewById(R.id.tv_wode_yue_hongbao);
        rvBill = (RecyclerView) findViewById(R.id.rv_wode_yue);

        tvYuEHongBao.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvBill.setLayoutManager(manager);

        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.wode_yue_refresh);
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshScrollView.setOnRefreshListener(this);

        loadData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wo_de_yu_e;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wode_yue_hongbao:
                UriDispatcher.getInstance().dispatch(this, "xiaoma://myHongBao");
                break;
            default:
                break;
        }
    }

    private void loadData() {
        bills.clear();
        networkRequest.get(NetworkApi.URL_WO_DE_YU_E,
                null,
                true,
                networkCallback
        );
    }

    private void loadMoreData() {
        if (!isEnd) {
            HashMap<String, String> params = new HashMap<>();
            params.put("wp", wp);
            networkRequest.get(NetworkApi.URL_WO_DE_BILL_MORE,
                    params,
                    true,
                    networkCallback
            );
        } else {
            pullToRefreshScrollView.onRefreshComplete();
            Toast toast = Toast.makeText(this, "已全部加载", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        loadData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        loadMoreData();
    }

    private NetworkCallback<WoDeYuEResult> networkCallback = new NetworkCallback<WoDeYuEResult>() {
        @Override
        protected void onSuccess(WoDeYuEResult response) {
            pullToRefreshScrollView.onRefreshComplete();
            String balance = response.getBalance();
            if (null != balance) {
                tvYuECount.setText(balance);
            }

            isEnd = response.getBills().isEnd();
            wp = response.getBills().getWp();

            bills.addAll(response.getBills().getList());
            if (bills.size() == 0) {
                return;
            }
            if (null == yueBillAdapter) {
                yueBillAdapter = new YuEBillAdapter(bills);
                rvBill.setAdapter(yueBillAdapter);
            }
            yueBillAdapter.refreshData(bills);
        }

        @Override
        protected void onFail(int i, String s) {
            pullToRefreshScrollView.onRefreshComplete();
        }
    };
}
