package com.hongyao.hongbao.iview;


import com.hongyao.hongbao.model.bean.UserInfoBean;
import com.xiaoma.common.ivew.BaseMvpView;

/**
 * Created by wjf on 2016-05-04.
 */
public interface IUserInfoView extends BaseMvpView<UserInfoBean> {
    void onAddBlackListSuc();

    void onAddBlackListFail();

    void onRemoveBlackListSuc();

    void onRemoveBlackListFail();

    void onDeleteFriendSuc();
}
