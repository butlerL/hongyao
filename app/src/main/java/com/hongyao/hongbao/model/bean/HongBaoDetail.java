package com.hongyao.hongbao.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liaolan on 16/3/24.
 */
public class HongBaoDetail {
    private HbInfoBean hbInfo;
    private ShareInfoBean shareInfo;
    private HbUsersBean hbUsers;
    private HbCommentsBean hbComments;

    public HbInfoBean getHbInfo() {
        return hbInfo;
    }

    public void setHbInfo(HbInfoBean hbInfo) {
        this.hbInfo = hbInfo;
    }

    public ShareInfoBean getShareInfo() {
        return shareInfo;
    }

    public void setShareInfo(ShareInfoBean shareInfo) {
        this.shareInfo = shareInfo;
    }

    public HbUsersBean getHbUsers() {
        return hbUsers;
    }

    public void setHbUsers(HbUsersBean hbUsers) {
        this.hbUsers = hbUsers;
    }

    public HbCommentsBean getHbComments() {
        return hbComments;
    }

    public void setHbComments(HbCommentsBean hbComments) {
        this.hbComments = hbComments;
    }

    public static class HbInfoBean {
        private String avatar;
        private String name;
        private boolean isV;
        private int gender;
        private String money;
        private String message;
        private String intro;
        private String introLink;
        private String rule;
        private String ruleLink;
        private String hbTheme;
        private String total;
        private String myMoney;
        private int status;
        private String userInfoLink;

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

        public boolean isIsV() {
            return isV;
        }

        public void setIsV(boolean isV) {
            this.isV = isV;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getIntroLink() {
            return introLink;
        }

        public void setIntroLink(String introLink) {
            this.introLink = introLink;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public String getRuleLink() {
            return ruleLink;
        }

        public void setRuleLink(String ruleLink) {
            this.ruleLink = ruleLink;
        }

        public String getHbTheme() {
            return hbTheme;
        }

        public void setHbTheme(String hbTheme) {
            this.hbTheme = hbTheme;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getMyMoney() {
            return myMoney;
        }

        public void setMyMoney(String myMoney) {
            this.myMoney = myMoney;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUserInfoLink() {
            return userInfoLink;
        }

        public void setUserInfoLink(String userInfoLink) {
            this.userInfoLink = userInfoLink;
        }
    }

    public static class ShareInfoBean implements Serializable {
        private String image;
        private String link;
        private String title;
        private String desc;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public static class HbUsersBean {
        private String link;
        /**
         * avatar : http://img.guaniuwu.cn/20160321/ab46c054-190e-a3de-bdf5-22447386cb49_a6e4b50cc56c0f320b8e260dbf55f15b_750x750.jpg
         * isV : false
         */

        private List<ListBean> list;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String avatar;
            private boolean isV;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public boolean isIsV() {
                return isV;
            }

            public void setIsV(boolean isV) {
                this.isV = isV;
            }
        }
    }

    public static class HbCommentsBean {
        private boolean isEnd;
        private String total;
        private String wp;
        private List<CommentBean> list;

        public boolean isIsEnd() {
            return isEnd;
        }

        public void setIsEnd(boolean isEnd) {
            this.isEnd = isEnd;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getWp() {
            return wp;
        }

        public void setWp(String wp) {
            this.wp = wp;
        }

        public List<CommentBean> getList() {
            return list;
        }

        public void setList(List<CommentBean> list) {
            this.list = list;
        }

        public static class CommentBean {
            private String commentId;
            private String avatar;
            private String name;
            private boolean isV;
            private String time;
            private String content;
            private String floor;
            private String parentComment;
            private String gender;


            public String getCommentId() {
                return commentId;
            }

            public void setCommentId(String commentId) {
                this.commentId = commentId;
            }

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

            public boolean isV() {
                return isV;
            }

            public void setIsV(boolean isV) {
                this.isV = isV;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getFloor() {
                return floor;
            }

            public void setFloor(String floor) {
                this.floor = floor;
            }

            public String getParentComment() {
                return parentComment;
            }

            public void setParentComment(String parentComment) {
                this.parentComment = parentComment;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }
        }
    }
}
