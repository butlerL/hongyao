package com.hongyao.hongbao.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.WoDeHongBaoResult;

import java.util.ArrayList;

/**
 * Created by wjf on 2016-03-28.
 */
public class HongBaoGiveAdapter extends RecyclerView.Adapter<HongBaoGiveAdapter.WoDeHongBaoGiveViewHolder> {
    private ArrayList<WoDeHongBaoResult.HongBaoContent> hongbaos = null;
    private OnRecyclerViewListener onRecyclerViewListener = null;

    public HongBaoGiveAdapter(ArrayList<WoDeHongBaoResult.HongBaoContent> hongbaos) {
        this.hongbaos = hongbaos;
    }

    @Override
    public HongBaoGiveAdapter.WoDeHongBaoGiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wode_hongbao_give, null);
        WoDeHongBaoGiveViewHolder viewHolder = new WoDeHongBaoGiveViewHolder(view, onClickItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HongBaoGiveAdapter.WoDeHongBaoGiveViewHolder holder, int position) {
        holder.bindViewHolder(hongbaos.get(position));
    }

    @Override
    public int getItemCount() {
        return hongbaos == null ? 0 : hongbaos.size();
    }

    public void refreshData(ArrayList<WoDeHongBaoResult.HongBaoContent> hongbaos) {
        this.hongbaos = hongbaos;
        notifyDataSetChanged();
    }

    private OnClickItemListener onClickItemListener = new OnClickItemListener() {
        @Override
        public void onItemClick(int position) {
            onRecyclerViewListener.onClickItem(position);
        }
    };

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    /**
     * adapter回调到UI
     */
    public interface OnRecyclerViewListener {
        public void onClickItem(int position);
    }

    public class WoDeHongBaoGiveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTheme = null;
        private TextView tvTime = null;
        private TextView tvMoney = null;

        private OnClickItemListener onClickItemListener = null;

        public WoDeHongBaoGiveViewHolder(View itemView, OnClickItemListener onClickItemListener) {
            super(itemView);
            tvTheme = (TextView) itemView.findViewById(R.id.tv_wode_hongbao_give_theme);
            tvTime = (TextView) itemView.findViewById(R.id.tv_wode_hongbao_give_time);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_wode_hongbao_give_money);
            itemView.setOnClickListener(this);
            this.onClickItemListener = onClickItemListener;
        }

        private void bindView(WoDeHongBaoResult.HongBaoContent hongBaoContent) {
            String theme = hongBaoContent.getName();
            String time = hongBaoContent.getTime();
            String money = hongBaoContent.getMoney();

            tvTheme.setText(theme);
            tvTime.setText(time);
            tvMoney.setText(money);
        }

        public void bindViewHolder(WoDeHongBaoResult.HongBaoContent hongBaoContent) {
            bindView(hongBaoContent);
        }

        @Override
        public void onClick(View v) {
            if (null != onClickItemListener) {
                onClickItemListener.onItemClick(getPosition());
            }
        }
    }

    /**
     * RecyclerView的item点击回调到adapter
     */
    public interface OnClickItemListener {
        void onItemClick(int position);
    }
}
