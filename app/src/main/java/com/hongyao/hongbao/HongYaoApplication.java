package com.hongyao.hongbao;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.hongyao.hongbao.util.Push;
import com.hongyao.hongbao.view.activity.MainActivity;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.xiaoma.common.application.BaseApplication;
import com.xiaoma.common.bean.TencentImBean;
import com.xiaoma.common.eventBus.LoginEvent;
import com.xiaoma.common.eventBus.LogoutEvent;
import com.xiaoma.common.network.UrlName;
import com.xiaoma.common.route.UriDispatcher;
import com.xiaoma.common.util.XMToast;
import com.xiaoma.im.Constants;
import com.xiaoma.im.IMManager;
import com.xiaoma.im.utils.PushUtil;
import com.xiaoma.login.UserConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

/**
 * Created by liaolan on 16/3/23.
 */
public class HongYaoApplication extends BaseApplication {

    private static HongYaoApplication instance;
    private static Context mContext;
    private static final String WX_APPID = "";

    private IWXAPI wxapi;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        mContext = context;
    }


    public static HongYaoApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        instance = this;
        UriDispatcher.getInstance().registerMainActivityTab("index", 0);
        UriDispatcher.getInstance().registerMainActivityTab("shopping", 1);
        UriDispatcher.getInstance().registerMainActivityTab("fashaoyou", 3);
        UriDispatcher.getInstance().registerMainActivityTab("mine", 4);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        UserConfig.init();
        if (inMainProcess()) {
            regToWx();
            IMManager.init(this, MainActivity.class, new IMManager.OnIMForceOfflineListener() {
                @Override
                public void onOffline() {
                    XMToast.makeText("被踢下线啦", XMToast.LENGTH_LONG).show();
                    UserConfig.setUserInfo("", "");
                    UriDispatcher.getInstance().dispatch(mContext, "xiaoma://login");
                }
            });
            IMManager.setDisableGroup(true);
            IMManager.setTitle("发烧友");
            EventBus.getDefault().register(this);

            Constants.ACCOUNT_NAME = "街圈号";
            Constants.ic_notification_big_image = R.mipmap.ic_launcher;
            Constants.ic_notification_small_image = R.mipmap.ic_small_notification;

            Push.initHandler(this);
        }
    }

    @Override
    protected HashMap<UrlName, String> getUrls() {
        HashMap<UrlName, String> urls = new HashMap<>();
        urls.put(UrlName.CITY_LIST, "http://app2.hoyaoo.com/utils_region");
        urls.put(UrlName.UPLOAD_IMAGE, "http://app2.hoyaoo.com/upload/img");
        urls.put(UrlName.LOGIN, "http://app2.hoyaoo.com/login");
        urls.put(UrlName.LOGOUT, "http://app2.hoyaoo.com/logout");
        urls.put(UrlName.SMS_GET_CODE, "http://app2.hoyaoo.com/utils_sms/getcode");
        urls.put(UrlName.SMS_CHECK, "http://app2.hoyaoo.com/utils_sms/check");
        urls.put(UrlName.FORGET_PASSWORD, "http://app2.hoyaoo.com/forgetpassword");
        urls.put(UrlName.REGISTER, "http://app2.hoyaoo.com/register");
        urls.put(UrlName.ME, "http://app2.hoyaoo.com/user_me");
        urls.put(UrlName.ME_CHANGE_AVATAR, "http://app2.hoyaoo.com/user_me/changeavatar");
        urls.put(UrlName.ME_CHANGE_NAME, "http://app2.hoyaoo.com/user_me/changename");
        urls.put(UrlName.ME_CHANGE_GENDER, "http://app2.hoyaoo.com/user_me/changegender");
        urls.put(UrlName.CASHIER, "http://app2.hoyaoo.com/pay_cashier");
        urls.put(UrlName.CASHIER_BALANCEPAY, "http://app2.hoyaoo.com/pay_cashier/balancepay");
        urls.put(UrlName.CASHIER_ALIPAYSIGN, "http://app2.hoyaoo.com/pay_cashier/alipaysign");
        urls.put(UrlName.CASHIER_WXPAYSIGN, "http://app2.hoyaoo.com/pay_cashier/wxpaysign");
        urls.put(UrlName.MALL_WALL, "http://app2.hoyaoo.com/mall_wall_catewall");
        urls.put(UrlName.MALL_WALL_MORE, "http://app2.hoyaoo.com/mall_wall_catewall/more");
        urls.put(UrlName.MALL_CONSIGNEE, "http://app2.hoyaoo.com/mall_consignee");
        urls.put(UrlName.MALL_CONSIGNEE_ADD, "http://app2.hoyaoo.com/mall_consignee/add");
        urls.put(UrlName.MALL_CONSIGNEE_DELETE, "http://app2.hoyaoo.com/mall_consignee/delete");
        urls.put(UrlName.MALL_CONSIGNEE_EDIT, "http://app2.hoyaoo.com/mall_consignee/edit");
        urls.put(UrlName.MALL_CONSIGNEE_SET_DEFAULT, "http://app2.hoyaoo.com/mall_consignee/setdefault");
        urls.put(UrlName.MALL_CONSIGNEE_LIST, "http://app2.hoyaoo.com/mall_consignee/list");
        urls.put(UrlName.MALL_CONSIGNEE_LIST_MORE, "http://app2.hoyaoo.com/mall_consignee/list/more");
        urls.put(UrlName.MALL_ITEM, "http://app2.hoyaoo.com/mall_item");
        urls.put(UrlName.MALL_ITEM_FOLLOW, "http://app2.hoyaoo.com/mall_item/follow");
        urls.put(UrlName.MALL_ITEM_UNFOLLOW, "http://app2.hoyaoo.com/mall_item/unfollow");
        urls.put(UrlName.MALL_CART, "http://app2.hoyaoo.com/mall_cart");
        urls.put(UrlName.MALL_CART_CHANGE, "http://app2.hoyaoo.com/mall_cart/change");
        urls.put(UrlName.MALL_CART_ADD, "http://app2.hoyaoo.com/mall_cart/add");
        urls.put(UrlName.MALL_CART_TOTAL_PRICE, "http://app2.hoyaoo.com/mall_cart/totalprice");
        urls.put(UrlName.MALL_ORDER_CONFIRM, "http://app2.hoyaoo.com/mall_order/confirm");
        urls.put(UrlName.MALL_ORDER_GENERATE, "http://app2.hoyaoo.com/mall_order/generate");
        urls.put(UrlName.MALL_ORDER, "http://app2.hoyaoo.com/mall_order");
        urls.put(UrlName.MALL_ORDER_CANCEL, "http://app2.hoyaoo.com/mall_order/cancel");
        urls.put(UrlName.MALL_ORDER_DELETE, "http://app2.hoyaoo.com/mall_order/delete");
        urls.put(UrlName.MALL_ORDER_LIST, "http://app2.hoyaoo.com/mall_order/list");
        urls.put(UrlName.MALL_ORDER_LIST_MORE, "http://app2.hoyaoo.com/mall_order/listmore");
        urls.put(UrlName.MALL_ORDER_RECEIVE, "http://app2.hoyaoo.com/mall_order/receive");
        urls.put(UrlName.MALL_ORDER_AFTER_SALE_LIST, "http://app2.hoyaoo.com/mall_refund/list");
        urls.put(UrlName.MALL_ORDER_AFTER_SALE_LIST_MORE, "http://app2.hoyaoo.com/mall_refund/more");
        urls.put(UrlName.MALL_SHOP, "http://app2.hoyaoo.com/mall_shop");
        urls.put(UrlName.MALL_SHOP_MORE, "http://app2.hoyaoo.com/mall_shop/more");
        urls.put(UrlName.MALL_SHOP_FOLLOW, "http://app2.hoyaoo.com/mall_shop/follow");
        urls.put(UrlName.MALL_SHOP_UNFOLLOW, "http://app2.hoyaoo.com/mall_shop/unfollow");
        urls.put(UrlName.MALL_COMMENT_READY, "http://app2.hoyaoo.com/mall_comment/ready");
        urls.put(UrlName.MALL_COMMENT_PUSH, "http://app2.hoyaoo.com/mall_comment/publish");
        urls.put(UrlName.MALL_COMMENT_LIST, "http://app2.hoyaoo.com/mall_item/comment");
        urls.put(UrlName.MALL_REFUND_APPLY, "http://app2.hoyaoo.com/mall_refund/apply");
        urls.put(UrlName.MALL_REFUND_SUBMIT, "http://app2.hoyaoo.com/mall_refund/submit");
        urls.put(UrlName.MALL_REFUND_CANCEL, "http://app2.hoyaoo.com/mall_refund/cancel");
        urls.put(UrlName.MALL_REFUND_HISTORY, "http://app2.hoyaoo.com/mall_refund/history");
        urls.put(UrlName.MALL_REFUND_DETAIL, "http://app2.hoyaoo.com/mall_refund/detail");
        urls.put(UrlName.MALL_WALL_SIFT_WALL, "http://app2.hoyaoo.com/youcai_siftwall");
        urls.put(UrlName.MALL_WALL_SIFT_WALL_MORE, "http://app2.hoyaoo.com/youcai_siftwall/more");
        urls.put(UrlName.BMALL_CART, "http://app2.hoyaoo.com/bmall_cart");
        urls.put(UrlName.BMALL_CART_ADD, "http://app2.hoyaoo.com/bmall_cart/add");
        urls.put(UrlName.BMALL_ITEM, "http://app2.hoyaoo.com/bmall_item");
        urls.put(UrlName.BMALL_ORDER_CONFIRM, "http://app2.hoyaoo.com/bmall_order/confirm");

        urls.put(UrlName.USER_INFO, "http:///app2.hoyaoo.com/user_user/userInfo");
        urls.put(UrlName.SEARCH_USER, "http:///app2.hoyaoo.com/user_user/search");
        urls.put(UrlName.IM_FRIEND_INFO_LIST, "http:///app2.hoyaoo.com/im_friend/list");
        urls.put(UrlName.IM_FRIEND_INFO, "http:///app2.hoyaoo.com/im_friend");
        urls.put(UrlName.IM_USER_APPLY, "http:///app2.hoyaoo.com/im_user/apply");
        urls.put(UrlName.IM_USER_APPLY_ACCEPT, "http:///app2.hoyaoo.com/im_user/applyaccept");
        urls.put(UrlName.IM_USER_APPLY_LIST, "http:///app2.hoyaoo.com/im_user/applylist");
        urls.put(UrlName.IM_USER_FRIEND_LIST, "http:///app2.hoyaoo.com/im_user/friendlist");
        urls.put(UrlName.IM_USER_FRIEND_DELETE, "http:///app2.hoyaoo.com/im_user/frienddelete");
        urls.put(UrlName.IM_USER_BLACK_LIST, "http:///app2.hoyaoo.com/im_user/blacklist");
        urls.put(UrlName.IM_USER_BLACK_LIST_ADD, "http:///app2.hoyaoo.com/im_user/blacklistadd");
        urls.put(UrlName.IM_USER_BLACK_LIST_DELETE, "http:///app2.hoyaoo.com/im_user/blacklistdelete");
        urls.put(UrlName.IM_FRIEND_MIXED_INFO, "http:///app2.hoyaoo.com/im_friend/mixed_info");
        urls.put(UrlName.IM_SESSION_INFO, "http:///app2.hoyaoo.com/im_session/info");
        return urls;
    }

    private void regToWx() {
        wxapi = WXAPIFactory.createWXAPI(mContext, WX_APPID, true);
        wxapi.registerApp(WX_APPID);

    }

    public IWXAPI getWxapi() {
        return this.wxapi;
    }

    public IWXAPI getIWXAPI() {
        if (wxapi == null) {
            wxapi = WXAPIFactory.createWXAPI(getApplicationContext(), WX_APPID, true);
            wxapi.registerApp(WX_APPID);
        }
        return wxapi;
    }

    /**
     * 分享到微信好友
     *
     * @param bmp
     * @param shareUrl
     * @param shareTitle
     * @param shareDesc
     */
    public void shareFriend(Bitmap bmp, String shareUrl, String shareTitle, String shareDesc) {
        checkWeChat();

        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = shareUrl;
        WXMediaMessage msg = new WXMediaMessage(webPage);
        msg.title = shareTitle;
        msg.description = shareDesc;
        msg.setThumbImage(bmp);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(null);
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        HongYaoApplication.getInstance().getWxapi().sendReq(req);
    }

    /**
     * 分享到微信朋友圈
     *
     * @param bmp
     * @param shareUrl
     * @param shareTitle
     * @param shareDesc
     */
    public void shareWX(Bitmap bmp, String shareUrl, String shareTitle, String shareDesc) {
        checkWeChat();

        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = shareUrl;

        WXMediaMessage msg = new WXMediaMessage(webPage);
        msg.title = shareTitle;
        msg.description = shareDesc;
        msg.setThumbImage(bmp);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(null);
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        HongYaoApplication.getInstance().getWxapi().sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    /**
     * 检查当前设备是否已安装微信客户端
     */
    private void checkWeChat() {
        if (!HongYaoApplication.getInstance().getWxapi().isWXAppInstalled()) {
            Toast.makeText(this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Subscribe
    public void onEvent(LoginEvent event) {
        if (event.loginBean != null && event.loginBean.getTencentIm() != null) {
            UserConfig.setUserInfo(event.loginBean.getUser().getUserId(), event.loginBean.getUser().getSign());
            TencentImBean tencentIm = event.loginBean.getTencentIm();
            IMManager.login(tencentIm);
        } else {
            XMToast.makeText("无法登录聊天服务器", XMToast.LENGTH_LONG).show();
        }
        Push.start(this);
    }

    @Subscribe
    public void onEvent(LogoutEvent event) {
        IMManager.logout();
    }

}
