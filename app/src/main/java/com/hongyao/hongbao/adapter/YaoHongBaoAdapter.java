package com.hongyao.hongbao.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.hongyao.hongbao.model.bean.TimeLineResult;
import com.hongyao.hongbao.util.CenteredImageSpan;
import com.squareup.picasso.Picasso;
import com.xiaoma.common.activity.BrowserImageActivity;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.common.util.ScreenUtils;
import com.xiaoma.common.widget.NineImageGrid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaolan on 16/3/23.
 */
public class YaoHongBaoAdapter extends BaseAdapter implements NineImageGrid.OnClickItemListener {

    private Context context;
    private OnFavorListener onFavorListener;
    private List<TimeLineResult.TimelineBean.ListBean> datas;

    public YaoHongBaoAdapter(Context context, OnFavorListener onFavorListener) {
        this.context = context;
        this.onFavorListener = onFavorListener;
        datas = new ArrayList<>();
    }

    public List<TimeLineResult.TimelineBean.ListBean> getDatas() {
        return datas;
    }

    public void addAll(List<TimeLineResult.TimelineBean.ListBean> datas) {
        this.datas.addAll(datas);
    }

    public void clear() {
        this.datas.clear();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public TimeLineResult.TimelineBean.ListBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_yao_hong_bao, parent, false);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.ivAvatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.ivV = (ImageView) convertView.findViewById(R.id.iv_v);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.ivSex = (ImageView) convertView.findViewById(R.id.iv_sex);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
            holder.tvYao = (TextView) convertView.findViewById(R.id.tv_yao);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.nineGrid = (NineImageGrid) convertView.findViewById(R.id.nine_grid);
            holder.rlHongBao = convertView.findViewById(R.id.rl_hong_bao);
            holder.tvHbUserCount = (TextView) convertView.findViewById(R.id.tv_hb_user_count);
            holder.tvFavorCount = (TextView) convertView.findViewById(R.id.tv_favor_count);
            holder.vDividerSecond = convertView.findViewById(R.id.v_divider_second);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final TimeLineResult.TimelineBean.ListBean item = getItem(position);
        Picasso.with(context).load(item.getAvatar()).fit().into(holder.ivAvatar);
        holder.tvName.setText(item.getName());
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

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriDispatcher.getInstance().dispatch(context, item.getLink());
            }
        });

        holder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriDispatcher.getInstance().dispatch(context, item.getUserLink());
            }
        });

        holder.rlHongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriDispatcher.getInstance().dispatch(context, item.getHongbao().getLink());
            }
        });
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

        int gender = item.getGender();
        int resGender[] = {R.drawable.ic_male, R.drawable.ic_female, R.drawable.ic_lishi};
        holder.ivSex.setImageResource(resGender[gender - 1]);

        holder.ivV.setVisibility(item.isIsV() ? View.VISIBLE : View.GONE);

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
                    if (onFavorListener !=  null) {
                        onFavorListener.onFavor(item.getId());
                    }
                }
            });
        }

        holder.vDividerSecond.setVisibility(position == getCount() - 1 ? View.GONE : View.VISIBLE);

        return convertView;
    }

    @Override
    public void onClick(int i, ArrayList<String> urls) {
        Intent intent = new Intent(context, BrowserImageActivity.class);
        intent.putExtra(BrowserImageActivity.POSITION, i);
        intent.putExtra(BrowserImageActivity.URLS, urls);
        context.startActivity(intent);
    }

    public static class ViewHolder {
        ImageView ivAvatar;
        ImageView ivV;
        TextView tvName;
        ImageView ivSex;
        TextView tvTime;
        TextView tvMoney;
        TextView tvYao;
        TextView tvContent;
        NineImageGrid nineGrid;
        TextView tvHbUserCount;
        TextView tvFavorCount;
        View rlHongBao;
        View vDividerSecond;
    }

    public interface OnFavorListener {
        void onFavor(String hbId);
    }
}
