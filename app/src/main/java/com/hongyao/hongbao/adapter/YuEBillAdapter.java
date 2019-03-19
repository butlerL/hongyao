package com.hongyao.hongbao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.WoDeYuEResult;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * Created by wjf on 2016-03-29.
 */
public class YuEBillAdapter extends RecyclerView.Adapter<YuEBillAdapter.YuEBillViewHolder> {
    private ArrayList<WoDeYuEResult.BillContent> bills = null;

    public YuEBillAdapter(ArrayList<WoDeYuEResult.BillContent> bills) {
        this.bills = bills;
    }

    @Override
    public YuEBillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wode_yue_bill, null);
        YuEBillViewHolder viewHolder = new YuEBillViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(YuEBillViewHolder holder, int position) {
        holder.bindViewHolder(bills.get(position));
    }

    @Override
    public int getItemCount() {
        return null == bills ? 0 : bills.size();
    }

    public void refreshData(ArrayList<WoDeYuEResult.BillContent> bills) {
        this.bills = bills;
        notifyDataSetChanged();
    }

    public class YuEBillViewHolder extends RecyclerView.ViewHolder {
        private Context context = null;
        private TextView tvWeek = null;
        private TextView tvDate = null;
        private TextView tvMoney = null;
        private TextView tvDesc = null;
        private RoundedImageView ivBill = null;

        public YuEBillViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();

            tvWeek = (TextView) itemView.findViewById(R.id.tv_wode_yue_week);
            tvDate = (TextView) itemView.findViewById(R.id.tv_wode_yue_date);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_wode_yue_money);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_wode_yue_desc);
            ivBill = (RoundedImageView) itemView.findViewById(R.id.iv_wode_yue_bill);
        }

        private void bindView(WoDeYuEResult.BillContent billContent) {
            tvWeek.setText(billContent.getWeek());
            tvDate.setText(billContent.getDate());
            tvMoney.setText(billContent.getMoney());
            tvDesc.setText(billContent.getDesc());

            switch (billContent.getType()) {
                case WoDeYuEResult.BillContent.BILL_TYPE_BUY_MALL:
                    ivBill.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_yu_e_bill_store));
                    break;
                case WoDeYuEResult.BillContent.BILL_TYPE_GIVE_HONG_BAO:
                    ivBill.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_yu_e_bill_hongbao));
                    break;
                case WoDeYuEResult.BillContent.BILL_TYPE_BUY_VIP:
                    ivBill.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_yu_e_bill_vip));
                    break;
                case WoDeYuEResult.BillContent.BILL_TYPE_BUY_YAO_SHI:
                    ivBill.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_yu_e_bill_yaoshi));
                    break;
                case WoDeYuEResult.BillContent.BILL_TYPE_BUY_STORE:
                    ivBill.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_yu_e_bill_store));
                    break;
                default:
                    break;
            }
        }

        public void bindViewHolder(WoDeYuEResult.BillContent billContent) {
            bindView(billContent);
        }
    }
}
