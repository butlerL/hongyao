package com.hongyao.hongbao.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.eventBus.YaoHongBaoEvent;
import com.hongyao.hongbao.model.bean.HongBaoDetail;
import com.hongyao.hongbao.model.bean.HongBaoShakeResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.hongyao.hongbao.util.HongBaoThemeUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.xiaoma.common.fragment.BaseFullScreenDialogFragment;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.route.UriDispatcher;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by liaolan on 15/6/29.
 */
public class ShakeFragment extends BaseFullScreenDialogFragment
        implements View.OnClickListener, IWXAPIEventHandler {

    private ImageView ivTheme;
    private GifImageView giv;
    private LinearLayout llSuccess;
    private TextView tvMoney;
    private TextView tvLookMore;
    private TextView tvShare;
    private GifDrawable gifDrawable;
    private Vibrator vibrator;
    private SoundPool soundPool;
    private int soundId;
    private String hbId;
    private String hbTheme;
    private HongBaoThemeUtil.Bean bean;
    private HongBaoShakeResult response;
    private Handler handler = new Handler();

    private HongBaoDetail.ShareInfoBean hbShare;
    private String shareUrl;
    private String shareDesc;
    private String shareTitle;
    private String shareImage;
    private LayoutInflater inflater;

    public static void shake(FragmentManager fm, String hbId, String hbTheme, HongBaoDetail.ShareInfoBean hbShare) {
        ShakeFragment f = new ShakeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hbId", hbId);
        bundle.putString("hbTheme", hbTheme);
        bundle.putSerializable("hbShare", hbShare);
        f.setArguments(bundle);
        f.show(fm, "ShakeFragment");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shake;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hbId = getArguments().getString("hbId");
        hbTheme = getArguments().getString("hbTheme");
        hbShare = (HongBaoDetail.ShareInfoBean) getArguments().getSerializable("hbShare");
        shareUrl = hbShare.getLink();
        shareDesc = hbShare.getDesc();
        shareTitle = hbShare.getTitle();
        shareImage = hbShare.getImage();

        ivTheme = (ImageView) view.findViewById(R.id.iv_theme);
        giv = (GifImageView) view.findViewById(R.id.giv_shake);
        llSuccess = (LinearLayout) view.findViewById(R.id.ll_success);
        tvMoney = (TextView) view.findViewById(R.id.tv_money);
        tvLookMore = (TextView) view.findViewById(R.id.tv_look_more);
        tvShare = (TextView) view.findViewById(R.id.tv_shake_share);

        tvLookMore.setOnClickListener(this);
        tvShare.setOnClickListener(this);

        bean = HongBaoThemeUtil.getTheme(hbTheme);
        ivTheme.setImageResource(bean.drawableNormal);
        gifDrawable = (GifDrawable) giv.getDrawable();

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundPool.load(getContext(), R.raw.shake, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundId = sampleId;
                startShake();
            }
        });
        inflater = getLayoutInflater(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopShake();
        handler.removeCallbacks(runnable);
    }

    private void startShake() {
        soundPool.play(soundId, 1, 1, 0, -1, 1);
        vibrator.vibrate(new long[]{100, 500, 500, 500}, 2);
        HashMap<String, String> params = new HashMap<>();
        params.put("hbId", hbId);
        networkRequest.get(NetworkApi.URL_HONG_BAO_YAO,
                params,
                true,
                networkCallback);
    }

    private void stopShake() {
        giv.setVisibility(View.GONE);
        gifDrawable.stop();
        vibrator.cancel();
        soundPool.stop(soundId);
    }

    private void shopShakeDelay() {
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_look_more:
                UriDispatcher.getInstance().dispatch(getContext(), "xiaoma://index");
                dismiss();
                break;
            case R.id.tv_shake_share:
                ShareDialogFragment.show(getChildFragmentManager(), shareTitle, shareImage, shareUrl, shareDesc);
                break;
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            stopShake();
            if (response != null) {
                llSuccess.setVisibility(View.VISIBLE);
                ivTheme.setImageResource(bean.drawableGotten);
                tvMoney.setText(String.format("%s元", response.getMoney()));

                if (response.isIsLogin()) {
                    YaoHongBaoEvent event = new YaoHongBaoEvent();
                    event.hbId = hbId;
                    event.money = response.getMoney();
                    event.status = 2;
                    EventBus.getDefault().post(event);
                } else {

                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setMessage("请登录后领取红包")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    UriDispatcher.getInstance().dispatch(getContext(), "xiaoma://login");
                                }
                            })
                            .create();
                    dialog.show();
                }
            }
        }
    };


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        String result = "";

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "errcode_success";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "errcode_cancel";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "errcode_deny";
                break;
            default:
                result = "errcode_unknown";
                break;
        }
    }

    private NetworkCallback<HongBaoShakeResult> networkCallback = new NetworkCallback<HongBaoShakeResult>() {
        @Override
        protected void onSuccess(HongBaoShakeResult response) {
            ShakeFragment.this.response = response;
            shopShakeDelay();
        }

        @Override
        protected void onFail(int i, String s) {
            if (i == 5001) {
                ivTheme.setImageResource(bean.drawableEmpty);
                YaoHongBaoEvent event = new YaoHongBaoEvent();
                event.hbId = hbId;
                event.money = "";
                event.status = 3;
                EventBus.getDefault().post(event);
            } else if (i == 5002) {
                dismiss();
            }
            shopShakeDelay();
        }
    };
}
