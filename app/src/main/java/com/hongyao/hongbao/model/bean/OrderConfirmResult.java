package com.hongyao.hongbao.model.bean;

import java.util.List;

/**
 * Created by liaolan on 16/3/30.
 */
public class OrderConfirmResult {
    private ConsigneeInfoBean consigneeInfo;
    private List<ItemInfosBean> itemInfos;
    private String totalPrice;

    public ConsigneeInfoBean getConsigneeInfo() {
        return consigneeInfo;
    }

    public void setConsigneeInfo(ConsigneeInfoBean consigneeInfo) {
        this.consigneeInfo = consigneeInfo;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ItemInfosBean> getItemInfos() {
        return itemInfos;
    }

    public void setItemInfos(List<ItemInfosBean> itemInfos) {
        this.itemInfos = itemInfos;
    }

    public static class ConsigneeInfoBean {
        private String consigneeId;
        private String address;
        private String name;
        private String phone;

        public String getConsigneeId() {
            return consigneeId;
        }

        public void setConsigneeId(String consigneeId) {
            this.consigneeId = consigneeId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public static class ItemInfosBean {
        private ShopInfoBean shopInfo;
        private String totalPrice;
        private String shippingFee;
        private List<SkuInfoBean> skuInfo;

        public ShopInfoBean getShopInfo() {
            return shopInfo;
        }

        public void setShopInfo(ShopInfoBean shopInfo) {
            this.shopInfo = shopInfo;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getShippingFee() {
            return shippingFee;
        }

        public void setShippingFee(String shippingFee) {
            this.shippingFee = shippingFee;
        }

        public List<SkuInfoBean> getSkuInfo() {
            return skuInfo;
        }

        public void setSkuInfo(List<SkuInfoBean> skuInfo) {
            this.skuInfo = skuInfo;
        }

        public static class ShopInfoBean {
            private String logo;
            private String shopName;
            private String shopId;

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }
        }

        public static class SkuInfoBean {
            private String image;
            private String title;
            private int amount;
            private String cartId;
            private String skuId;
            private String skuDesc;
            private String price;

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

            public String getCartId() {
                return cartId;
            }

            public void setCartId(String cartId) {
                this.cartId = cartId;
            }

            public String getSkuId() {
                return skuId;
            }

            public void setSkuId(String skuId) {
                this.skuId = skuId;
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
        }
    }
}
