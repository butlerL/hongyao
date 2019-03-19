package com.hongyao.hongbao.model.bean;

import java.io.Serializable;

/**
 * Created by liaolan on 16/4/1.
 */
public class AliPaySignResult implements Serializable{
    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
