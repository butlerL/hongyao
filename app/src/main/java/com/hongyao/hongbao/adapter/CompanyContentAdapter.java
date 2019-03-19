package com.hongyao.hongbao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.CompanyContentResult;
import com.squareup.picasso.Picasso;
import com.xiaoma.common.route.UriDispatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaolan on 16/3/26.
 */
public class CompanyContentAdapter extends BaseAdapter {
    private Context context;
    private List<CompanyContentResult.ContentsBean.ListBean> contents;

    public CompanyContentAdapter(Context context) {
        this.context = context;
        contents = new ArrayList<>();
    }

    public void addAll(List<CompanyContentResult.ContentsBean.ListBean> companies) {
        this.contents.addAll(companies);
    }

    public void clear() {
        contents.clear();
    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public CompanyContentResult.ContentsBean.ListBean getItem(int position) {
        return contents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_compay_content, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final CompanyContentResult.ContentsBean.ListBean item = getItem(position);
        Picasso.with(context).load(item.getImage()).fit().into(holder.ivPic);
        holder.tvName.setText(item.getTitle());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriDispatcher.getInstance().dispatch(context, item.getLink());
            }
        });
        return convertView;
    }

    private static class Holder {
        ImageView ivPic;
        TextView tvName;

        public Holder(View itemView) {
            ivPic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
