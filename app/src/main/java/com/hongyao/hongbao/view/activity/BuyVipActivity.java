package com.hongyao.hongbao.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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
 * Created by wjf on 16/3/31.
 */
public class BuyVipActivity extends BaseActivity {
    private final String TAG = "BuyVipActivity";

    private RecyclerView recyclerView = null;

    private String[] counts = {"1", "3", "6", "12"};
    private String[] pays = {"20", "50", "100", "180"};
    private String[] types = {"1", "2", "3", "4"};
    private ArrayList<WoDeYaoShiResult.YaoShiBuyType> buyTypes = new ArrayList<>();
    private NetworkRequest networkRequest = new NetworkRequest();
    private YaoShiBuyTypeAdapter buyTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("会员支付");

        recyclerView = (RecyclerView) findViewById(R.id.rv_wode_buy_vip);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

//        buyTypes = initBuyTypes(counts, pays);
        buyTypeAdapter = new YaoShiBuyTypeAdapter(onRecyclerViewListener);
        recyclerView.setAdapter(buyTypeAdapter);
        loadBuyVipList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_vip;
    }

    private ArrayList<WoDeYaoShiResult.YaoShiBuyType> initBuyTypes(String[] counts, String[] pays) {
        ArrayList<WoDeYaoShiResult.YaoShiBuyType> buyTypes = new ArrayList<>();

        for (int i = counts.length - 1; i >= 0; i--) {
            String count = counts[i];
            String desc = counts[i] + "个月";
            String pay = pays[i];
            String type = types[i];
            WoDeYaoShiResult.YaoShiBuyType buyType = new WoDeYaoShiResult.YaoShiBuyType(count, desc, pay, type);
            buyTypes.add(buyType);
        }
        return buyTypes;
    }

    private void loadPayId(String type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type);
        networkRequest.get(NetworkApi.URL_WO_DE_BUY_VIP,
                params,
                true,
                reqBuyKeyNetwork
        );
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private YaoShiBuyTypeAdapter.OnRecyclerViewListener onRecyclerViewListener = new YaoShiBuyTypeAdapter.OnRecyclerViewListener() {
        @Override
        public void onClickBuy(String type) {
            Log.i(TAG, "购买类型：" + type);
            loadPayId(type);
        }
    };


    private NetworkCallback reqBuyKeyNetwork = new NetworkCallback<WoDeYaoShiResult.YaoShiBuyId>() {
        @Override
        public void onSuccess(WoDeYaoShiResult.YaoShiBuyId response) {
            String payId = response.getPayId();
            Log.i(TAG, "-------payId==" + payId);
            UriDispatcher.getInstance().dispatch(BuyVipActivity.this, "xiaoma://cashier" + "?orders=" + String.format("[\"%s\"]", payId) + "&type=5");
        }

        @Override
        protected void onFail(int i, String s) {

        }
    };

    private void loadBuyVipList() {
        networkRequest.get(
                NetworkApi.URL_BUY_VIP_LIST,
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
