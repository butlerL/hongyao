package com.hongyao.hongbao.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hongyao.hongbao.R;
import com.hongyao.hongbao.adapter.UserTimeLineAdapter;
import com.hongyao.hongbao.model.bean.HongBaoFavorBean;
import com.hongyao.hongbao.model.bean.UserTimeLineResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xiaoma.common.activity.BaseActivity;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.common.util.XMToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liaolan on 16/3/26.
 */
public class UserTimeLineActivity extends BaseActivity
        implements PullToRefreshBase.OnRefreshListener2<ListView>,UserTimeLineAdapter.OnFavorListener {
    private String userId;
    private String type;
    private String wp;
    private boolean isEnd;
    private PullToRefreshListView ptrListView;
    private UserTimeLineAdapter adapter;
    private boolean isRefresh;
    private RoundedImageView rivAvatar;
    private View vV;
    private ImageView ivGender;
    private TextView tvName;
    private TextView tvMoney;
    private TextView tvCount;

    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_time_line;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = getQueryParameter("userId");
        type = getQueryParameter("type");

        ptrListView = (PullToRefreshListView) findViewById(R.id.ptr_list_view);
        ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
        ptrListView.setOnRefreshListener(this);
        ListView listView = ptrListView.getRefreshableView();
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        View header = LayoutInflater.from(this).inflate(R.layout.header_user_time_line, listView, false);
        listView.addHeaderView(header, null, false);
        rivAvatar = (RoundedImageView) findViewById(R.id.riv_avatar);
        vV = findViewById(R.id.iv_v);
        ivGender = (ImageView) findViewById(R.id.iv_gender);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        tvCount = (TextView) findViewById(R.id.tv_count);
        listView.setDivider(null);
        adapter = new UserTimeLineAdapter(this, this);
        listView.setAdapter(adapter);
        refreshData();
    }

    private void refreshData() {
        isRefresh = true;
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("type", type);
        networkRequest.get(NetworkApi.URL_USER_TIME_LINE,
                params,
                true,
                networkCallback
        );
    }

    private void loadMore() {
        if (isEnd || TextUtils.isEmpty(wp)) {
            return;
        }
        isRefresh = false;
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("type", type);
        params.put("wp", wp);
        networkRequest.get(NetworkApi.URL_USER_TIME_LINE + "/more",
                params,
                true,
                networkCallback
        );
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        refreshData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (!isEnd) {
            loadMore();
        } else {
            ptrListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrListView.onRefreshComplete();
                }
            }, 1000);
        }
    }

    private NetworkCallback<UserTimeLineResult> networkCallback = new NetworkCallback<UserTimeLineResult>() {
        @Override
        protected void onSuccess(final UserTimeLineResult response) {
            if (isRefresh) {
                adapter.clear();
                Picasso.with(UserTimeLineActivity.this).load(response.getAvatar()).fit().into(rivAvatar);
                vV.setVisibility(response.isIsV() ? View.VISIBLE : View.GONE);
                tvName.setText(response.getName());
                tvMoney.setText(String.format("%s元", response.getTotalMoney()));
                tvCount.setText(String.format("已有%s人抢到", response.getTotalCount()));
                setTitle(String.format("%s的红包", response.getName()));
                int gender = response.getGender();
                int resGender[] = {R.drawable.ic_male, R.drawable.ic_female, R.drawable.ic_lishi};
                if (gender - 1 > 0 && gender - 1 < resGender.length) {
                    ivGender.setImageResource(resGender[gender - 1]);
                    ivGender.setVisibility(View.VISIBLE);
                } else {
                    ivGender.setVisibility(View.GONE);
                }
            }
            wp = response.getTimeline().getWp();
            isEnd = response.getTimeline().isIsEnd();
            adapter.addAll(response.getTimeline().getList());
            adapter.notifyDataSetChanged();
            ptrListView.onRefreshComplete();

            rivAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UriDispatcher.getInstance().dispatch(UserTimeLineActivity.this, response.getUserInfoLink());
                }
            });
        }

        @Override
        protected void onFail(int i, String s) {

        }
    };

    @Override
    public void onFavor(final String hbId) {
        Map<String, String> params = new HashMap<>();
        params.put("hbId", hbId);
        showProgress();
        new NetworkRequest().get(NetworkApi.URL_HONGBAO_FAVOR,
                params,
                true,
                new NetworkCallback<HongBaoFavorBean>() {

                    @Override
                    protected void onSuccess(HongBaoFavorBean hongBaoFavorBean) {
                        if (!TextUtils.isEmpty(hongBaoFavorBean.getMsg())) {
                            XMToast.makeText(hongBaoFavorBean.getMsg(), XMToast.LENGTH_LONG).show();
                        }

                        if (adapter == null || ptrListView == null) {
                            hideProgress();
                            return;
                        }
                        List<UserTimeLineResult.TimelineBean.ListBean> datas = adapter.getDatas();
                        if (datas != null && !datas.isEmpty()) {
                            for (UserTimeLineResult.TimelineBean.ListBean item : datas) {
                                if (hbId.equals(item.getId())) {
                                    item.setFavored(true);
                                    item.setFavorCount(hongBaoFavorBean.getFavorCount());
                                    break;
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                        hideProgress();
                    }

                    @Override
                    protected void onFail(int i, String s) {
                        hideProgress();
                    }
                });
    }
}
