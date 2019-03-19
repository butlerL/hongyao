package com.hongyao.hongbao.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.PayInShopResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xiaoma.common.activity.BaseActivity;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.route.UriDispatcher;

import java.util.HashMap;

/**
 * Created by liaolan on 16/3/28.
 */
public class CompanyPayActivity extends BaseActivity implements TextWatcher {
    private RoundedImageView rivAvatar;
    private TextView tvName;
    private EditText etMoney;
    private EditText etDesc;
    private TextView tvConfirm;

    private String companyId;
    private String name;
    private String avatar;

    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_pay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("到店支付");

        rivAvatar = (RoundedImageView) findViewById(R.id.riv_avatar);
        tvName = (TextView) findViewById(R.id.tv_name);
        etMoney = (EditText) findViewById(R.id.et_money);
        etDesc = (EditText) findViewById(R.id.et_desc);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        etMoney.addTextChangedListener(this);

        companyId = getQueryParameter("companyId");
        name = getQueryParameter("name");
        avatar = getQueryParameter("avatar");
        Picasso.with(this).load(avatar).fit().into(rivAvatar);
        tvName.setText(name);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = etMoney.getText().toString().trim();
                String remark = etDesc.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    return;
                }
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("money", money);
                params.put("companyId", companyId);
                params.put("remark", remark);
                networkRequest.get(NetworkApi.URL_OFFICIAL_INSHOPPAY,
                        params,
                        true,
                        new NetworkCallback<PayInShopResult>() {
                            @Override
                            public void onSuccess(PayInShopResult response) {
                                UriDispatcher.getInstance().dispatch(CompanyPayActivity.this, String.format("xiaoma://cashier?payId=%s", response.getPayId()));
                                onBackPressed();
                            }

                            @Override
                            protected void onFail(int i, String s) {

                            }

                        });
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        tvConfirm.setBackgroundColor(s.length() > 0 ? Color.parseColor("#EC312D") : Color.WHITE);
        tvConfirm.setTextColor(s.length() > 0 ? Color.WHITE : Color.parseColor("#949494"));
        tvConfirm.setEnabled(s.length() > 0 ? true : false);
    }
}
