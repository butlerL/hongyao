package com.hongyao.hongbao.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hongyao.hongbao.R;
import com.hongyao.hongbao.iview.ISplashView;
import com.hongyao.hongbao.model.bean.SplashPageBean;
import com.hongyao.hongbao.model.bean.VersionBean;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.hongyao.hongbao.presenter.SplashPresenter;
import com.hongyao.hongbao.util.Push;
import com.hongyao.hongbao.view.fragment.TabWoDeFragment;
import com.hongyao.hongbao.view.fragment.TabYaoHongBaoFragment;
import com.hongyao.hongbao.view.fragment.TabYongHongBaoFragment;
import com.squareup.picasso.Picasso;
import com.umeng.message.PushAgent;
import com.xiaoma.common.activity.CommonMainActivity;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.common.util.DeviceInfoUtil;
import com.xiaoma.common.util.Preferences;
import com.xiaoma.common.widget.tab.TabInfo;
import com.xiaoma.im.fragment.ImFragment;

public class MainActivity extends CommonMainActivity {
    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected TabInfo[] getTabInfos() {
        return new TabInfo[]{
                new TabInfo("摇红包", TabYaoHongBaoFragment.class, null, R.drawable.tab_yao_hong_bao, null, null),
                new TabInfo("用红包", TabYongHongBaoFragment.class, null, R.drawable.tab_yong_hong_bao, null, null),
                new TabInfo("发红包", Fragment.class, null, R.drawable.tab_fa_hong_bao, Uri.parse("xiaoma://login"), Uri.parse("xiaoma://hongbao")),
                new TabInfo("发烧友", ImFragment.class, null, R.drawable.tab_fa_shao_you, Uri.parse("xiaoma://login"), null),
                new TabInfo("我的", TabWoDeFragment.class, null, R.drawable.tab_wo_de, Uri.parse("xiaoma://login"), null),
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleBar.hideTitleBar();

        PushAgent.getInstance(this).enable();
        Push.start(this);

        String adLink = getIntent().getStringExtra("splash_ad_link");
        try {
            UriDispatcher.getInstance().dispatch(this, adLink);
        } catch (Exception e) {
        }
        loadSplashAds();
        checkVersion();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkRequest.clear();
    }

    private void loadSplashAds() {
        SplashPresenter presenter = new SplashPresenter(splashView);
        presenter.loadData();
    }

    private ISplashView splashView = new ISplashView() {
        @Override
        public void onLoadSuccess(Object response, boolean b) {
            SplashPageBean splashPageBean = new Gson().fromJson(new Gson().toJson(response), SplashPageBean.class);
            Preferences.putString("splash_ads", new Gson().toJson(response));
            for (SplashPageBean.GuidePagesBean pagesBean : splashPageBean.getGuidePages()) {
                Picasso.with(MainActivity.this).load(pagesBean.getImage()).fetch();
            }
        }

        @Override
        public void onError(int i, String s) {

        }
    };

    private void checkVersion() {
        networkRequest.get(NetworkApi.URL_VERSION,
                null,
                false,
                new NetworkCallback<VersionBean>() {

                    @Override
                    protected void onSuccess(final VersionBean versionBean) {
                        if (isHaveNewVersion(versionBean.getVersionCode())) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("有新版本");
                            builder.setMessage("新版本更新:" + versionBean.getUpdateLog() + "\n"
                                    + "更新包大小:" + versionBean.getFileSize());
                            builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(versionBean.getLink()));
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            if (!versionBean.isForce()) {
                                builder.setNegativeButton("暂不更新", null);
                            }

                            builder.setCancelable(false);
                            builder.create().show();
                        }
                    }

                    @Override
                    protected void onFail(int i, String s) {

                    }
                });
    }

    private boolean isHaveNewVersion(String versionName) {
        if (TextUtils.isEmpty(versionName)) {
            return false;
        }
        if (!versionName.contains(".")) {
            return false;
        }
        String[] splitNames = versionName.split("\\.");
        int[] splitCodes = new int[splitNames.length];
        for (int i = 0; i < splitNames.length; i++) {
            splitCodes[i] = Integer.valueOf(splitNames[i]);
        }
        String[] localSplitNames = DeviceInfoUtil.getAppVersionName(MainActivity.this).split("\\.");
        int[] localSplitCodes = new int[localSplitNames.length];
        for (int i = 0; i < localSplitNames.length; i++) {
            localSplitCodes[i] = Integer.valueOf(localSplitNames[i]);
        }
        if (splitNames.length >= localSplitNames.length) {
            boolean isNewVersion = false;
            for (int i = 0; i < localSplitNames.length; i++) {
                if (splitCodes[i] > localSplitCodes[i]){
                    isNewVersion = true;
                    break;
                }
            }
            return isNewVersion;
        }
        return false;
    }
}
