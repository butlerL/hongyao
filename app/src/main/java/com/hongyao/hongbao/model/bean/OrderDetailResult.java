package com.hongyao.hongbao.model.bean;

import java.util.List;

/**
 * Created by liaolan on 16/3/31.
 */
public class OrderDetailResult {
    private ConsigneeInfoBean consigneeInfo;
    private String totalPrice;
    private String status;
    private String payId;
    private String payLink;

    private List<ItemInfosBean> itemInfos;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayLink() {
        return payLink;
    }

    public void setPayLink(String payLink) {
        this.payLink = payLink;
    }

    public List<ItemInfosBean> getItemInfos() {
        return itemInfos;
    }

    public void setItemInfos(List<ItemInfosBean> itemInfos) {
        this.itemInfos = itemInfos;
    }

    public static class ConsigneeInfoBean {
        private String address;
        private String name;
        private String phone;

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
        private String shippingFee;
        private String totalPrice;
        private String orderTime;
        private transient String orderId;

        private List<SkuInfoBean> skuInfo;

        public ShopInfoBean getShopInfo() {
            return shopInfo;
        }

        public void setShopInfo(ShopInfoBean shopInfo) {
            this.shopInfo = shopInfo;
        }

        public String getShippingFee() {
            return shippingFee;
        }

        public void setShippingFee(String shippingFee) {
            this.shippingFee = shippingFee;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public List<SkuInfoBean> getSkuInfo() {
            return skuInfo;
        }

        public void setSkuInfo(List<SkuInfoBean> skuInfo) {
            this.skuInfo = skuInfo;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public static class ShopInfoBean {
            private String logo;
            private String shopName;

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
        }

        public static class SkuInfoBean {
            private String image;
            private String title;
            private String amount;
            private String skuDesc;
            private String price;
            private String oPrice;

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

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
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

            public String getOPrice() {
                return oPrice;
            }

            public void setOPrice(String oPrice) {
                this.oPrice = oPrice;
            }
        }
    }
}
