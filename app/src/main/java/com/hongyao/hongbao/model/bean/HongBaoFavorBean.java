package com.hongyao.hongbao.model.bean;

import java.io.Serializable;

/**
 * Created by liaolan on 16/8/13.
 */
public class HongBaoFavorBean implements Serializable {
    private String msg;
    private int favorCount;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getFavorCount() {
        return favorCount;
    }

    public void setFavorCount(int favorCount) {
        this.favorCount = favorCount;
    }
}
