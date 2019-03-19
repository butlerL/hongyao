package com.hongyao.hongbao.presenter;

import com.hongyao.hongbao.iview.IUserInfoView;
import com.hongyao.hongbao.model.bean.UserInfoBean;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.xiaoma.common.network.NetworkCallback;
import com.xiaoma.common.network.UrlName;
import com.xiaoma.common.presenter.BasePresenter;
import com.xiaoma.im.bean.IMDataCenter;

import java.util.HashMap;

/**
 * Created by wjf on 2016-05-04.
 */
public class UserInfoPresenter extends BasePresenter<IUserInfoView> {

    public void loadUserInfo(String id) {
        showFirstProgress();
        HashMap<String, String> params = new HashMap<>();
        params.put("identifier", id);
        networkRequest.get(UrlName.USER_INFO,
                params,
                true,
                new NetworkCallback<UserInfoBean>() {
                    @Override
                    protected void onSuccess(UserInfoBean userInfoBean) {
                        getView().onLoadSuccess(userInfoBean, true);
                        hideProgress();
                    }

                    @Override
                    protected void onFail(int i, String s) {
                        getView().onError(i, s);
                        hideProgress();
                    }
                });
    }

    public void removeBlackList(String id) {
        showProgress();
        HashMap<String, String> params = new HashMap<>();
        params.put("blackUserId", id);
        networkRequest.get(UrlName.IM_USER_BLACK_LIST_DELETE,
                params,
                true,
                new NetworkCallback() {
                    @Override
                    protected void onSuccess(Object o) {
                        hideProgress();
                        getView().onRemoveBlackListSuc();
                    }

                    @Override
                    protected void onFail(int i, String s) {
                        hideProgress();
                        getView().onRemoveBlackListFail();
                    }
                });
    }

    public void addBlackList(final String id) {
        showProgress();
        HashMap<String, String> params = new HashMap<>();
        params.put("blackUserId", id);
        networkRequest.get(UrlName.IM_USER_BLACK_LIST_ADD,
                params,
                true,
                new NetworkCallback() {
                    @Override
                    protected void onSuccess(Object o) {
                        hideProgress();
                        //删除会话
                        TIMManager.getInstance().deleteConversation(TIMConversationType.C2C, id);
                        //维护本地关系链
                        IMDataCenter.getInstance().removeContactUser(id);
                        getView().onAddBlackListSuc();
                    }

                    @Override
                    protected void onFail(int i, String s) {
                        hideProgress();
                        getView().onAddBlackListFail();
                    }
                });
    }

    public void deleteFriend(final String friendId) {
        showProgress();
        HashMap<String, String> params = new HashMap<>();
        params.put("friendId", friendId);
        networkRequest.get(UrlName.IM_USER_FRIEND_DELETE,
                params,
                true,
                new NetworkCallback() {
                    @Override
                    protected void onSuccess(Object o) {
                        hideProgress();
                        //删除会话
                        TIMManager.getInstance().deleteConversation(TIMConversationType.C2C, friendId);
                        //维护本地关系链
                        IMDataCenter.getInstance().removeContactUser(friendId);
                        getView().onDeleteFriendSuc();
                    }

                    @Override
                    protected void onFail(int i, String s) {
                        hideProgress();
                    }
                });
    }
}
