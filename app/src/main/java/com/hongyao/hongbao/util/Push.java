package com.hongyao.hongbao.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengRegistrar;
import com.umeng.message.entity.UMessage;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.NetworkRequest;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.im.Constants;
import com.xiaoma.im.IMManager;
import com.xiaoma.im.eventBus.ContactChangeEvent;
import com.xiaoma.im.utils.PushUtil;
import com.xiaoma.login.UserConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liaolan on 16/9/1.
 */
public class Push {
    public static void start(final Context context) {
        PushAgent mPushAgent = PushAgent.getInstance(context);

        String deviceToken = UmengRegistrar.getRegistrationId(context);
        if (TextUtils.isEmpty(deviceToken)) {
            mPushAgent.enable(new IUmengRegisterCallback() {
                @Override
                public void onRegistered(String s) {
                    unLoadDeviceToken(s);
                }
            });
        } else {
            unLoadDeviceToken(deviceToken);
        }
    }

    public static void initHandler(Context context) {
        PushAgent.getInstance(context).setMessageHandler(new UmengMessageHandler() {
            @Override
            public void handleMessage(Context context, UMessage uMessage) {
                String custom = uMessage.custom;
                if (TextUtils.isEmpty(custom)) {
                    super.handleMessage(context, uMessage);
                    return;
                }

                Custom customBean = new Gson().fromJson(custom, Custom.class);
                if (customBean == null) {
                    super.handleMessage(context, uMessage);
                    return;
                }

                if (customBean.getType() != PushUtil.PUSH_CHAT
                        && customBean.getType() != PushUtil.PUSH_USER_APPLY
                        && customBean.getType() != PushUtil.PUSH_USER_APPLY_ACCEPT
                        && customBean.getType() != PushUtil.PUSH_USER_DELETE) {
                    super.handleMessage(context, uMessage);
                    return;
                }

                EventBus.getDefault().post(new ContactChangeEvent());
                switch (customBean.getType()) {
                    case PushUtil.PUSH_USER_APPLY:
                        PushUtil.friendApplyCount++;
                        break;
                    case PushUtil.PUSH_USER_DELETE:
                        return;
                }


                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                Intent notificationIntent = new Intent(context, IMManager.getMainActivityCls());
                if (!TextUtils.isEmpty(customBean.getLink())) {
                    notificationIntent = UriDispatcher.getInstance().getUriIntent(context, Uri.parse(customBean.getLink()));
                }
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent intent = PendingIntent.getActivity(context, 0,
                        notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                Bitmap icon = BitmapFactory.decodeResource(context.getResources(), Constants.ic_notification_big_image);
                mBuilder.setContentTitle(uMessage.title)//设置通知栏标题
                        .setContentText(uMessage.text)
                        .setContentIntent(intent) //设置通知栏点击意图
                        .setLargeIcon(icon)
                        .setTicker(uMessage.ticker) //通知首次出现在通知栏，带上升动画效果的
                        .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                        .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                        .setSmallIcon(Constants.ic_notification_small_image);//设置通知小ICON
                Notification notify = mBuilder.build();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                mNotificationManager.notify(1, notify);
            }
        });
    }

    //1-android, 2-ios
    private static void unLoadDeviceToken(String deviceToken) {
        if (TextUtils.isEmpty(UserConfig.getSign())) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("deviceToken", deviceToken);
        params.put("deviceType", "1");
        new NetworkRequest().get("http://app2.hoyaoo.com/user_user/setpushtoken",
                params,
                false,
                new NetworkCallback<Object>() {
                    @Override
                    public void onSuccess(Object response) {
                    }

                    @Override
                    public void onFail(int code, String message) {
                    }
                });
    }

    private static class Custom {
        private int type;
        private String link;
        private String time;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
