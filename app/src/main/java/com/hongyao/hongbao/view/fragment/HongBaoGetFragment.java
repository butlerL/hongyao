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
import com.hongyao.hongbao.adapter.HongBaoGetAdapter;
import com.hongyao.hongbao.model.bean.WoDeHongBaoResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.xiaoma.common.fragment.BaseFragment;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.common.util.Logger;
import com.xiaoma.login.UserConfig;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wjf on 2016-03-25.
 */
public class HongBaoGetFragment extends BaseFragment implements View.OnClickListener,
        PullToRefreshBase.OnRefreshListener2 {
    private final String TAG = "HongBaoGetFragment";

    private TextView tvHongBaoCount = null;
    private TextView tvHongBaototal = null;
    private TextView tvHongBaoGet = null;
    private RecyclerView rvHongBaoGet = null;

    private HongBaoGetAdapter hongBaoGetAdapter = null;
    private ArrayList<WoDeHongBaoResult.HongBaoContent> hongbaos = new ArrayList<>();

    private PullToRefreshScrollView pullToRefreshScrollView = null;

    private boolean isEnd = false;
    private String wp = null;
    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hongbao_get;
    }

    @Override
    protected void initView(View rootView) {
        tvHongBaoCount = (TextView) rootView.findViewById(R.id.tv_wode_hongbao_get_count);
        tvHongBaototal = (TextView) rootView.findViewById(R.id.tv_wode_hongbao_get_total);
        tvHongBaoGet = (TextView) rootView.findViewById(R.id.tv_wode_hongbao_get_now);
        rvHongBaoGet = (RecyclerView) rootView.findViewById(R.id.rv_wode_hongbao_get_detail);

        pullToRefreshScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.wode_hongbao_get_refresh);
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshScrollView.setOnRefreshListener(this);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated");
        tvHongBaoGet.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvHongBaoGet.setLayoutManager(manager);

        loadData();
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
            case R.id.tv_wode_hongbao_get_now:
                UriDispatcher.getInstance().dispatch(getContext(), "xiaoma://index");
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    private HongBaoGetAdapter.OnRecyclerViewListener onRecyclerViewListener = new HongBaoGetAdapter.OnRecyclerViewListener() {
        @Override
        public void onClickItem(int position) {
            String link = hongbaos.get(position).getLink();
            Log.i(TAG, "onRecyclerViewListener_link==" + link);
            UriDispatcher.getInstance().dispatch(getActivity(), link);
        }
    };

    private void loadData() {
        hongbaos.clear();
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "in");
        networkRequest.get(NetworkApi.URL_WO_DE_HONG_BAO_GET_GIVE,
                params,
                true,
                networkCallback);
    }

    private void loadMoreData() {
        String sign = UserConfig.getSign();
        if (!isEnd) {
            HashMap<String, String> params = new HashMap<>();
            params.put("wp", wp);
            networkRequest.get(NetworkApi.URL_WO_DE_HONG_BAO_MORE,
                    params,
                    true,
                    networkCallback);
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

    private NetworkCallback<WoDeHongBaoResult> networkCallback = new NetworkCallback<WoDeHongBaoResult>() {
        @Override
        protected void onSuccess(WoDeHongBaoResult response) {
            Logger.i(TAG, "onSuccess_response==" + response.toString());
            pullToRefreshScrollView.onRefreshComplete();

            String count = response.getTotalCount();
            String total = response.getTotalMoney();
            if (null != count && null != total) {
                tvHongBaoCount.setText("已摇到" + count + "个红包共");
                tvHongBaototal.setText(total);
            }

            hongbaos.addAll(response.getHongbaos().getList());
            if (hongbaos.size() == 0) {
                return;
            }
            if (null == hongBaoGetAdapter) {
                hongBaoGetAdapter = new HongBaoGetAdapter(getActivity(), hongbaos);
                hongBaoGetAdapter.setOnRecyclerViewListener(onRecyclerViewListener);
                rvHongBaoGet.setAdapter(hongBaoGetAdapter);
            }
            hongBaoGetAdapter.refreshData(hongbaos);

            isEnd = response.getHongbaos().isEnd();
            wp = response.getHongbaos().getWp();
        }

        @Override
        protected void onFail(int i, String s) {
            pullToRefreshScrollView.onRefreshComplete();
        }
    };
}
