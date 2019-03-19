package com.hongyao.hongbao.model.bean;

import java.util.List;

/**
 * Created by liaolan on 16/3/27.
 */
public class ShopResult {
    private ShopInfoBean shopInfo;
    private ItemsBean items;

    public ShopInfoBean getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfoBean shopInfo) {
        this.shopInfo = shopInfo;
    }

    public ItemsBean getItems() {
        return items;
    }

    public void setItems(ItemsBean items) {
        this.items = items;
    }

    public static class ShopInfoBean {
        private String shopId;
        private String shopName;
        private String sales;
        private String favCount;
        private String logo;
        private boolean isFav;

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public String getFavCount() {
            return favCount;
        }

        public void setFavCount(String favCount) {
            this.favCount = favCount;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public boolean isIsFav() {
            return isFav;
        }

        public void setIsFav(boolean isFav) {
            this.isFav = isFav;
        }
    }

    public static class ItemsBean {
        private boolean isEnd;
        private String wp;
        /**
         * link : xiaoma://item?itemId=11q
         * title : 开卖雪纺衫上衣+喇叭裤性感时尚两件套装
         * price : ￥338.00
         * id : 11q
         * image : {"src":"http://img.guaniuwu.cn/20160315/11aca_29595c9c80ac378d4f56e8a37bb6a137_750x750.jpg","h":"750","w":"750"}
         */

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
            private String link;
            private String title;
            private String price;
            private String id;
            private ImageBean image;

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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public ImageBean getImage() {
                return image;
            }

            public void setImage(ImageBean image) {
                this.image = image;
            }

            public static class ImageBean {
                private String src;
                private int h;
                private int w;

                public String getSrc() {
                    return src;
                }

                public void setSrc(String src) {
                    this.src = src;
                }

                public int getH() {
                    return h;
                }

                public void setH(int h) {
                    this.h = h;
                }

                public int getW() {
                    return w;
                }

                public void setW(int w) {
                    this.w = w;
                }
            }
        }
    }
}
