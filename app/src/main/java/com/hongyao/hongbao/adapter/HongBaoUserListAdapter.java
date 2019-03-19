package com.hongyao.hongbao.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.HongBaoUserListResult;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xiaoma.common.route.UriDispatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaolan on 16/3/27.
 */
public class HongBaoUserListAdapter extends BaseAdapter {
    private Context context;
    private List<HongBaoUserListResult.HbUsersBean.ListBean> users;
    private boolean isGotten = false;

    public HongBaoUserListAdapter(Context context) {
        this.context = context;
        users = new ArrayList<>();
    }

    public void setGotten() {
        isGotten = true;
    }

    public void addAll(List<HongBaoUserListResult.HbUsersBean.ListBean> users) {
        this.users.addAll(users);
    }

    public void clear() {
        users.clear();
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public HongBaoUserListResult.HbUsersBean.ListBean getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hong_bao_user_list, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final HongBaoUserListResult.HbUsersBean.ListBean item = getItem(position);
        Picasso.with(context).load(item.getAvatar()).fit().into(holder.rivAvatar);
        holder.ivV.setVisibility(item.isIsV() ? View.VISIBLE : View.GONE);
        holder.tvName.setText(item.getName());
        holder.tvTime.setText(item.getTime());
        holder.tvMoney.setText(item.getMoney());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGotten) {
                    UriDispatcher.getInstance().dispatch(context, item.getUserInfoLink());
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setMessage("抢到红包后才可以查看发烧友的资料卡，赶紧去摇红包再认识他们吧")
                            .setPositiveButton("确定", null)
                            .create();
                    dialog.show();
                }
            }
        });
        holder.tvIsMax.setVisibility(item.isMaxMoney() ? View.VISIBLE : View.GONE);
        return convertView;
    }

    private static class Holder {
        RoundedImageView rivAvatar;
        ImageView ivV;
        TextView tvName;
        TextView tvTime;
        TextView tvMoney;
        TextView tvIsMax;

        public Holder(View itemView) {
            rivAvatar = (RoundedImageView) itemView.findViewById(R.id.riv_avatar);
            ivV = (ImageView) itemView.findViewById(R.id.iv_v);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
            tvIsMax = (TextView) itemView.findViewById(R.id.tv_is_max);
        }
    }
}
