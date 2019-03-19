package com.hongyao.hongbao.model.bean;

import java.util.List;

/**
 * Created by liaolan on 16/3/26.
 */
public class UserTimeLineResult {
    private String account;
    private boolean isV;
    private int gender;
    private String avatar;
    private String name;
    private String totalMoney;
    private String totalCount;
    private TimelineBean timeline;
    private String userInfoLink;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public TimelineBean getTimeline() {
        return timeline;
    }

    public void setTimeline(TimelineBean timeline) {
        this.timeline = timeline;
    }

    public String getUserInfoLink() {
        return userInfoLink;
    }

    public void setUserInfoLink(String userInfoLink) {
        this.userInfoLink = userInfoLink;
    }

    public static class TimelineBean {
        private boolean isEnd;
        private String wp;
        private List<ListBean> list;

        public boolean isIsEnd() {
            return isEnd;
        }

        public void setIsEnd(boolean isEnd) {
            this.isEnd = isEnd;
        }

        public String getWp() {
            return wp;
        }

        public void setWp(String wp) {
            this.wp = wp;
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
            private String name;
            private String time;
            private String content;
            /**
             * money : 2323232
             * total : 2
             * status : 1
             * link : xiaoma://hbDetail?hbId=11o
             */

            private HongbaoBean hongbao;
            private String link;
            private String id;
            private List<String> images;
            private boolean isFavored;
            private int favorCount;

            public boolean isFavored() {
                return isFavored;
            }

            public void setFavored(boolean favored) {
                isFavored = favored;
            }

            public int getFavorCount() {
                return favorCount;
            }

            public void setFavorCount(int favorCount) {
                this.favorCount = favorCount;
            }

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public HongbaoBean getHongbao() {
                return hongbao;
            }

            public void setHongbao(HongbaoBean hongbao) {
                this.hongbao = hongbao;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }

            public static class HongbaoBean {
                private String money;
                private int total;
                private int status;
                private String link;

                public String getMoney() {
                    return money;
                }

                public void setMoney(String money) {
                    this.money = money;
                }

                public int getTotal() {
                    return total;
                }

                public void setTotal(int total) {
                    this.total = total;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }
            }
        }
    }
}
