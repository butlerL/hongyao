package com.hongyao.hongbao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.HongBaoDetail;
import com.hongyao.hongbao.view.activity.HongBaoDetailActivity;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaolan on 16/3/25.
 */
public class HbUserAdapter extends RecyclerView.Adapter<HbUserAdapter.Holder> {

    private Context context;
    private List<HongBaoDetail.HbUsersBean.ListBean> users;

    public HbUserAdapter(Context context) {
        this.context = context;
        users = new ArrayList<>();
    }

    public void addAll(List<HongBaoDetail.HbUsersBean.ListBean> users) {
        this.users.addAll(users);
    }

    public void clear() {
        users.clear();
    }

    @Override
    public HbUserAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_hong_bao_user, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(HbUserAdapter.Holder holder, int position) {
        HongBaoDetail.HbUsersBean.ListBean user = users.get(position);
        Picasso.with(context).load(user.getAvatar()).fit().into(holder.rivAvatar);
        holder.ivV.setVisibility(user.isIsV() ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof HongBaoDetailActivity) {
                    ((HongBaoDetailActivity)context).lookAllUser(v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        RoundedImageView rivAvatar;
        ImageView ivV;

        public Holder(View itemView) {
            super(itemView);
            rivAvatar = (RoundedImageView) itemView.findViewById(R.id.riv_avatar);
            ivV = (ImageView) itemView.findViewById(R.id.iv_v);
        }
    }
}
