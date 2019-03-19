package com.hongyao.hongbao.view.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.hongyao.hongbao.HongYaoApplication;
import com.hongyao.hongbao.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.xiaoma.common.activity.BaseActivity;
import com.xiaoma.common.fragment.BaseFullScreenDialogFragment;

/**
 * Created by liaolan on 16/5/6.
 */
public class ShareDialogFragment extends BaseFullScreenDialogFragment implements View.OnClickListener {
    private String title;
    private String imageUrl;
    private String webUrl;
    private String desc;

    private static ShareDialogFragment instance;

    public static void show(FragmentManager fm, String title, String imageUrl, String webUrl, String desc) {
        if (instance != null) {
            instance.dismiss();
        }
        instance = new ShareDialogFragment();
        instance.setTitle(title);
        instance.setImageUrl(imageUrl);
        instance.setWebUrl(webUrl);
        instance.setDesc(desc);
        instance.show(fm, ShareDialogFragment.class.getSimpleName());
    }

    public static void dismissDialog() {
        if (instance != null) {
            instance.dismiss();
            instance = null;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_share_view;
    }

    @Override
    protected int getWindowColor() {
        return Color.parseColor("#80000000");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvWeChatFriend = (TextView) view.findViewById(R.id.tv_wechat_friend);
        TextView tvWeChatSpace = (TextView) view.findViewById(R.id.tv_wechat_space);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_dialog_cancel);

        tvWeChatFriend.setOnClickListener(this);
        tvWeChatSpace.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.in_from_bottom);
        getView().startAnimation(animation);
    }

    @Override
    public void dismiss() {
        if (dialog.isShowing()) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.out_to_bottom);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ShareDialogFragment.super.dismiss();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            getView().startAnimation(animation);
        } else {
            super.dismiss();
        }
    }

    private void shareToWXFriend() {
        Picasso.with(getContext()).load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (null != bitmap) {
                    HongYaoApplication.getInstance().shareFriend(bitmap, webUrl, title, desc);
                }
                hideProgress();
                dismiss();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        showProgress();
    }

    private void shareToWXCircle() {
        Picasso.with(getContext()).load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (null != bitmap) {
                    HongYaoApplication.getInstance().shareWX(bitmap, webUrl, title, desc);
                }
                hideProgress();
                dismiss();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        showProgress();
    }

    private void showProgress() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showProgress();
        }
    }

    private void hideProgress() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideProgress();
        }
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wechat_friend:
                shareToWXFriend();
                break;
            case R.id.tv_wechat_space:
                shareToWXCircle();
                break;
            case R.id.tv_dialog_cancel:
                dismiss();
                break;
        }
    }
}
