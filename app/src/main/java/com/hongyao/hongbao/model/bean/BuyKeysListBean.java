package com.hongyao.hongbao.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wjf on 16/8/24.
 */
public class BuyKeysListBean implements Serializable {

    private List<ListBean> list;
    private String what;
    private String how;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public static class ListBean implements Serializable {
        private int type;
        private int num;
        private String money;
        private String desc;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
