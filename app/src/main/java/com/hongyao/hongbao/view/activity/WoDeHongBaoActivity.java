package com.hongyao.hongbao.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.adapter.HongBaoPagerAdapter;
import com.hongyao.hongbao.view.fragment.HongBaoGetFragment;
import com.hongyao.hongbao.view.fragment.HongBaoGiveFragment;
import com.xiaoma.common.activity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by wjf on 16/3/25.
 */
public class WoDeHongBaoActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private final String TAG = "WoDeHongBaoActivity";

    private TextView tvHongbaoGet = null;
    private TextView tvHongbaoGive = null;
    private View vHongbaoGet = null;
    private View vHongbaoGive = null;
    private ViewPager viewPager = null;

    private boolean isFirstTime = true;
    private ArrayList<Fragment> fragments = null;
    private HongBaoPagerAdapter hongBaoPagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvHongbaoGet = (TextView) findViewById(R.id.tv_wode_hongbao_get);
        tvHongbaoGive = (TextView) findViewById(R.id.tv_wode_hongbao_give);
        vHongbaoGet = (View) findViewById(R.id.v_wode_hongbao_get);
        vHongbaoGive = (View) findViewById(R.id.v_wode_hongbao_give);

        tvHongbaoGet.setOnClickListener(this);
        tvHongbaoGive.setOnClickListener(this);

        setTitle("我的红包");
        setTabStatus(0);
        initViewPager();
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.vp_wode_hongbao);
        fragments = new ArrayList<>();
        HongBaoGetFragment hongBaoGetFragment = new HongBaoGetFragment();
        HongBaoGiveFragment hongBaoGiveFragment = new HongBaoGiveFragment();
        fragments.add(hongBaoGetFragment);
        fragments.add(hongBaoGiveFragment);

        hongBaoPagerAdapter = new HongBaoPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(hongBaoPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wo_de_hong_bao;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wode_hongbao_get:
                setTabStatus(0);
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_wode_hongbao_give:
                setTabStatus(1);
                viewPager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    private void setTabStatus(int position) {

        switch (position) {
            case 0:
                tvHongbaoGet.setTextColor(getResources().getColor(R.color.color_tab_hongbao_pressed));
                tvHongbaoGive.setTextColor(getResources().getColor(R.color.color_tab_hongbao_normal));

                vHongbaoGet.setBackgroundResource(R.color.color_tab_hongbao_pressed);
                vHongbaoGive.setBackgroundResource(R.color.color_wode_white);
                break;
            case 1:
                tvHongbaoGet.setTextColor(getResources().getColor(R.color.color_tab_hongbao_normal));
                tvHongbaoGive.setTextColor(getResources().getColor(R.color.color_tab_hongbao_pressed));

                vHongbaoGet.setBackgroundResource(R.color.color_wode_white);
                vHongbaoGive.setBackgroundResource(R.color.color_tab_hongbao_pressed);

                if (isFirstTime) {
                    notifyHongBaoGive.doNetwork();
                    isFirstTime = false;
                }
                break;
        }
    }

    private NotifyHongBaoGive notifyHongBaoGive = null;

    public void setNotifyHongBaoGive(NotifyHongBaoGive notifyHongBaoGive) {
        this.notifyHongBaoGive = notifyHongBaoGive;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTabStatus(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface NotifyHongBaoGive {
        public void doNetwork();
    }
}
