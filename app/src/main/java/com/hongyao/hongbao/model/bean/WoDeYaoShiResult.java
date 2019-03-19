package com.hongyao.hongbao.model.bean;

import java.io.Serializable;

/**
 * Created by wjf on 2016-03-30.
 */
public class WoDeYaoShiResult implements Serializable {
    private String keyNum = null;
    private String link = null;

    public String getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(String keyNum) {
        this.keyNum = keyNum;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "WoDeYaoShiResult{" +
                "keyNum='" + keyNum + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    /**
     * 购买摇匙的类型
     */
    public static class YaoShiBuyType implements Serializable {
        private String count = null;
        private String desc = null;
        private String pay = null;
        private String type = null;

        public YaoShiBuyType(String count, String desc, String pay, String type) {
            this.count = count;
            this.desc = desc;
            this.pay = pay;
            this.type = type;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "YaoShiBuyType{" +
                    "count='" + count + '\'' +
                    ", desc='" + desc + '\'' +
                    ", pay='" + pay + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    /**
     * 购买摇匙的payId
     */
    public static class YaoShiBuyId implements Serializable {
        private String payId = null;

        public String getPayId() {
            return payId;
        }

        public void setPayId(String payId) {
            this.payId = payId;
        }

        @Override
        public String toString() {
            return "YaoShiBuyId{" +
                    "payId='" + payId + '\'' +
                    '}';
        }
    }
}
