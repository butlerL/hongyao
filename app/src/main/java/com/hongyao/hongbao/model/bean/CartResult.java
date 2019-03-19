package com.hongyao.hongbao.model.bean;

import java.util.List;

/**
 * Created by liaolan on 16/3/29.
 */
public class CartResult {
    private List<CartInfoBean> cartInfo;

    public List<CartInfoBean> getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(List<CartInfoBean> cartInfo) {
        this.cartInfo = cartInfo;
    }

    public static class CartInfoBean {
        private ShopInfoBean shopInfo;
        private List<SkuInfoBean> skuInfo;

        public ShopInfoBean getShopInfo() {
            return shopInfo;
        }

        public void setShopInfo(ShopInfoBean shopInfo) {
            this.shopInfo = shopInfo;
        }

        public List<SkuInfoBean> getSkuInfo() {
            return skuInfo;
        }

        public void setSkuInfo(List<SkuInfoBean> skuInfo) {
            this.skuInfo = skuInfo;
        }

        public static class ShopInfoBean {
            private String shopId;
            private String shopName;
            private String link;
            private DiscountInfoBean discountInfo;

            //前端添加的字段
            private boolean isCheck;

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

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public DiscountInfoBean getDiscountInfo() {
                return discountInfo;
            }

            public void setDiscountInfo(DiscountInfoBean discountInfo) {
                this.discountInfo = discountInfo;
            }

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public static class DiscountInfoBean {
                private String show;

                public String getShow() {
                    return show;
                }

                public void setShow(String show) {
                    this.show = show;
                }
            }
        }

        public static class SkuInfoBean {
            private String skuId;
            private String image;
            private String title;
            private int amount;
            private String quantity;
            private String cartId;
            private String skuDesc;
            private String price;
            private int status;
            private String link;

            //前端添加的字段
            private boolean isCheck;

            public String getSkuId() {
                return skuId;
            }

            public void setSkuId(String skuId) {
                this.skuId = skuId;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getCartId() {
                return cartId;
            }

            public void setCartId(String cartId) {
                this.cartId = cartId;
            }

            public String getSkuDesc() {
                return skuDesc;
            }

            public void setSkuDesc(String skuDesc) {
                this.skuDesc = skuDesc;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }
        }
    }
}
