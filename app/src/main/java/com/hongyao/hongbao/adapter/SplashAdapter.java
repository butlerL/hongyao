package com.hongyao.hongbao.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.eventBus.SplashClickEvent;
import com.hongyao.hongbao.model.bean.SplashPageBean;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.xiaoma.common.route.UriDispatcher;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjf on 2016-06-01.
 */
public class SplashAdapter extends PagerAdapter {
    private final String TAG = "ViewPagerAdapter";
    private Context context;
    private List<ImageView> viewList;
    private ViewGroup.LayoutParams lp;


    public SplashAdapter(Context context) {
        this.context = context;
        viewList = new ArrayList<>();

        lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(lp);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(context).load(R.drawable.ic_splash).into(imageView);
//        viewList.add(imageView);
        notifyDataSetChanged();
    }

    public void setPages(final SplashPageBean splashPageBeen) {

        for (final SplashPageBean.GuidePagesBean bean : splashPageBeen.getGuidePages()) {
            ImageView imageView1 = new ImageView(context);
            imageView1.setLayoutParams(lp);
            imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
            String url = bean.getImage();
            Picasso.with(context).load(url).into(imageView1);
            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new SplashClickEvent(bean.getLink()));
//                    try {
//                        UriDispatcher.getInstance().dispatch(context, bean.getLink());
//                    } catch (Exception e) {
//                    }
                }
            });
            viewList.add(imageView1);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView itemView = viewList.get(position);
        container.addView(itemView);
        return itemView;
    }
}
