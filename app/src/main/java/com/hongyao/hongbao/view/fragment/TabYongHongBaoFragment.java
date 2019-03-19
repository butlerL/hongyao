package com.hongyao.hongbao.view.fragment;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.adapter.MallAdapter;
import com.hongyao.hongbao.listener.OnClickRecyclerViewItemListener;
import com.hongyao.hongbao.model.bean.MallResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.xiaoma.common.fragment.BaseFragment;
import com.xiaoma.common.network.HttpConnection;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.common.util.ScreenUtils;

/**
 * Created by liaolan on 16/3/23.
 */
public class TabYongHongBaoFragment extends BaseFragment
        implements OnClickRecyclerViewItemListener {

    private TextView tvBalance;
    private TextView tvHongBao;
    private RecyclerView rvMall;
    private MallAdapter adapter;
    private int loadCount = 0;
    private NetworkRequest networkRequest = new NetworkRequest();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yong_hong_bao;
    }

    @Override
    protected void initView(View rootView) {
        rootView.findViewById(R.id.iv_back).setVisibility(View.GONE);
        ((TextView) rootView.findViewById(R.id.title)).setText("用红包");
        tvBalance = (TextView) rootView.findViewById(R.id.tv_balance);
        tvHongBao = (TextView) rootView.findViewById(R.id.tv_yong_hongbao_wode);
        tvHongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(HttpConnection.getInstance().getSign())) {
                    UriDispatcher.getInstance().dispatch(getActivity(), "xiaoma://myHongBao");
                } else {
                    UriDispatcher.getInstance().dispatch(getActivity(), "xiaoma://login");
                }
            }
        });
        rvMall = (RecyclerView) rootView.findViewById(R.id.rv_mall);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        rvMall.setLayoutManager(layoutManager);
        rvMall.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                int row = position / 4;
                int verticalSpace = ScreenUtils.dp2px(14);

                if (row == 0) {
                    outRect.top = 0;
                } else {
                    outRect.top = verticalSpace;
                }

                outRect.left = 0;
                outRect.right = 0;
                outRect.bottom = 0;
            }
        });
        adapter = new MallAdapter(getContext());
        adapter.setOnItemClickListener(this);
        rvMall.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        showProgress();
        networkRequest.get(NetworkApi.URL_SHOPPING, null, true, networkCallback);
    }

    @Override
    public void onClick(int position) {
        MallResult.ModulesBean mall = adapter.getItem(position);
        UriDispatcher.getInstance().dispatch(getContext(), mall.getLink());
    }

    @Override
    public void onLongClick(int position) {

    }

    private NetworkCallback<MallResult> networkCallback = new NetworkCallback<MallResult>() {
        @Override
        protected void onSuccess(MallResult response) {
            tvBalance.setText(response.getBalance());
            if (TabYongHongBaoFragment.this.loadCount == 0) {
                adapter.addAll(response.getModules());
                adapter.notifyDataSetChanged();
                TabYongHongBaoFragment.this.loadCount++;
            }
            hideProgress();
        }

        @Override
        protected void onFail(int i, String s) {
            hideProgress();
        }
    };
}
