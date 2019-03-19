package com.hongyao.hongbao.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.adapter.YaoShiBuyTypeAdapter;
import com.hongyao.hongbao.model.bean.BuyKeysListBean;
import com.hongyao.hongbao.model.bean.WoDeYaoShiResult;
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
public class WoDeYaoShiActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "WoDeYaoShiActivity";

    private LinearLayout llYaoShiDesc = null;
    private TextView tvYaoshiTotal = null;
    private TextView tvYaoShiFunc = null;
    private TextView tvHowDesc = null;
    private RecyclerView recyclerView = null;

    private String yaoshiDescLink = null;

    private String[] counts = {"20", "100", "500", "1000"};
    private String[] pays = {"2", "10", "50", "100"};
    private String[] types = {"1", "2", "3", "4"};
    private ArrayList<WoDeYaoShiResult.YaoShiBuyType> buyTypes = new ArrayList<>();
    private NetworkRequest networkRequest = new NetworkRequest();
    private YaoShiBuyTypeAdapter buyTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setTitle("我的摇匙");

        tvYaoshiTotal = (TextView) findViewById(R.id.tv_wode_yaoshi_total);
        llYaoShiDesc = (LinearLayout) findViewById(R.id.ll_wode_yaoshi_desc);
        tvYaoShiFunc = (TextView) findViewById(R.id.tv_wode_yaoshi_function);
        tvHowDesc = (TextView) findViewById(R.id.tv_how_desc);
        llYaoShiDesc.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_wode_yaoshi_buy);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

//        buyTypes = initBuyTypes(counts, pays);
        buyTypeAdapter = new YaoShiBuyTypeAdapter(onRecyclerViewListener);
        recyclerView.setAdapter(buyTypeAdapter);
        loadBuyKeysList();
    }

    private ArrayList<WoDeYaoShiResult.YaoShiBuyType> initBuyTypes(String[] counts, String[] pays) {
        ArrayList<WoDeYaoShiResult.YaoShiBuyType> buyTypes = new ArrayList<>();

        for (int i = counts.length - 1; i >= 0; i--) {
            String count = counts[i];
            String desc = counts[i] + "枚摇匙";
            String pay = pays[i];
            String type = types[i];
            WoDeYaoShiResult.YaoShiBuyType buyType = new WoDeYaoShiResult.YaoShiBuyType(count, desc, pay, type);
            buyTypes.add(buyType);
        }
        return buyTypes;
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
        loadData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wo_de_yao_shi;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_wode_yaoshi_desc:
                UriDispatcher.getInstance().dispatch(this, yaoshiDescLink);
                break;
            default:
                break;
        }
    }

    private void loadData() {
        networkRequest.get(NetworkApi.URL_WO_DE_YAO_SHI,
                null,
                true,
                reqNetwork
        );
    }

    private void loadPayId(String type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type);
        networkRequest.get(NetworkApi.URL_WO_DE_YAO_SHI_BUY,
                params,
                true,
                reqBuyKeyNetwork
        );
    }

    private YaoShiBuyTypeAdapter.OnRecyclerViewListener onRecyclerViewListener = new YaoShiBuyTypeAdapter.OnRecyclerViewListener() {
        @Override
        public void onClickBuy(String type) {
            Log.i(TAG, "购买类型：" + type);
            loadPayId(type);
        }
    };

    private NetworkCallback reqNetwork = new NetworkCallback<WoDeYaoShiResult>() {
        @Override
        public void onSuccess(WoDeYaoShiResult response) {
            String yaoshiTotal = response.getKeyNum();
            yaoshiDescLink = response.getLink();

            tvYaoshiTotal.setText(yaoshiTotal);
        }

        @Override
        protected void onFail(int i, String s) {

        }
    };

    private NetworkCallback reqBuyKeyNetwork = new NetworkCallback<WoDeYaoShiResult.YaoShiBuyId>() {
        @Override
        public void onSuccess(WoDeYaoShiResult.YaoShiBuyId response) {
            String payId = response.getPayId();
            Log.i(TAG, "-------payId==" + payId);
            UriDispatcher.getInstance().dispatch(WoDeYaoShiActivity.this, "xiaoma://cashier" + "?orders=" + String.format("[\"%s\"]", payId) + "&type=4");
        }

        @Override
        protected void onFail(int i, String s) {

        }

    };

    private void loadBuyKeysList() {
        networkRequest.get(
                NetworkApi.URL_BUY_KEYS_LIST,
                null,
                false,
                new NetworkCallback<BuyKeysListBean>() {
                    @Override
                    public void onSuccess(BuyKeysListBean response) {
                        for (BuyKeysListBean.ListBean listBean : response.getList()) {
                            String count = String.valueOf(listBean.getNum());
                            String desc = listBean.getDesc();
                            String pay = listBean.getMoney();
                            String type = String.valueOf(listBean.getType());
                            WoDeYaoShiResult.YaoShiBuyType buyType = new WoDeYaoShiResult.YaoShiBuyType(count, desc, pay, type);
                            buyTypes.add(buyType);
                            if (tvYaoShiFunc != null) {
                                tvYaoShiFunc.setText(Html.fromHtml(response.getWhat()));
                            }
                            if (tvHowDesc != null) {
                                tvHowDesc.setText(Html.fromHtml(response.getHow()));
                            }
                        }
                        buyTypeAdapter.setBuyTypes(buyTypes);
                    }

                    @Override
                    public void onFail(int code, String message) {
                    }
                }
        );
    }
}
