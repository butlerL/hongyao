package com.hongyao.hongbao.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.HongBaoAddResult;
import com.hongyao.hongbao.model.bean.HongBaoRegularResult;
import com.hongyao.hongbao.model.bean.HongBaoThemeResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.hongyao.hongbao.util.HongBaoThemeUtil;
import com.squareup.picasso.Picasso;
import com.xiaoma.common.activity.BaseActivity;
import com.xiaoma.common.activity.PhotoPickerActivity;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.common.util.FileUtils;
import com.xiaoma.common.util.ScreenUtils;
import com.xiaoma.common.util.XMToast;
import com.xiaoma.common.widget.photopicker.adapters.PhotoAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liaolan on 16/3/25.
 */
public class FaHongBaoActivity extends BaseActivity
        implements TextWatcher {
    private static final int REQUEST_CODE_SELECT_THEME = 0;
    private static final int REQUEST_CODE_SELECT_PHOTO = 1;

    private EditText etMoney;
    private TextView tvLimit;
    private TextView tvType;
    private EditText etDesc;
    private TextView tvMoney;
    private LinearLayout llImages;
    private ImageView ivAdd;
    private int imageWidth;
    private int imageMax = 9;
    private List<File> imageFiles = new ArrayList<>();
    private HongBaoThemeUtil.Bean theme = HongBaoThemeUtil.beans[0];
    private HongBaoRegularResult regular;
    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fa_hong_bao;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("发红包");

        etMoney = (EditText) findViewById(R.id.et_money);
        tvLimit = (TextView) findViewById(R.id.tv_limit);
        tvType = (TextView) findViewById(R.id.tv_type);
        etDesc = (EditText) findViewById(R.id.et_desc);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        llImages = (LinearLayout) findViewById(R.id.ll_images);
        ivAdd = (ImageView) findViewById(R.id.iv_add);

        etMoney.addTextChangedListener(this);
        tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FaHongBaoActivity.this, HongBaoThemeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_THEME);
            }
        });
        imageWidth = (getResources().getDisplayMetrics().widthPixels - ScreenUtils.dp2px(5) * (imageMax - 1) - ScreenUtils.dp2px(12) * 2) / imageMax;
        requestRegular();
    }

    public void selectPhoto(View view) {
        Intent intent = new Intent(this, PhotoPickerActivity.class);
        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, false);
        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoAdapter.MODE_MULTI);
        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, imageMax - imageFiles.size());
        startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTO);
    }

    public void sendHongBao(View view) {
        String money = etMoney.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            etMoney.setError("填写金额");
            return;
        }

        if (TextUtils.isEmpty(desc)) {
            etDesc.setError("填写描述");
            return;
        }

        double d = Double.parseDouble(money);
        if (d < regular.getMinPrice() * 1f / 100) {
            etMoney.setError(String.format("最小金额%.2f元", regular.getMinYuan()));
            return;
        } else if (d > regular.getMaxPrice() * 1f / 100) {
            etMoney.setError(String.format("最大金额%.2f元", regular.getMaxYuan()));
            return;
        }

        showProgress();
        HashMap<String, String> params = new HashMap<>();
        params.put("hbMoney", money);
        params.put("hbTheme", theme.code);
        params.put("hbIntroduce", desc);
        params.put("hbType", "1");
        if (imageFiles == null || imageFiles.isEmpty()) {
            networkRequest.post(NetworkApi.URL_HONG_BAO_ADD,
                    params,
                    true,
                    new NetworkCallback<HongBaoAddResult>() {
                        @Override
                        public void onSuccess(HongBaoAddResult response) {
                            hideProgress();
                            UriDispatcher.getInstance().dispatch(FaHongBaoActivity.this, "xiaoma://cashier" + "?orders=" + String.format("[\"%s\"]", response.getPayId()) + "&type=6");
                            onBackPressed();
                        }

                        @Override
                        protected void onFail(int i, String s) {
                            hideProgress();
                            XMToast.makeText(s, XMToast.LENGTH_SHORT).show();
                            if(i== 1002) {
                                UriDispatcher.getInstance().dispatch(FaHongBaoActivity.this, "xiaoma://login");
                            }
                        }

                    }
            );
        } else {
            networkRequest.postMultiImage(NetworkApi.URL_HONG_BAO_ADD,
                    params,
                    imageFiles,
                    true,
                    new NetworkCallback<HongBaoAddResult>() {
                        @Override
                        public void onSuccess(HongBaoAddResult response) {
                            hideProgress();
                            UriDispatcher.getInstance().dispatch(FaHongBaoActivity.this, "xiaoma://cashier" + "?orders=" + String.format("[\"%s\"]", response.getPayId()) + "&type=6");
                            onBackPressed();
                        }

                        @Override
                        protected void onFail(int i, String s) {
                            hideProgress();
                            XMToast.makeText(s, XMToast.LENGTH_SHORT).show();
                            if (i == 1002) {
                                UriDispatcher.getInstance().dispatch(FaHongBaoActivity.this, "xiaoma://login");
                            }
                        }

                    }
            );
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String value = s.toString().trim();
        if (!TextUtils.isEmpty(value)) {
            try {
                double money = Double.parseDouble(value);
                tvMoney.setText(String.format("￥%.2f", money));
            } catch (Exception e) {
            }
        }
    }

    private void setThemeName() {
        tvType.setText(theme.name);
    }

    private void requestRegular() {
        etMoney.setEnabled(false);
        etDesc.setEnabled(false);
        networkRequest.get(NetworkApi.URL_HONG_BAO_REGULAR,
                null,
                true,
                new NetworkCallback<HongBaoRegularResult>() {
                    @Override
                    public void onSuccess(HongBaoRegularResult response) {
                        regular = response;
                        etMoney.setEnabled(true);
                        etDesc.setEnabled(true);
                        setThemeName();
                        requestThemeDesc();
                        tvLimit.setText(regular.getSendDesc());
                    }

                    @Override
                    protected void onFail(int i, String s) {
                        if (i == 1002) {
                            UriDispatcher.getInstance().dispatch(FaHongBaoActivity.this, "xiaoma://login");
                            finish();
                        }
                    }
                });
    }

    private void requestThemeDesc() {
        HashMap<String, String> params = new HashMap<>();
        params.put("themeId", theme.code);

        networkRequest.get(NetworkApi.URL_THEME_DESC,
                params,
                true,
                networkCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_SELECT_THEME) {
                theme = (HongBaoThemeUtil.Bean) data.getSerializableExtra(HongBaoThemeActivity.THEME);
                setThemeName();
                requestThemeDesc();
            } else if (requestCode == REQUEST_CODE_SELECT_PHOTO) {
                List<String> list = (List<String>) data.getSerializableExtra(PhotoPickerActivity.EXTRA_RESULT);
                if (list == null || list.isEmpty()) {
                    return;
                }
                for (String imagePath : list) {
                    ImageView imageView = new ImageView(this);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageWidth, imageWidth);
                    if (llImages.getChildCount() != 0) {
                        lp.setMargins(ScreenUtils.dp2px(5), 0, 0, 0);
                    }
                    imageView.setLayoutParams(lp);
                    Picasso.with(this).load(new File(imagePath)).fit().into(imageView);
                    llImages.addView(imageView);
                    if (llImages.getChildCount() >= imageMax) {
                        ivAdd.setVisibility(View.GONE);
                    }
                    imageFiles.add(new File(FileUtils.getPath(this, Uri.fromFile(new File(imagePath)))));
                }
            }
        }
    }

    private NetworkCallback<HongBaoThemeResult> networkCallback = new NetworkCallback<HongBaoThemeResult>() {
        @Override
        protected void onSuccess(HongBaoThemeResult response) {
            etDesc.setText(response.getThemeDesc());
            etMoney.requestFocus();
        }

        @Override
        protected void onFail(int i, String s) {

        }
    };
}
