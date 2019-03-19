package com.hongyao.hongbao.model.bean;

/**
 * Created by slumbersoul on 16/3/26.
 */
public class UserInfoBean {
    private String avatar;
    private String name;
    private String gender;
    private boolean isV;
    private String totalSend;
    private String identifier;
    private String hbLink;
    private int relation;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isV() {
        return isV;
    }

    public void setIsV(boolean isV) {
        this.isV = isV;
    }

    public String getTotalSend() {
        return totalSend;
    }

    public void setTotalSend(String totalSend) {
        this.totalSend = totalSend;
    }

    public String getIdentifier() {
        return identifier != null ? identifier : "";
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getHbLink() {
        return hbLink;
    }

    public void setHbLink(String hbLink) {
        this.hbLink = hbLink;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }
}
