package com.hongyao.hongbao.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.UserTimeLineResult;
import com.xiaoma.common.activity.BrowserImageActivity;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.common.widget.NineImageGrid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaolan on 16/3/26.
 */
public class UserTimeLineAdapter extends BaseAdapter
        implements NineImageGrid.OnClickItemListener {
    private Context context;
    private OnFavorListener onFavorListener;
    private List<UserTimeLineResult.TimelineBean.ListBean> timelineBeans;

    public UserTimeLineAdapter(Context context, OnFavorListener onFavorListener) {
        this.context = context;
        this.onFavorListener = onFavorListener;
        timelineBeans = new ArrayList<>();
    }

    public List<UserTimeLineResult.TimelineBean.ListBean> getDatas() {
        return timelineBeans;
    }

    public void addAll(List<UserTimeLineResult.TimelineBean.ListBean> timelineBeans) {
        this.timelineBeans.addAll(timelineBeans);
    }

    public void clear() {
        timelineBeans.clear();
    }

    @Override
    public int getCount() {
        return timelineBeans.size();
    }

    @Override
    public UserTimeLineResult.TimelineBean.ListBean getItem(int position) {
        return timelineBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_time_line, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final UserTimeLineResult.TimelineBean.ListBean item = getItem(position);
        holder.tvTime.setText(item.getTime());
        if (!TextUtils.isEmpty(item.getHongbao().getMoney())) {
            SpannableString spannable = new SpannableString(String.format("%s元", item.getHongbao().getMoney()));
            spannable.setSpan(new AbsoluteSizeSpan(20, true), 0, spannable.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#d41424")), 0, spannable.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvMoney.setText(spannable);
        }
        holder.tvContent.setText(item.getContent());
        holder.nineGrid.setImageUrls(item.getImages());
        holder.nineGrid.setOnClickItemListener(this);
        holder.nineGrid.setVisibility(item.getImages() == null || item.getImages().isEmpty() ? View.GONE : View.VISIBLE);

        int status = item.getHongbao().getStatus();
        if (status == 1) {
            holder.tvYao.setText("马上去摇");
            holder.tvYao.setBackgroundColor(Color.parseColor("#D41424"));
        } else if (status == 2) {
            holder.tvYao.setText("已摇到");
            holder.tvYao.setBackgroundColor(Color.parseColor("#F5A623"));
        } else if (status == 3) {
            holder.tvYao.setText("已抢光");
            holder.tvYao.setBackgroundColor(Color.parseColor("#BBBBBB"));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriDispatcher.getInstance().dispatch(context, item.getLink());
            }
        });

        if (item.getHongbao().getTotal() == 0) {
            holder.tvHbUserCount.setVisibility(View.GONE);
        } else {
            holder.tvHbUserCount.setText("" + item.getHongbao().getTotal());
            holder.tvHbUserCount.setVisibility(View.VISIBLE);
        }

        if (item.getFavorCount() == 0) {
            holder.tvFavorCount.setText("点赞");
            holder.tvFavorCount.setTextColor(Color.parseColor("#60656F"));
            holder.tvFavorCount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hb_ic_unfavored, 0, 0, 0);
            holder.tvFavorCount.setBackgroundResource(R.drawable.hb_bg_unfavored);
        } else {
            holder.tvFavorCount.setText("" + item.getFavorCount());
            if (item.isFavored()) {
                holder.tvFavorCount.setTextColor(Color.parseColor("#F44348"));
                holder.tvFavorCount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hb_ic_favored, 0, 0, 0);
                holder.tvFavorCount.setBackgroundResource(R.drawable.hb_bg_favored);
            } else {
                holder.tvFavorCount.setTextColor(Color.parseColor("#60656F"));
                holder.tvFavorCount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hb_ic_unfavored, 0, 0, 0);
                holder.tvFavorCount.setBackgroundResource(R.drawable.hb_bg_unfavored);
            }
        }

        if (item.isFavored()) {
            holder.tvFavorCount.setOnClickListener(null);
        } else {
            holder.tvFavorCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFavorListener != null) {
                        onFavorListener.onFavor(item.getId());
                    }
                }
            });
        }

        return convertView;
    }

    @Override
    public void onClick(int i, ArrayList<String> urls) {
        Intent intent = new Intent(context, BrowserImageActivity.class);
        intent.putExtra(BrowserImageActivity.POSITION, i);
        intent.putExtra(BrowserImageActivity.URLS, urls);
        context.startActivity(intent);
    }

    private static class Holder {
        TextView tvTime;
        TextView tvMoney;
        TextView tvYao;
        TextView tvContent;
        NineImageGrid nineGrid;
        TextView tvHbUserCount;
        TextView tvFavorCount;
        View rlHongBao;

        public Holder(View itemView) {
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
            tvYao = (TextView) itemView.findViewById(R.id.tv_yao);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            nineGrid = (NineImageGrid) itemView.findViewById(R.id.nine_grid);
            tvHbUserCount = (TextView) itemView.findViewById(R.id.tv_hb_user_count);
            tvFavorCount = (TextView) itemView.findViewById(R.id.tv_favor_count);
            rlHongBao = itemView.findViewById(R.id.rl_hong_bao);
        }
    }

    public interface OnFavorListener {
        void onFavor(String hbId);
    }
}
