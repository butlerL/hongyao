package com.hongyao.hongbao.model.network;

/**
 * Created by liaolan on 16/3/23.
 */
public class NetworkApi {
    private static final String HOST = "";

    public static final String URL_TIME_LINE = HOST;

    /**
     * wp
     */
    public static final String URL_TIME_LINE_MORE = HOST + "/index/more";

    /**
     * hbId
     */
    public static final String URL_HONG_BAO_DETAIL = HOST + "/hongbao";

    /**
     * hbId
     */
    public static final String URL_HONGBAO_FAVOR = HOST + "/hongbao/favor";

    /**
     * hbId
     * wp
     */
    public static final String URL_HONG_BAO_COMMENT = HOST + "/hongbao/comments";

    /**
     * hbId
     */
    public static final String URL_HONG_BAO_ADD_COMMENT = HOST + "/hongbao/addcomment";

    public static final String URL_SHOPPING = HOST + "/shopping";

    /**
     * userId=1217o&type=1
     */
    public static final String URL_USER_TIME_LINE = HOST + "/usertimeline";

    public static final String URL_COMPANY_LIST = HOST + "/official/list";

    /**
     * companyId
     */
    public static final String URL_COMPANY_CONTENT_LIST = HOST + "/official/contentlist";

    /**
     * hbId
     */
    public static final String URL_HONG_BAO_USER_LIST = HOST + "/hongbao/userlist";

    /**
     * companyId
     */
    public static final String URL_COMPANY_INFO = HOST + "/official/index";

    /**
     * wode
     */
    public static final String URL_WO_DE = HOST + "/me";

    /**
     * wode:红包明细
     */
    public static final String URL_WO_DE_HONG_BAO_GET_GIVE = HOST + "/me/myhblist";

    /**
     * wode:红包明细加载更多
     */
    public static final String URL_WO_DE_HONG_BAO_MORE = HOST + "/me/myhblistmore";

    /**
     * themeId
     */
    public static final String URL_THEME_DESC = HOST + "/hongbao/themedesc";

    /**
     * wode:余额
     */
    public static final String URL_WO_DE_YU_E = HOST + "/me/mybalancelist";

    /**
     * wode:余额-账单加载更多
     */
    public static final String URL_WO_DE_BILL_MORE = HOST + "/me/mybalancelistmore";

    /**
     * wode:摇匙
     */
    public static final String URL_WO_DE_YAO_SHI = HOST + "/me/mykey";

    /**
     * wode:购买摇匙
     */
    public static final String URL_WO_DE_YAO_SHI_BUY = HOST + "/me/buykey";

    /**
     * hbId
     */
    public static final String URL_HONG_BAO_YAO = HOST + "/hongbao/yao";

    public static final String URL_HONG_BAO_REGULAR = HOST + "/hongbao/regular";

    /**
     * hbMoney(金额), hbTheme(主题 8001-8006), hbIntroduce(内容), hbImages(内容图片), hbType(红包类型，永恒为1)
     */
    public static final String URL_HONG_BAO_ADD = HOST + "/hongbao/add";

    /**
     * companyId
     */
    public static final String URL_OFFICIAL_FOLLOW = HOST + "/official/follow";

    public static final String URL_OFFICIAL_UNFOLLOW = HOST + "/official/unfollow";

    public static final String URL_OFFICIAL_INSHOPPAY = HOST + "/official/inshoppay";

    public static final String URL_ME_DAYGETKEY = HOST + "/me/daygetkey";
    /**
     * wode:buy vip
     */
    public static final String URL_WO_DE_BUY_VIP = HOST + "/me/buyvip";

    public static final String URL_VERSION = HOST + "/version";

    public static final String URL_BUY_KEYS_LIST = HOST + "/me/keys";

    public static final String URL_BUY_VIP_LIST = HOST + "/me/vips";
}
