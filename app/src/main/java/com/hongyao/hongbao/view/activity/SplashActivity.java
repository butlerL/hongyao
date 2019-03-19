package com.hongyao.hongbao.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hongyao.hongbao.R;
import com.hongyao.hongbao.adapter.SplashAdapter;
import com.hongyao.hongbao.eventBus.SplashClickEvent;
import com.hongyao.hongbao.iview.ISplashView;
import com.hongyao.hongbao.model.bean.SplashPageBean;
import com.hongyao.hongbao.presenter.SplashPresenter;
import com.viewpagerindicator.CirclePageIndicator;
import com.xiaoma.common.util.DeviceInfoUtil;
import com.xiaoma.common.util.Preferences;

import org.apmem.tools.layouts.FlowLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by liaolan on 16/3/27.
 */
public class SplashActivity extends Activity implements ISplashView {

    private FrameLayout flAds;
    private ViewPager viewPager;
    private CirclePageIndicator indicator;
    private SplashAdapter adapter;
    //    private SplashPresenter presenter;
    private TextView tvIgnore;
    private FrameLayout flSplashImage;
    private TextView tvVersionName;

    private CountDownTimer countDownTimer;
    private boolean isFirstEnter = true;
    private boolean countDownTimerCanceled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        countDownTimer = new CountDownTimer(5 * 1000, 1 * 1000) {
            public void onTick(long millisUntilFinished) {
                tvIgnore.setText(String.valueOf(millisUntilFinished / 1000 - 1) + "秒");
            }

            public void onFinish() {
                delayToMain(0);
            }
        };

        initView();
        EventBus.getDefault().register(this);
    }

    private void delayToMain(int time) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (countDownTimerCanceled) {
                    return;
                }
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, time);
    }

    private void initView() {
        flAds = (FrameLayout) findViewById(R.id.fl_ads);
        flAds.setVisibility(View.GONE);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setOnTouchListener(onTouchListener);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setOnPageChangeListener(pageChangeListener);
        indicator.setFillColor(Color.WHITE);

        adapter = new SplashAdapter(this);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        indicator.setCurrentItem(0);
        indicator.setVisibility(View.GONE);

        tvIgnore = (TextView) findViewById(R.id.tv_splash_ignore);
        tvIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelCountDown();
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
//        presenter = new SplashPresenter(this);

        flSplashImage = (FrameLayout) findViewById(R.id.fl_splash_image);
        flSplashImage.setVisibility(View.VISIBLE);
        tvVersionName = (TextView) findViewById(R.id.tv_splash_version_name);
        tvVersionName.setText("V" + DeviceInfoUtil.getAppVersionName(this));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                presenter.loadData();
                showAds();
            }
        }, 2000);
    }

    private void showAds() {
        String gson = Preferences.getString("splash_ads");
        if (TextUtils.isEmpty(gson)) {
            delayToMain(0);
        } else {
            SplashPageBean splashPageBean = new Gson().fromJson(gson, SplashPageBean.class);

            if (!splashPageBean.getGuidePages().isEmpty()) {
                if (splashPageBean.getGuidePages().size() > 1) {
                    indicator.setVisibility(View.VISIBLE);
                } else {
                    indicator.setVisibility(View.GONE);
                }
                adapter.setPages(splashPageBean);
                flSplashImage.setVisibility(View.GONE);
                flAds.setVisibility(View.VISIBLE);
                Animation animationShow = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.push_left_in);
                Animation animationHide = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.push_left_out);
                flSplashImage.startAnimation(animationHide);
                flAds.startAnimation(animationShow);
            } else {
                indicator.setVisibility(View.GONE);
            }
            if (adapter.getCount() == 0) {
                delayToMain(0);
            } else {
                countDownTimer.start();
            }
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == adapter.getCount() - 1) {
                tvIgnore.setText("进入");
            } else {
                tvIgnore.setText("跳过");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (isFirstEnter) {
                isFirstEnter = false;
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP:
                        cancelCountDown();
                        tvIgnore.setText("跳过");
                        break;
                }
            }
            return false;
        }
    };

    @Override
    public void onLoadSuccess(Object response, boolean b) {
        SplashPageBean splashPageBean = new Gson().fromJson(new Gson().toJson(response), SplashPageBean.class);
        if (!splashPageBean.getGuidePages().isEmpty()) {
            indicator.setVisibility(View.VISIBLE);
            adapter.setPages(splashPageBean);
        } else {
            indicator.setVisibility(View.GONE);
        }
        if (adapter.getCount() == 0) {
            delayToMain(0);
        } else {
            countDownTimer.start();
        }
    }

    @Override
    public void onError(int i, String s) {
        Toast.makeText(this, i + s, Toast.LENGTH_SHORT).show();
        if (adapter.getCount() == 0) {
            delayToMain(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(SplashClickEvent event) {
        cancelCountDown();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("splash_ad_link", event.link);
        startActivity(intent);
        finish();
    }

    private void cancelCountDown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimerCanceled = true;
    }
}
