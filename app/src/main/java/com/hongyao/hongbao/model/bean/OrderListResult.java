package com.hongyao.hongbao.model.bean;

import java.util.List;

/**
 * Created by liaolan on 16/3/31.
 */
public class OrderListResult {
    private OrdersBean orders;

    public OrdersBean getOrders() {
        return orders;
    }

    public void setOrders(OrdersBean orders) {
        this.orders = orders;
    }

    public static class OrdersBean {
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
            private String orderId;
            private String status;
            private String statusForShow;
            private String payId;
            private String payLink;
            private String shippingFee;
            private String totalPrice;
            private String link;
            private List<OrderInfosBean> orderInfos;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStatusForShow() {
                return statusForShow;
            }

            public void setStatusForShow(String statusForShow) {
                this.statusForShow = statusForShow;
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

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public List<OrderInfosBean> getOrderInfos() {
                return orderInfos;
            }

            public void setOrderInfos(List<OrderInfosBean> orderInfos) {
                this.orderInfos = orderInfos;
            }

            public static class OrderInfosBean {
                private String shopName;
                private List<SkuInfoBean> skuInfo;
                private transient String link;

                public String getShopName() {
                    return shopName;
                }

                public void setShopName(String shopName) {
                    this.shopName = shopName;
                }

                public List<SkuInfoBean> getSkuInfo() {
                    return skuInfo;
                }

                public void setSkuInfo(List<SkuInfoBean> skuInfo) {
                    this.skuInfo = skuInfo;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }

                public static class SkuInfoBean {
                    private String image;
                    private String title;
                    private String amount;
                    private String skuDesc;
                    private String price;
                    private String oPrice;
                    private transient String link;

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
}
