package com.hongyao.hongbao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.WoDeHongBaoResult;
import com.hongyao.hongbao.model.bean.WoDeResult;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by wjf on 2016-03-26.
 */
public class HongBaoGetAdapter extends RecyclerView.Adapter<HongBaoGetAdapter.WoDeHongBaoGetViewHolder> {
    private ArrayList<WoDeHongBaoResult.HongBaoContent> hongbaos = null;
    private Context context = null;
    private OnRecyclerViewListener onRecyclerViewListener = null;

    public HongBaoGetAdapter(Context context, ArrayList<WoDeHongBaoResult.HongBaoContent> hongbaos) {
        this.hongbaos = hongbaos;
        this.context = context;
    }

    @Override
    public HongBaoGetAdapter.WoDeHongBaoGetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wode_hongbao_get, null);
        WoDeHongBaoGetViewHolder viewHolder = new WoDeHongBaoGetViewHolder(context, view, onClickItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HongBaoGetAdapter.WoDeHongBaoGetViewHolder viewHolder, int position) {
        viewHolder.bindViewHolder(hongbaos.get(position));
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
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onClickItem(position);
            }
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

    public class WoDeHongBaoGetViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private RoundedImageView ivAvatar = null;
        private ImageView ivIsV = null;
        private TextView tvName = null;
        private TextView tvTime = null;
        private TextView tvMoney = null;

        private OnClickItemListener onClickItemListener = null;
        private Context context = null;

        public WoDeHongBaoGetViewHolder(Context context, View itemView, OnClickItemListener onClickItemListener) {
            super(itemView);

            ivAvatar = (RoundedImageView) itemView.findViewById(R.id.iv_wode_hongbao_get_avatar);
            ivIsV = (ImageView) itemView.findViewById(R.id.iv_wode_hongbao_get_isv);
            tvName = (TextView) itemView.findViewById(R.id.tv_wode_hongbao_get_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_wode_hongbao_get_time);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_wode_hongbao_get_money);
            itemView.setOnClickListener(this);
            this.onClickItemListener = onClickItemListener;
            this.context = context;
        }

        private void bindView(WoDeHongBaoResult.HongBaoContent hongBaoContent) {
            String urlAvatar = hongBaoContent.getAvatar();
            Picasso.with(context).load(urlAvatar).fit().into(ivAvatar);

            boolean isV = hongBaoContent.isV();
            String name = hongBaoContent.getName();
            String gender = hongBaoContent.getGender();
            String time = hongBaoContent.getTime();
            String money = hongBaoContent.getMoney();

            if (isV) {
                ivIsV.setVisibility(View.VISIBLE);
            } else {
                ivIsV.setVisibility(View.GONE);
            }
            tvName.setText(name);
            tvTime.setText(time);
            tvMoney.setText(money);

            if (gender.equals(WoDeResult.WO_DE_GENDER_MALE)) {
                tvName.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.ic_male), null);
            } else if (gender.equals(WoDeResult.WO_DE_GENDER_FEMALE)) {
                tvName.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.ic_female), null);
            } else {
                tvName.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.ic_lishi), null);
            }
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
