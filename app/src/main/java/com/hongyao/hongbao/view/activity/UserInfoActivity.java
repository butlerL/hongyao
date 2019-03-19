package com.hongyao.hongbao.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.hongyao.hongbao.R;
import com.hongyao.hongbao.iview.IUserInfoView;
import com.hongyao.hongbao.model.bean.UserInfoBean;
import com.hongyao.hongbao.presenter.UserInfoPresenter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.tencent.TIMAddFriendRequest;
import com.xiaoma.common.activity.BrowserImageActivity;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.im.IMManager;
import com.xiaoma.im.activity.AddFriendInfoActivity;
import com.xiaoma.im.activity.IMBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slumbersoul on 16/3/26.
 */
public class UserInfoActivity extends IMBaseActivity<IUserInfoView, UserInfoPresenter>
        implements IUserInfoView {

    private String identifier;
    private String nickName;
    private RoundedImageView rivAvatar;
    private ImageView ivVip;
    private ImageView gender;
    private TextView uname;
    private TextView hyAccount;
    private TextView hbCount;
    private RelativeLayout hbLine;

    private SwitchCompat tbBlack;
    private ViewFlipper vf;
    private TextView tvSend;
    private TextView tvDelete;
    private TextView tvAdd;
    private RelativeLayout rlBlackList;
    private boolean toAdd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("详细信息");

        identifier = getQueryParameter("identifier");
        if (TextUtils.isEmpty(identifier)) {
            onBackPressed();
            return;
        }
        rivAvatar = (RoundedImageView) findViewById(R.id.riv_avatar);
        ivVip = (ImageView) findViewById(R.id.iv_vip);
        uname = (TextView) findViewById(R.id.tv_name);
        gender = (ImageView) findViewById(R.id.iv_sex);
        hyAccount = (TextView) findViewById(R.id.tv_chatid);
        hbCount = (TextView) findViewById(R.id.tv_count);
        hbLine = (RelativeLayout) findViewById(R.id.rl_hong_bao);

        tbBlack = (SwitchCompat) findViewById(R.id.tb_set_black);
        tvSend = (TextView) findViewById(R.id.tv_send_msg);
        tvDelete = (TextView) findViewById(R.id.tv_delete_friend);
        rlBlackList = (RelativeLayout) findViewById(R.id.rl_friend_info_black_list);
        tvAdd = (TextView) findViewById(R.id.tv_add);
        vf = (ViewFlipper) findViewById(R.id.vf);
        vf.setVisibility(View.GONE);

        if (identifier.equals(IMManager.getId())) {
            rlBlackList.setVisibility(View.GONE);
            tvAdd.setVisibility(View.GONE);
            tvSend.setVisibility(View.GONE);
            tvDelete.setVisibility(View.GONE);
        } else {
            rlBlackList.setVisibility(View.VISIBLE);
            tvAdd.setVisibility(View.VISIBLE);
            tvSend.setVisibility(View.VISIBLE);
            tvDelete.setVisibility(View.VISIBLE);
        }

        tvDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Dialog alertDialog = new AlertDialog.Builder(UserInfoActivity.this).
                        setTitle("确定删除？").
                        setNegativeButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                List<TIMAddFriendRequest> list = new ArrayList<TIMAddFriendRequest>();
                                TIMAddFriendRequest req = new TIMAddFriendRequest();
                                req.setIdentifier(identifier);
                                list.add(req);
                                presenter.deleteFriend(identifier);
                            }
                        }).
                        setPositiveButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).
                        create();
                alertDialog.show();
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriDispatcher.getInstance().dispatch(UserInfoActivity.this,
                        String.format("xiaoma://session?sessionId=%s&type=c2c", identifier));
                finish();
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbBlack.isChecked()) {
                    toAdd = true;
                    ignoreCheck = true;
                    presenter.removeBlackList(identifier);
                } else {
                    Intent intent = new Intent(UserInfoActivity.this, AddFriendInfoActivity.class);
                    intent.putExtra("userID", identifier);
                    intent.putExtra("userName", nickName);
                    startActivity(intent);
                }
            }
        });

        if (!IMManager.isLogin()) {
            presenter.loadUserInfo(identifier);
        }
    }

    @Override
    protected void onNewMessage() {

    }

    @Override
    protected void onIMLogin() {
        presenter.loadUserInfo(identifier);
    }

    @NonNull
    @Override
    public UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    public void onAddBlackListSuc() {
        vf.setDisplayedChild(1);
    }

    @Override
    public void onAddBlackListFail() {
        tbBlack.setOnCheckedChangeListener(null);
        tbBlack.setChecked(false);
        tbBlack.setOnCheckedChangeListener(blackListListener);
    }

    @Override
    public void onRemoveBlackListSuc() {
        vf.setDisplayedChild(1);
        if (toAdd) {
            toAdd = false;
            Intent intent = new Intent(this, AddFriendInfoActivity.class);
            intent.putExtra("userID", identifier);
            intent.putExtra("userName", nickName);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRemoveBlackListFail() {
        tbBlack.setOnCheckedChangeListener(null);
        tbBlack.setChecked(true);
        tbBlack.setOnCheckedChangeListener(blackListListener);
    }

    @Override
    public void onDeleteFriendSuc() {
        finish();
    }

    @Override
    public void onLoadSuccess(final UserInfoBean userInfo, boolean isRefresh) {
        nickName = userInfo.getName();
        Picasso.with(this).load(userInfo.getAvatar()).fit().into(rivAvatar);
        ivVip.setVisibility(userInfo.isV() ? View.VISIBLE : View.GONE);
        uname.setText(String.format("%s", nickName));
        hyAccount.setText(String.format("街圈号:%s", userInfo.getIdentifier()));
        gender.setImageDrawable(userInfo.getGender().equals("1") ? getResources().getDrawable(R.drawable.ic_male) : getResources().getDrawable(R.drawable.ic_female));
        hbCount.setText(String.format("%s个红包", userInfo.getTotalSend()));
        hbLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriDispatcher.getInstance().dispatch(UserInfoActivity.this, userInfo.getHbLink());
            }
        });

        tbBlack.setChecked(userInfo.getRelation() == 2);
        tbBlack.setOnCheckedChangeListener(blackListListener);
        if (!identifier.equals(IMManager.getId())) {
            if (userInfo.getRelation() == 1) {
                vf.setDisplayedChild(0);
            } else {
                vf.setDisplayedChild(1);
            }
            vf.setVisibility(View.VISIBLE);
        }

        rivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, BrowserImageActivity.class);
                ArrayList<String> urls = new ArrayList<>();
                urls.add(userInfo.getAvatar());
                intent.putExtra(BrowserImageActivity.POSITION, 0);
                intent.putExtra(BrowserImageActivity.URLS, urls);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onError(int code, String message) {

    }

    private boolean ignoreCheck = false;
    private CompoundButton.OnCheckedChangeListener blackListListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
            if (ignoreCheck) {
                ignoreCheck = false;
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
            String addToBlack = "添加到黑名单，将会解除好友，并不会收到对方发来的消息";
            String deleteFromBlack = "从黑名单恢复，并不会恢复好友关系，需要手动加对方未好友";
            builder.setMessage(isChecked ? addToBlack : deleteFromBlack);
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ignoreCheck = true;
                    tbBlack.setChecked(!isChecked);
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ignoreCheck = false;
                    if (isChecked) {
                        presenter.addBlackList(identifier);
                    } else {
                        presenter.removeBlackList(identifier);
                    }
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    tbBlack.setOnCheckedChangeListener(null);
                    tbBlack.setChecked(!tbBlack.isChecked());
                    tbBlack.setOnCheckedChangeListener(blackListListener);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    };

}
