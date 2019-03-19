package com.hongyao.hongbao.model.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wjf on 2016-03-26.
 */
public class WoDeHongBaoResult implements Serializable {

    private String totalCount = null;
    private String totalMoney = null;
    private HongBaoList hongbaos = null;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public HongBaoList getHongbaos() {
        return hongbaos;
    }

    public void setHongbaos(HongBaoList hongbaos) {
        this.hongbaos = hongbaos;
    }

    @Override
    public String toString() {
        return "WoDeHongBaoResult{" +
                "totalCount='" + totalCount + '\'' +
                ", totalMoney='" + totalMoney + '\'' +
                ", hongbaos=" + hongbaos +
                '}';
    }

    public static class HongBaoList implements Serializable {
        private boolean isEnd = false;
        private ArrayList<HongBaoContent> list = null;
        private String wp = null;

        public boolean isEnd() {
            return isEnd;
        }

        public void setIsEnd(boolean isEnd) {
            this.isEnd = isEnd;
        }

        public ArrayList<HongBaoContent> getList() {
            return list;
        }

        public void setHongBaoContentList(ArrayList<HongBaoContent> list) {
            this.list = list;
        }

        public String getWp() {
            return wp;
        }

        public void setWp(String wp) {
            this.wp = wp;
        }

        @Override
        public String toString() {
            return "HongBaoList{" +
                    "isEnd=" + isEnd +
                    ", list=" + list +
                    ", wp='" + wp + '\'' +
                    '}';
        }
    }

    public static class HongBaoContent implements Serializable {
        private String avatar = null;
        private String gender = null;
        private String name = null;
        private boolean isV = false;
        private String time = null;
        private String money = null;
        private boolean isTop = false;
        private String link = null;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isV() {
            return isV;
        }

        public void setIsV(boolean isV) {
            this.isV = isV;
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

        public boolean isTop() {
            return isTop;
        }

        public void setIsTop(boolean isTop) {
            this.isTop = isTop;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        @Override
        public String toString() {
            return "HongBaoContent{" +
                    "avatar='" + avatar + '\'' +
                    ", gender='" + gender + '\'' +
                    ", name='" + name + '\'' +
                    ", isV=" + isV +
                    ", time='" + time + '\'' +
                    ", money='" + money + '\'' +
                    ", isTop=" + isTop +
                    ", link='" + link + '\'' +
                    '}';
        }
    }
}
