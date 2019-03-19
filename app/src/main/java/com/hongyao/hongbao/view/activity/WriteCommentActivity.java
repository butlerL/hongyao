package com.hongyao.hongbao.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.eventBus.HongBaoCommentEvent;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.xiaoma.common.activity.BaseActivity;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by liaolan on 16/3/25.
 */
public class WriteCommentActivity extends BaseActivity
        implements TextWatcher, View.OnClickListener {
    private EditText etContent;
    private TextView tvSend;
    private String hbId;
    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("评论");
        hbId = getIntent().getStringExtra("hbId");

        etContent = (EditText) findViewById(R.id.et_comment);
        tvSend = (TextView) findViewById(R.id.tv_send);
        etContent.addTextChangedListener(this);
        tvSend.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_comment;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        tvSend.setEnabled(s.length() > 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send:
                String content = etContent.getText().toString().trim();
                HashMap<String, String> params = new HashMap<>();
                params.put("hbId", hbId);
                params.put("content", content);
                networkRequest.post(NetworkApi.URL_HONG_BAO_ADD_COMMENT,
                        params,
                        true,
                        networkCallback
                );
                break;
        }
    }

    private NetworkCallback networkCallback = new NetworkCallback() {
        @Override
        protected void onSuccess(Object o) {
            EventBus.getDefault().post(new HongBaoCommentEvent(hbId));
            onBackPressed();
        }

        @Override
        protected void onFail(int i, String s) {

        }
    };
}
