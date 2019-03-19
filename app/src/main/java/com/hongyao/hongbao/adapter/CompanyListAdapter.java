package com.hongyao.hongbao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.model.bean.CompanyListResult;
import com.hongyao.hongbao.model.network.NetworkApi;
import com.squareup.picasso.Picasso;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.common.util.KeyboardUtil;
import com.xiaoma.common.util.XMToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liaolan on 16/3/26.
 */
public class CompanyListAdapter extends RecyclerView.Adapter {
    public final static int VIEW_TYPE_SEARCH = 1;
    public final static int VIEW_TYPE_COMPANY = 2;

    private Context context;
    private OnSearchListener onSearchListener;
    private List<CompanyListResult.CompaniesBean.ListBean> companies;

    public CompanyListAdapter(Context context, OnSearchListener onSearchListener) {
        this.context = context;
        this.onSearchListener = onSearchListener;
        companies = new ArrayList<>();
    }

    public void addAll(List<CompanyListResult.CompaniesBean.ListBean> companies) {
        this.companies.addAll(companies);
    }

    public void clear() {
        companies.clear();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_SEARCH;
        } else {
            return VIEW_TYPE_COMPANY;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SEARCH) {
            return new SearchHolder(LayoutInflater.from(context).inflate(R.layout.header_company_list, parent, false), onSearchListener);
        } else if (viewType == VIEW_TYPE_COMPANY) {
            return new CompanyHolder(LayoutInflater.from(context).inflate(R.layout.item_company, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_SEARCH) {

        } else if (viewType == VIEW_TYPE_COMPANY) {
            final CompanyListResult.CompaniesBean.ListBean item = companies.get(position - 1);
            CompanyHolder companyHolder = (CompanyHolder) holder;
            Picasso.with(context).load(item.getAvatar()).fit().into(companyHolder.ivAvatar);
            companyHolder.tvName.setText(item.getName());
            companyHolder.tvDesc.setText(item.getDesc());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UriDispatcher.getInstance().dispatch(context, item.getLink());
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return companies == null ? 1 : companies.size() + 1;
    }

    private static class SearchHolder extends RecyclerView.ViewHolder implements TextWatcher, TextView.OnEditorActionListener {
        EditText etSearch;
        ImageView ivCancel;
        OnSearchListener onSearchListener;

        public SearchHolder(View itemView, OnSearchListener onSearchListener) {
            super(itemView);
            this.onSearchListener = onSearchListener;
            etSearch = (EditText) itemView.findViewById(R.id.et_search);
            ivCancel = (ImageView) itemView.findViewById(R.id.iv_cancel);
            etSearch.addTextChangedListener(this);
            etSearch.setOnEditorActionListener(this);
            ivCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etSearch.setText("");
                }
            });
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            ivCancel.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String key = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(key)) {
                    XMToast.makeText("请输入要查询的公司或利市号", XMToast.LENGTH_LONG).show();
                } else {
                    if (onSearchListener != null) {
                        KeyboardUtil.hide(etSearch);
                        onSearchListener.onSearch(key);
                    }
                }
            }
            return true;
        }
    }

    private static class CompanyHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvName;
        TextView tvDesc;

        public CompanyHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
        }
    }

    public interface OnSearchListener {
        void onSearch(String key);
    }
}
