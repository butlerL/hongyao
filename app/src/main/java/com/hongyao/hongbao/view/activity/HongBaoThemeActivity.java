package com.hongyao.hongbao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.hongyao.hongbao.R;
import com.hongyao.hongbao.util.HongBaoThemeUtil;
import com.squareup.picasso.Picasso;
import com.xiaoma.common.activity.BaseActivity;

/**
 * Created by liaolan on 16/3/28.
 */
public class HongBaoThemeActivity extends BaseActivity {
    public static final String THEME = "theme";

    private ViewPager viewPager;
    private View selectedView;
    private ImageView ivPreview;
    private HongBaoThemeUtil.Bean selectedTheme;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hong_bao_theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("红包主题");
        ivPreview = (ImageView) findViewById(R.id.iv_preview);
        ivPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        });
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                final int pageId = position;
                View view = LayoutInflater.from(HongBaoThemeActivity.this).inflate(R.layout.hong_bao_theme_grid, null);
                StaggeredGridView gridView = (StaggeredGridView) view.findViewById(R.id.staggered_grid_view);
                gridView.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return pageId == 0 ? 4 : 2;
                    }

                    @Override
                    public HongBaoThemeUtil.Bean getItem(int position) {
                        return pageId == 0 ? HongBaoThemeUtil.beans[position] : HongBaoThemeUtil.beans[4 + position];
                    }

                    @Override
                    public long getItemId(int position) {
                        return 0;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        convertView = LayoutInflater.from(HongBaoThemeActivity.this).inflate(R.layout.item_hong_bao_theme, parent, false);
                        ImageView ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
                        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);

                        final HongBaoThemeUtil.Bean item = getItem(position);
                        Picasso.with(HongBaoThemeActivity.this).load(item.drawableNormal).into(ivPic);
                        tvName.setText(item.name);
                        ivPic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (selectedView != null) {
                                    selectedView.setBackgroundDrawable(null);
                                    if (selectedView == v) {
                                        selectedTheme = null;
                                        selectedView = null;
                                        return;
                                    }
                                }
                                selectedTheme = item;
                                selectedView = v;
                                selectedView.setBackgroundResource(R.drawable.bg_red_border);
                            }
                        });
                        return convertView;
                    }
                });
                container.addView(view);
                return view;
            }
        });
    }

    public void confirm(View view) {
        if (selectedTheme != null) {
            Intent intent = new Intent();
            intent.putExtra(THEME, selectedTheme);
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }
        onBackPressed();
    }

    public void preView(View view) {
        if (selectedTheme == null) {
            new AlertDialog.Builder(this).setTitle("请选择主题包").setPositiveButton("确定", null).create().show();
        } else {
            Picasso.with(this).load(selectedTheme.drawablePreview).into(ivPreview);
            ivPreview.setVisibility(View.VISIBLE);
        }
    }
}
