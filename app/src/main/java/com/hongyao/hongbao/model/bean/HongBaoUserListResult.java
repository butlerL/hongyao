package com.hongyao.hongbao.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liaolan on 16/3/27.
 */
public class HongBaoUserListResult implements Serializable {
    private String hbTotal;
    private HbUsersBean hbUsers;
    private String hbMoney;
    private String hbDesc;

    public String getHbTotal() {
        return hbTotal;
    }

    public void setHbTotal(String hbTotal) {
        this.hbTotal = hbTotal;
    }

    public HbUsersBean getHbUsers() {
        return hbUsers;
    }

    public void setHbUsers(HbUsersBean hbUsers) {
        this.hbUsers = hbUsers;
    }

    public String getHbMoney() {
        return hbMoney;
    }

    public void setHbMoney(String hbMoney) {
        this.hbMoney = hbMoney;
    }

    public String getHbDesc() {
        return hbDesc;
    }

    public void setHbDesc(String hbDesc) {
        this.hbDesc = hbDesc;
    }

    public static class HbUsersBean implements Serializable {
        private boolean isEnd;
        private String wp;
        private List<ListBean> list;

        public boolean isIsEnd() {
            return isEnd;
        }

        public void setIsEnd(boolean isEnd) {
            this.isEnd = isEnd;
        }

        public String getWp() {
            return wp;
        }

        public void setWp(String wp) {
            this.wp = wp;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            private String avatar;
            private boolean isV;
            private String name;
            private String time;
            private String money;
            private boolean isTop;
            private String userInfoLink;
            private boolean isMaxMoney;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public boolean isIsV() {
                return isV;
            }

            public void setIsV(boolean isV) {
                this.isV = isV;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public boolean isIsTop() {
                return isTop;
            }

            public void setIsTop(boolean isTop) {
                this.isTop = isTop;
            }

            public String getUserInfoLink() {
                return userInfoLink;
            }

            public void setUserInfoLink(String userInfoLink) {
                this.userInfoLink = userInfoLink;
            }

            public boolean isMaxMoney() {
                return isMaxMoney;
            }

            public void setMaxMoney(boolean maxMoney) {
                isMaxMoney = maxMoney;
            }
        }
    }
}
