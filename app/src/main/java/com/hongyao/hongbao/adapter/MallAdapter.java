package com.hongyao.hongbao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.MallResult;
import com.hongyao.hongbao.listener.OnClickRecyclerViewItemListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaolan on 16/3/25.
 */
public class MallAdapter extends RecyclerView.Adapter<MallAdapter.Holder> {
    private Context context;
    private List<MallResult.ModulesBean> malls;
    private OnClickRecyclerViewItemListener onItemClickListener;

    public MallAdapter(Context context) {
        this.context = context;
        malls = new ArrayList<>();
    }

    public void setOnItemClickListener(OnClickRecyclerViewItemListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addAll(List<MallResult.ModulesBean> malls) {
        this.malls.addAll(malls);
    }

    public void clear() {
        this.malls.clear();
    }

    public MallResult.ModulesBean getItem(int position) {
        return malls.get(position);
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_mall, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        MallResult.ModulesBean mall = malls.get(position);
        Picasso.with(context).load(mall.getIcon()).fit().into(holder.rivPic);
        holder.tvName.setText(mall.getTitle());
        holder.tvDesc.setText(mall.getDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onLongClick(position);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return malls.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        RoundedImageView rivPic;
        TextView tvName;
        TextView tvDesc;

        public Holder(View itemView) {
            super(itemView);
            rivPic = (RoundedImageView) itemView.findViewById(R.id.riv_pic);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
        }
    }

}
