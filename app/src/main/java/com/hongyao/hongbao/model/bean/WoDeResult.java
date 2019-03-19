package com.hongyao.hongbao.model.bean;

import java.io.Serializable;

/**
 * Created by wjf on 2016-03-25.
 */
public class WoDeResult implements Serializable {

    public static final String WO_DE_GENDER_MALE = "1";                // 男
    public static final String WO_DE_GENDER_FEMALE = "2";              // 女
    public static final String WO_DE_GENDER_ORG = "3";                 // 机构组织

    private String gender = null;
    private String identifier = null;
    private boolean isV = false;
    private String hongbao = null;
    private String yue = null;
    private String yaoshi = null;
    private String centerLink = null;
    private String topLink = null;
    private String inviteLink = null;
    private String returnMoneyLink = null;
    private String aboutLink = null;
    private String avatar;
    private String uname;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public boolean isV() {
        return isV;
    }

    public void setIsV(boolean isV) {
        this.isV = isV;
    }

    public String getYue() {
        return yue;
    }

    public void setYue(String yue) {
        this.yue = yue;
    }

    public String getHongbao() {
        return hongbao;
    }

    public void setHongbao(String hongbao) {
        this.hongbao = hongbao;
    }

    public String getYaoshi() {
        return yaoshi;
    }

    public void setYaoshi(String yaoshi) {
        this.yaoshi = yaoshi;
    }

    public String getCenterLink() {
        return centerLink;
    }

    public void setCenterLink(String centerLink) {
        this.centerLink = centerLink;
    }

    public String getTopLink() {
        return topLink;
    }

    public void setTopLink(String topLink) {
        this.topLink = topLink;
    }

    public String getInviteLink() {
        return inviteLink;
    }

    public void setInviteLink(String inviteLink) {
        this.inviteLink = inviteLink;
    }

    public String getAboutLink() {
        return aboutLink;
    }

    public void setAboutLink(String aboutLink) {
        this.aboutLink = aboutLink;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getReturnMoneyLink() {
        return returnMoneyLink;
    }

    public void setReturnMoneyLink(String returnMoneyLink) {
        this.returnMoneyLink = returnMoneyLink;
    }

    @Override
    public String toString() {
        return "WoDeResult{" +
                "gender='" + gender + '\'' +
                ", isV=" + isV +
                ", hongbao='" + hongbao + '\'' +
                ", yue='" + yue + '\'' +
                ", yaoshi='" + yaoshi + '\'' +
                ", centerLink='" + centerLink + '\'' +
                ", topLink='" + topLink + '\'' +
                ", inviteLink='" + inviteLink + '\'' +
                ", aboutLink='" + aboutLink + '\'' +
                '}';
    }

    /**
     * 更新头像
     */
    public static class AvatarResult implements Serializable {
        private String avatar = null;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        @Override
        public String toString() {
            return "Avatar{" +
                    "avatar='" + avatar + '\'' +
                    '}';
        }
    }
}
