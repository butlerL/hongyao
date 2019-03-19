package com.hongyao.hongbao.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.WoDeYaoShiResult;

import java.util.ArrayList;

/**
 * Created by wjf on 2016-03-30.
 */
public class YaoShiBuyTypeAdapter extends RecyclerView.Adapter<YaoShiBuyTypeAdapter.BuyTypeViewHolder> {
    private ArrayList<WoDeYaoShiResult.YaoShiBuyType> buyTypes = null;
    private OnRecyclerViewListener onRecyclerViewListener = null;

    public YaoShiBuyTypeAdapter(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    public void setBuyTypes(ArrayList<WoDeYaoShiResult.YaoShiBuyType> buyTypes) {
        this.buyTypes = buyTypes;
        notifyDataSetChanged();
    }

    @Override
    public BuyTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wode_yaoshi, null);
        YaoShiBuyTypeAdapter.BuyTypeViewHolder viewHolder = new YaoShiBuyTypeAdapter.BuyTypeViewHolder(view, onClickBuyListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BuyTypeViewHolder holder, int position) {
        holder.bindViewHolder(buyTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return null == buyTypes ? 0 : buyTypes.size();
    }

    private OnClickBuyListener onClickBuyListener = new OnClickBuyListener() {
        @Override
        public void onClickBuy(String type) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onClickBuy(type);
            }
        }
    };

    /**
     * recyclerView的点击事件回调给UI
     */
    public interface OnRecyclerViewListener {
        public void onClickBuy(String type);
    }

    public class BuyTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvCount = null;
        private TextView tvCountDesc = null;
        private TextView tvBuy = null;

        private OnClickBuyListener onClickBuyListener = null;
        private String type = null;

        public BuyTypeViewHolder(View itemView, OnClickBuyListener onClickBuyListener) {
            super(itemView);
            tvCount = (TextView) itemView.findViewById(R.id.tv_wode_yaoshi_count);
            tvCountDesc = (TextView) itemView.findViewById(R.id.tv_wode_yaoshi_count_desc);
            tvBuy = (TextView) itemView.findViewById(R.id.tv_wode_yaoshi_buy);

            tvBuy.setOnClickListener(this);
            this.onClickBuyListener = onClickBuyListener;
        }

        private void bindView(WoDeYaoShiResult.YaoShiBuyType buyType) {
            tvCount.setText(buyType.getCount());
            tvCountDesc.setText(buyType.getDesc());
            tvBuy.setText(buyType.getPay());

            type = buyType.getType();
        }

        public void bindViewHolder(WoDeYaoShiResult.YaoShiBuyType buyType) {
            bindView(buyType);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_wode_yaoshi_buy:
                    if (null != onClickBuyListener) {
                        onClickBuyListener.onClickBuy(type);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * viewHolder的item点击事件回调给adapter
     */
    public interface OnClickBuyListener {
        public void onClickBuy(String type);
    }
}
