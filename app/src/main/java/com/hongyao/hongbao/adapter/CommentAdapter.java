package com.hongyao.hongbao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.HongBaoDetail;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaolan on 16/3/24.
 */
public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<HongBaoDetail.HbCommentsBean.CommentBean> comments;

    public CommentAdapter(Context context) {
        this.context = context;
        comments = new ArrayList<>();
    }

    public void addAll(List<HongBaoDetail.HbCommentsBean.CommentBean> comments) {
        this.comments.addAll(comments);
    }

    public void clear() {
        comments.clear();
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public HongBaoDetail.HbCommentsBean.CommentBean getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.rivAvatar = (RoundedImageView) convertView.findViewById(R.id.riv_avatar);
            holder.ivV = (ImageView) convertView.findViewById(R.id.iv_v);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.ivGender = (ImageView) convertView.findViewById(R.id.iv_gender);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HongBaoDetail.HbCommentsBean.CommentBean comment = getItem(position);

        Picasso.with(context).load(comment.getAvatar()).fit().into(holder.rivAvatar);
        holder.ivV.setVisibility(comment.isV() ? View.VISIBLE : View.GONE);
        holder.tvName.setText(comment.getName());
        holder.tvContent.setText(comment.getContent());
        holder.tvTime.setText(comment.getTime());
        holder.ivGender.setImageResource(comment.getGender().equals("1") ? R.drawable.ic_male : R.drawable.ic_female);

        return convertView;
    }

    private class ViewHolder {
        RoundedImageView rivAvatar;
        ImageView ivV;
        TextView tvName;
        TextView tvContent;
        TextView tvTime;
        ImageView ivGender;
    }
}
