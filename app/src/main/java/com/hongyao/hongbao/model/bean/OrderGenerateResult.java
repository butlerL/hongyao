package com.hongyao.hongbao.model.bean;

/**
 * Created by liaolan on 16/3/30.
 */
public class OrderGenerateResult {
    private String totalPrice;
    private String payId;
    private String balance;

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
