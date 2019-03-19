package com.hongyao.hongbao.model.bean;

/**
 * Created by liaolan on 16/3/31.
 */
public class HongBaoRegularResult {
    private double minPrice;
    private double maxPrice;
    private String sendDesc;
    private String getDesc;
    private String link;

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getSendDesc() {
        return sendDesc;
    }

    public void setSendDesc(String sendDesc) {
        this.sendDesc = sendDesc;
    }

    public String getGetDesc() {
        return getDesc;
    }

    public void setGetDesc(String getDesc) {
        this.getDesc = getDesc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getMinYuan() {
        return minPrice / 100;
    }

    public double getMaxYuan() {
        return maxPrice / 100;
    }
}
