package com.hongyao.hongbao.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.WoDeResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xiaoma.common.fragment.BaseFragment;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.login.UserConfig;
import com.xiaoma.share2.WebShareViewActivity;

/**
 * Created by liaolan on 16/3/23.
 */
public class TabWoDeFragment extends BaseFragment implements View.OnClickListener {
    private final String TAG = "TabWoDeFragment";

    private LinearLayout llWoDeHongBao = null;
    private LinearLayout llWoDeYuE = null;
    private LinearLayout llWoDeYaoShi = null;

    private TextView tvWoDeHongBao = null;
    private TextView tvWoDeYuE = null;
    private TextView tvWoDeYaoShi = null;
    private RoundedImageView ivAvatar = null;
    private TextView tvId;
    private TextView tvWoDeName = null;
    private ImageView ivWoDeGender = null;
    private ImageView ivIsV = null;

    private LinearLayout llVipCenter = null;
    private LinearLayout llWoDeOrders = null;
    private LinearLayout llWoDeCart = null;
    private LinearLayout llInvit = null;
    private LinearLayout llRank = null;
    private LinearLayout llWoDeSetting = null;
    private LinearLayout llAbout = null;
    private LinearLayout llReturnMoney = null;

    private String vipCenterURI = null;
    private String inviteURI = null;
    private String rankURI = null;
    private String aboutLink = null;
    private String returnMoneyLink = null;

    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wode;
    }

    @Override
    protected void initView(View rootView) {
        rootView.findViewById(R.id.iv_back).setVisibility(View.GONE);
        ((TextView) rootView.findViewById(R.id.title)).setText("我的");
        llWoDeHongBao = (LinearLayout) rootView.findViewById(R.id.ll_wode_hongbao);
        llWoDeYuE = (LinearLayout) rootView.findViewById(R.id.ll_wode_yue);
        llWoDeYaoShi = (LinearLayout) rootView.findViewById(R.id.ll_wode_yaoshi);

        tvWoDeHongBao = (TextView) rootView.findViewById(R.id.tv_wode_hongbao);
        tvWoDeYuE = (TextView) rootView.findViewById(R.id.tv_wode_yue);
        tvWoDeYaoShi = (TextView) rootView.findViewById(R.id.tv_wode_yaoshi);

        ivAvatar = (RoundedImageView) rootView.findViewById(R.id.iv_wode_photo);
        tvId = (TextView) rootView.findViewById(R.id.tv_id);
        tvWoDeName = (TextView) rootView.findViewById(R.id.tv_wode_name);
        ivWoDeGender = (ImageView) rootView.findViewById(R.id.iv_wode_gender);
        ivIsV = (ImageView) rootView.findViewById(R.id.iv_wode_photo_isv);

        llVipCenter = (LinearLayout) rootView.findViewById(R.id.ll_wode_vip_center);
        llWoDeOrders = (LinearLayout) rootView.findViewById(R.id.ll_wode_orders);
        llWoDeCart = (LinearLayout) rootView.findViewById(R.id.ll_wode_cart);
        llInvit = (LinearLayout) rootView.findViewById(R.id.ll_wode_recommendation);
        llRank = (LinearLayout) rootView.findViewById(R.id.ll_wode_rank);
        llWoDeSetting = (LinearLayout) rootView.findViewById(R.id.ll_wode_setting);
        llAbout = (LinearLayout) rootView.findViewById(R.id.ll_about);
        llReturnMoney = (LinearLayout) rootView.findViewById(R.id.ll_wode_return_money);

        rootView.findViewById(R.id.ll_after_sale).setOnClickListener(this);
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
        String sign = UserConfig.getSign();
        if (TextUtils.isEmpty(sign)) {
            return;
        }
        showProgress();
        networkRequest.get(NetworkApi.URL_WO_DE, null, true, networkCallback);
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated");
        llWoDeHongBao.setOnClickListener(this);
        llWoDeYuE.setOnClickListener(this);
        llWoDeYaoShi.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        ivAvatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        llVipCenter.setOnClickListener(this);
        llWoDeOrders.setOnClickListener(this);
        llWoDeCart.setOnClickListener(this);
        llInvit.setOnClickListener(this);
        llRank.setOnClickListener(this);
        llWoDeSetting.setOnClickListener(this);
        llAbout.setOnClickListener(this);
        llReturnMoney.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String sign = UserConfig.getSign();
        switch (v.getId()) {
            case R.id.ll_wode_hongbao:
                UriDispatcher.getInstance().dispatch(getActivity(), "xiaoma://myHongBao");
                break;
            case R.id.ll_wode_yue:
                UriDispatcher.getInstance().dispatch(getActivity(), "xiaoma://myBalance");
                break;
            case R.id.ll_wode_yaoshi:
                UriDispatcher.getInstance().dispatch(getActivity(), "xiaoma://myKey");
                break;
            case R.id.iv_wode_photo:
                UriDispatcher.getInstance().dispatch(getActivity(), "xiaoma://setting");
                break;
            case R.id.ll_wode_vip_center:
                UriDispatcher.getInstance().dispatch(getActivity(), vipCenterURI + "?sign=" + sign);
                break;
            case R.id.ll_wode_orders:
                UriDispatcher.getInstance().dispatch(getActivity(), "xiaoma://mallOrderList");
                break;
            case R.id.ll_after_sale:
                UriDispatcher.getInstance().dispatch(getActivity(), "xiaoma://mallAfterSale");
                break;
            case R.id.ll_wode_cart:
                UriDispatcher.getInstance().dispatch(getActivity(), "xiaoma://mallCart");
                break;
            case R.id.ll_wode_recommendation:
                Intent intent = new Intent(getContext(), WebShareViewActivity.class);
                intent.setData(Uri.parse(inviteURI));
                startActivity(intent);
                break;
            case R.id.ll_wode_rank:
                UriDispatcher.getInstance().dispatch(getActivity(), rankURI + "?sign=" + sign);
                break;
            case R.id.ll_wode_setting:
                UriDispatcher.getInstance().dispatch(getActivity(), "xiaoma://setting");
                break;
            case R.id.ll_about:
                if (!TextUtils.isEmpty(aboutLink)) {
                    UriDispatcher.getInstance().dispatch(getActivity(), aboutLink);
                }
                break;
            case R.id.ll_wode_return_money:
                if (!TextUtils.isEmpty(returnMoneyLink)) {
                    UriDispatcher.getInstance().dispatch(getActivity(), returnMoneyLink);
                }
                break;
            default:
                break;
        }
    }

    private NetworkCallback<WoDeResult> networkCallback = new NetworkCallback<WoDeResult>() {
        @Override
        protected void onSuccess(WoDeResult response) {
            Log.i(TAG, "wo_de_response==" + response.toString());
            String hongbao = response.getHongbao();
            String yue = response.getYue();
            String yaoshi = response.getYaoshi();

            boolean isV = response.isV();
            tvId.setText(String.format("街圈号：%s", response.getIdentifier()));
            vipCenterURI = response.getCenterLink();
            inviteURI = response.getInviteLink();
            rankURI = response.getTopLink();
            aboutLink = response.getAboutLink();
            returnMoneyLink = response.getReturnMoneyLink();

            tvWoDeHongBao.setText(hongbao);
            tvWoDeYuE.setText(yue);
            tvWoDeYaoShi.setText(yaoshi);

            if (isV) {
                ivIsV.setVisibility(View.VISIBLE);
            } else {
                ivIsV.setVisibility(View.GONE);
            }

            String avatarUri = response.getAvatar();
            String uname = response.getUname();
            String gender = response.getGender();

            Picasso.with(getActivity()).load(avatarUri).fit().into(ivAvatar);
            tvWoDeName.setText(uname);
            if (gender.equals(WoDeResult.WO_DE_GENDER_MALE)) {
                ivWoDeGender.setImageResource(R.drawable.ic_male);
            } else if (gender.equals(WoDeResult.WO_DE_GENDER_FEMALE)) {
                ivWoDeGender.setImageResource(R.drawable.ic_female);
            } else {
                ivWoDeGender.setImageResource(R.drawable.ic_lishi);
            }
            hideProgress();
        }

        @Override
        protected void onFail(int i, String s) {
            hideProgress();
            if (i == 1002) {
                UriDispatcher.getInstance().dispatch(getActivity(), "xiaoma://login");
            }
        }
    };
}
