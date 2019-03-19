package com.hongyao.hongbao.view.fragment;

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
import com.hongyao.hongbao.adapter.HongBaoGiveAdapter;
import com.hongyao.hongbao.model.bean.WoDeHongBaoResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.hongyao.hongbao.view.activity.WoDeHongBaoActivity;
import com.xiaoma.common.fragment.BaseFragment;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.login.UserConfig;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wjf on 2016-03-25.
 */
public class HongBaoGiveFragment extends BaseFragment implements View.OnClickListener,
        WoDeHongBaoActivity.NotifyHongBaoGive, PullToRefreshBase.OnRefreshListener2 {
    private final String TAG = "HongBaoGiveFragment";

    private TextView tvHongBaoCount = null;
    private TextView tvHongBaototal = null;
    private TextView tvHongBaoGive = null;
    private RecyclerView rvHongBaoGive = null;

    private HongBaoGiveAdapter hongBaoGiveAdapter = null;
    private ArrayList<WoDeHongBaoResult.HongBaoContent> hongbaos = new ArrayList<>();

    private PullToRefreshScrollView pullToRefreshScrollView = null;

    private boolean isEnd = false;
    private String wp = null;
    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hongbao_give;
    }

    @Override
    protected void initView(View rootView) {
        tvHongBaoCount = (TextView) rootView.findViewById(R.id.tv_wode_hongbao_give_count);
        tvHongBaototal = (TextView) rootView.findViewById(R.id.tv_wode_hongbao_give_total);
        tvHongBaoGive = (TextView) rootView.findViewById(R.id.tv_wode_hongbao_give_now);
        rvHongBaoGive = (RecyclerView) rootView.findViewById(R.id.rv_wode_hongbao_give_detail);

        pullToRefreshScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.wode_hongbao_give_refresh);
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshScrollView.setOnRefreshListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated");
        tvHongBaoGive.setOnClickListener(this);
        ((WoDeHongBaoActivity) getActivity()).setNotifyHongBaoGive(this);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvHongBaoGive.setLayoutManager(manager);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wode_hongbao_give_now:
                UriDispatcher.getInstance().dispatch(getActivity(), "xiaoma://hongbao");
                break;
            default:
                break;
        }
    }

    @Override
    public void doNetwork() {
        loadData();
    }

    private void loadData() {
        hongbaos.clear();
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "out");
        networkRequest.get(NetworkApi.URL_WO_DE_HONG_BAO_GET_GIVE,
                params,
                true,
                networkCallback
        );
    }

    private void loadMoreData() {
        if (!isEnd) {
            HashMap<String, String> params = new HashMap<>();
            params.put("wp", "wp");
            networkRequest.get(NetworkApi.URL_WO_DE_HONG_BAO_MORE,
                    params,
                    true,
                    networkCallback
            );
        } else {
            pullToRefreshScrollView.onRefreshComplete();
            Toast toast = Toast.makeText(getActivity(), "已全部加载", Toast.LENGTH_SHORT);
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

    private HongBaoGiveAdapter.OnRecyclerViewListener onRecyclerViewListener = new HongBaoGiveAdapter.OnRecyclerViewListener() {
        @Override
        public void onClickItem(int position) {
            String link = hongbaos.get(position).getLink();
            UriDispatcher.getInstance().dispatch(getActivity(), link);
        }
    };

    private NetworkCallback<WoDeHongBaoResult> networkCallback = new NetworkCallback<WoDeHongBaoResult>() {
        @Override
        protected void onSuccess(WoDeHongBaoResult response) {
            pullToRefreshScrollView.onRefreshComplete();

            String count = response.getTotalCount();
            String total = response.getTotalMoney();
            tvHongBaoCount.setText("已发出" + count + "个红包共");
            tvHongBaototal.setText(total);
            hongbaos.addAll(response.getHongbaos().getList());
            if (hongbaos.size() == 0) {
                return;
            }
            if (null == hongBaoGiveAdapter) {
                hongBaoGiveAdapter = new HongBaoGiveAdapter(hongbaos);
                hongBaoGiveAdapter.setOnRecyclerViewListener(onRecyclerViewListener);
                rvHongBaoGive.setAdapter(hongBaoGiveAdapter);
            }
            hongBaoGiveAdapter.refreshData(hongbaos);

            isEnd = response.getHongbaos().isEnd();
            wp = response.getHongbaos().getWp();
        }

        @Override
        protected void onFail(int i, String s) {
            pullToRefreshScrollView.onRefreshComplete();
        }
    };
}
