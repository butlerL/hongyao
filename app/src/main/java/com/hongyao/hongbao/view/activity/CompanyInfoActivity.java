package com.hongyao.hongbao.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.CompanyInfo;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xiaoma.common.activity.BaseActivity;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;

import java.util.HashMap;

/**
 * Created by liaolan on 16/3/27.
 */
public class CompanyInfoActivity extends BaseActivity {
    public static final String COMPANY_ID = "companyId";

    private RoundedImageView rivAvatar;
    private TextView tvName;
    private TextView tvAccount;
    private TextView tvIntro;
    private TextView tvVerify;
    private TextView tvFollow;
    private String companyId;
    private CompanyInfo companyInfo;
    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        companyId = getIntent().getStringExtra(COMPANY_ID);

        rivAvatar = (RoundedImageView) findViewById(R.id.riv_avatar);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvAccount = (TextView) findViewById(R.id.tv_account);
        tvIntro = (TextView) findViewById(R.id.tv_intro);
        tvVerify = (TextView) findViewById(R.id.tv_verify);
        tvFollow = (TextView) findViewById(R.id.tv_follow);

        tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (companyInfo != null) {
                    final boolean isFollow = companyInfo.isIsFollowed();
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("companyId", companyId);
                    networkRequest.get(
                            isFollow ? NetworkApi.URL_OFFICIAL_UNFOLLOW : NetworkApi.URL_OFFICIAL_FOLLOW,
                            params,
                            true,
                            new NetworkCallback<Object>() {
                                @Override
                                public void onSuccess(Object response) {
                                    companyInfo.setIsFollowed(!isFollow);
                                    setFollow(!isFollow);
                                }

                                @Override
                                protected void onFail(int i, String s) {

                                }

                            }
                    );
                }
            }
        });

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("companyId", companyId);
        networkRequest.get(NetworkApi.URL_COMPANY_INFO,
                params,
                true,
                networkCallback);
    }

    private void setFollow(boolean isFallow) {
        tvFollow.setText(isFallow ? "已关注" : "加关注");
    }

    private NetworkCallback<CompanyInfo> networkCallback = new NetworkCallback<CompanyInfo>() {
        @Override
        protected void onSuccess(CompanyInfo response) {
            companyInfo = response;
            setTitle(response.getName());
            Picasso.with(CompanyInfoActivity.this).load(response.getAvatar()).fit().into(rivAvatar);
            tvName.setText(response.getName());
            tvAccount.setText(String.format("利市号: %s", response.getAccount()));
            tvIntro.setText(response.getIntro());
            tvVerify.setText(response.getVerify());
            setFollow(response.isIsFollowed());
        }

        @Override
        protected void onFail(int i, String s) {

        }
    };
}
