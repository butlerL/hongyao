package com.hongyao.hongbao.model.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wjf on 2016-03-29.
 */
public class WoDeYuEResult implements Serializable {

    private String balance = null;
    private BillResult bills = null;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public BillResult getBills() {
        return bills;
    }

    public void setBills(BillResult bills) {
        this.bills = bills;
    }

    @Override
    public String toString() {
        return "WoDeYuEResult{" +
                "balance='" + balance + '\'' +
                ", bills=" + bills +
                '}';
    }

    public class BillResult implements Serializable {
        private boolean isEnd = false;
        private ArrayList<BillContent> list = null;
        private String wp = null;

        public boolean isEnd() {
            return isEnd;
        }

        public void setIsEnd(boolean isEnd) {
            this.isEnd = isEnd;
        }

        public ArrayList<BillContent> getList() {
            return list;
        }

        public void setList(ArrayList<BillContent> list) {
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
            return "BillResult{" +
                    "isEnd=" + isEnd +
                    ", list=" + list +
                    ", wp='" + wp + '\'' +
                    '}';
        }
    }

    public class BillContent implements Serializable {
        /**
         * type:1,红包街圈商场商品
         */
        public static final String BILL_TYPE_BUY_MALL = "1";
        /**
         * type:2,发红包
         */
        public static final String BILL_TYPE_GIVE_HONG_BAO = "2";
        /**
         * type:3,购买会员
         */
        public static final String BILL_TYPE_BUY_VIP = "3";
        /**
         * type:4,购买摇匙
         */
        public static final String BILL_TYPE_BUY_YAO_SHI = "4";
        /**
         * type:5,到店支付
         */
        public static final String BILL_TYPE_BUY_STORE = "5";

        private String week = null;
        private String date = null;
        private String money = null;
        private String type = null;
        private String desc = null;

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "BillContent{" +
                    "week='" + week + '\'' +
                    ", date='" + date + '\'' +
                    ", money='" + money + '\'' +
                    ", type='" + type + '\'' +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }
}
