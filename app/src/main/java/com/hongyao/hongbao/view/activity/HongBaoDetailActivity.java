package com.hongyao.hongbao.view.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hongyao.hongbao.R;
import com.hongyao.hongbao.adapter.CommentAdapter;
import com.hongyao.hongbao.adapter.HbUserAdapter;
import com.hongyao.hongbao.eventBus.HongBaoCommentEvent;
import com.hongyao.hongbao.eventBus.YaoHongBaoEvent;
import com.hongyao.hongbao.model.bean.HongBaoDetail;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.hongyao.hongbao.view.fragment.ShakeFragment;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xiaoma.common.activity.BaseActivity;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.common.util.ScreenUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

/**
 * Created by liaolan on 16/3/24.
 */
public class HongBaoDetailActivity extends BaseActivity
        implements PullToRefreshBase.OnRefreshListener2<ListView>,
        SensorEventListener {

    private PullToRefreshListView ptrComment;
    private String hongBaoId;
    private RoundedImageView rivAvatar;
    private ImageView ivVip;
    private TextView tvWhoseHongBao;
    private TextView tvBlessing;
    private TextView tvMoney;
    private TextView tvHongBaoState;
    private RecyclerView rvUser;
    private TextView tvInroContent;
    private TextView tvRuleContent;
    private TextView tvCommentCount;
    private TextView tvMyHongBao;
    private ViewFlipper vf;

    private CommentAdapter commentAdapter;
    private HbUserAdapter hbUserAdapter;
    private HongBaoDetail.HbInfoBean hbInfo;
    private HongBaoDetail.HbUsersBean hbUsers;
    private HongBaoDetail.ShareInfoBean hbShare;
    private String wp;
    private boolean isEnd;
    private boolean isRefresh;
    private ShakeFragment shakeFragment;

    private SensorManager sensorManager;
    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hong_bao_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("红包详情");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        EventBus.getDefault().register(this);

        hongBaoId = getQueryParameter("hbId");
        if (TextUtils.isEmpty(hongBaoId)) {
            onBackPressed();
        }

        ptrComment = (PullToRefreshListView) findViewById(R.id.ptr_comment);
        ptrComment.setOnRefreshListener(this);
        ListView lvComment = ptrComment.getRefreshableView();
        lvComment.setOverScrollMode(View.OVER_SCROLL_NEVER);
        View header = LayoutInflater.from(this).inflate(R.layout.header_hong_bao_detail, lvComment, false);
        lvComment.addHeaderView(header);

        rivAvatar = (RoundedImageView) findViewById(R.id.riv_avatar);
        ivVip = (ImageView) findViewById(R.id.iv_v);
        tvWhoseHongBao = (TextView) findViewById(R.id.tv_whose_hong_bao);
        tvBlessing = (TextView) findViewById(R.id.tv_blessing);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        tvHongBaoState = (TextView) findViewById(R.id.tv_hong_bao_current_state);
        rvUser = (RecyclerView) findViewById(R.id.rv_user);
        tvInroContent = (TextView) findViewById(R.id.tv_intro_content);
        tvRuleContent = (TextView) findViewById(R.id.tv_rule_content);
        tvCommentCount = (TextView) findViewById(R.id.tv_comment_count);
        tvMyHongBao = (TextView) findViewById(R.id.tv_my_hong_bao);
        vf = (ViewFlipper) findViewById(R.id.vf);

        rvUser.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvUser.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int parentWidth = parent.getWidth();
                int childCount = 6;
                int childWidth = ScreenUtils.dp2px(49);
                int space = (parentWidth - childCount * childWidth) / (childCount - 1) - 2;
                int position = parent.getChildAdapterPosition(view);
                if (position == 0) {
                    outRect.left = 0;
                    outRect.top = 0;
                    outRect.right = space / 2;
                    outRect.bottom = 0;
                } else if (position == parent.getAdapter().getItemCount() - 1) {
                    outRect.left = space / 2;
                    outRect.top = 0;
                    outRect.right = 0;
                    outRect.bottom = 0;
                } else {
                    outRect.left = space / 2;
                    outRect.top = 0;
                    outRect.right = space / 2;
                    outRect.bottom = 0;
                }
            }
        });
        hbUserAdapter = new HbUserAdapter(this);
        rvUser.setAdapter(hbUserAdapter);

        commentAdapter = new CommentAdapter(this);
        lvComment.setAdapter(commentAdapter);

        refreshData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void refreshData() {
        isRefresh = true;
        HashMap<String, String> params = new HashMap<>();
        params.put("hbId", hongBaoId);
        networkRequest.get(NetworkApi.URL_HONG_BAO_DETAIL,
                params,
                true,
                networkCallback);
    }

    private void loadMore() {
        if (isEnd || TextUtils.isEmpty(wp)) {
            ptrComment.onRefreshComplete();
            return;
        }
        isRefresh = false;
        HashMap<String, String> params = new HashMap<>();
        params.put("hbId", hongBaoId);
        params.put("wp", wp);
        networkRequest.get(NetworkApi.URL_HONG_BAO_COMMENT,
                params,
                true,
                networkCallback);
    }

    public void lookAllUser(View view) {
        if (hbInfo != null) {
            UriDispatcher.getInstance().dispatch(HongBaoDetailActivity.this, hbUsers.getLink());
        }
    }

    public void lookIntro(View view) {
        if (hbInfo != null) {
            UriDispatcher.getInstance().dispatch(HongBaoDetailActivity.this, hbInfo.getIntroLink());
        }
    }

    public void lookRule(View view) {
        if (hbInfo != null) {
            UriDispatcher.getInstance().dispatch(HongBaoDetailActivity.this, hbInfo.getRuleLink());
        }
    }

    public void writeComment(View view) {
        Intent intent = new Intent(this, WriteCommentActivity.class);
        intent.putExtra("hbId", hongBaoId);
        startActivity(intent);
    }

    public void useHongBao(View view) {
        UriDispatcher.getInstance().dispatch(this, "xiaoma://shopping");
    }

    public void toShake(View view) {
        if (hbInfo != null) {
            shakeFragment = new ShakeFragment();
            Bundle bundle = new Bundle();
            bundle.putString("hbId", hongBaoId);
            bundle.putString("hbTheme", hbInfo.getHbTheme());
            bundle.putSerializable("hbShare", hbShare);
            shakeFragment.setArguments(bundle);
            shakeFragment.show(getSupportFragmentManager(), "ShakeFragment");
        }
    }

    @Subscribe
    public void onEvent(YaoHongBaoEvent event) {
        refreshData();
    }

    @Subscribe
    public void onEvent(HongBaoCommentEvent event) {
        if (TextUtils.equals(event.hbId, hongBaoId)) {
            refreshData();
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadMore();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (hbInfo == null) {
            return;
        }
        if (shakeFragment != null && shakeFragment.isAdded()) {
            return;
        }
        int status = hbInfo.getStatus();
        if (status == 1) {
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            int medumValue = 19;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                toShake(null);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private NetworkCallback<HongBaoDetail> networkCallback = new NetworkCallback<HongBaoDetail>() {
        @Override
        protected void onSuccess(HongBaoDetail response) {
            if (isRefresh) {
                hbShare = response.getShareInfo();
                hbInfo = response.getHbInfo();
                Picasso.with(HongBaoDetailActivity.this).load(hbInfo.getAvatar()).fit().into(rivAvatar);
                rivAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UriDispatcher.getInstance().dispatch(HongBaoDetailActivity.this, hbInfo.getUserInfoLink());
                    }
                });
                ivVip.setVisibility(hbInfo.isIsV() ? View.VISIBLE : View.GONE);
                tvWhoseHongBao.setText(String.format("%s发的红包", hbInfo.getName()));
                tvBlessing.setText(hbInfo.getMessage());
                tvMoney.setText(String.format("%s元", hbInfo.getMoney()));
                tvHongBaoState.setText(hbInfo.getTotal());
                tvInroContent.setText(hbInfo.getIntro());
                tvRuleContent.setText(hbInfo.getRule());
                if (hbInfo.getMyMoney() != null) {
                    tvMyHongBao.setText(String.format("已摇到%s元", hbInfo.getMyMoney()));
                }

                hbUsers = response.getHbUsers();
                hbUserAdapter.clear();
                hbUserAdapter.addAll(hbUsers.getList());
                hbUserAdapter.notifyDataSetChanged();

                int status = response.getHbInfo().getStatus();
                vf.setDisplayedChild(status - 1);

                commentAdapter.clear();
            }
            HongBaoDetail.HbCommentsBean commentsBean = response.getHbComments();
            tvCommentCount.setText(String.format("评论 (%s)", commentsBean.getTotal()));
            wp = commentsBean.getWp();
            isEnd = commentsBean.isIsEnd();
            ptrComment.setMode(commentsBean.isIsEnd() ? PullToRefreshBase.Mode.DISABLED : PullToRefreshBase.Mode.PULL_FROM_END);

            commentAdapter.addAll(response.getHbComments().getList());
            commentAdapter.notifyDataSetChanged();
            ptrComment.onRefreshComplete();
        }

        @Override
        protected void onFail(int i, String s) {
            ptrComment.onRefreshComplete();
        }
    };
}
