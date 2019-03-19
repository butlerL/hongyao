package com.hongyao.hongbao.model.bean;

/**
 * Created by liaolan on 16/3/30.
 */
public class CashierResult {
    private String totalPrice;
    private String balance;
    private boolean canUseBalance;
    private String failLink;
    private String successLink;

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public boolean isCanUseBalance() {
        return canUseBalance;
    }

    public void setCanUseBalance(boolean canUseBalance) {
        this.canUseBalance = canUseBalance;
    }

    public String getFailLink() {
        return failLink;
    }

    public void setFailLink(String failLink) {
        this.failLink = failLink;
    }

    public String getSuccessLink() {
        return successLink;
    }

    public void setSuccessLink(String successLink) {
        this.successLink = successLink;
    }
}
