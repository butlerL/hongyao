package com.hongyao.hongbao.model.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by liaolan on 16/3/26.
 */
public class GoodDetail implements Serializable{
    private ItemInfoBean itemInfo;
    private ShopInfoBean shopInfo;

    public ItemInfoBean getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfoBean itemInfo) {
        this.itemInfo = itemInfo;
    }

    public ShopInfoBean getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfoBean shopInfo) {
        this.shopInfo = shopInfo;
    }

    public static class ItemInfoBean implements Serializable{
        private String title;
        private String desc;
        private int status;
        private CoverImageBean coverImage;
        private String sales;
        private boolean isFav;
        private String price;
        private String discountInfo;
        private List<DetailImageBean> detailImage;
        private SkuInfo skuInfo;

        public boolean isFav() {
            return isFav;
        }

        public void setFav(boolean fav) {
            isFav = fav;
        }

        public SkuInfo getSkuInfo() {
            return skuInfo;
        }

        public void setSkuInfo(SkuInfo skuInfo) {
            this.skuInfo = skuInfo;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public CoverImageBean getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(CoverImageBean coverImage) {
            this.coverImage = coverImage;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public boolean isIsFav() {
            return isFav;
        }

        public void setIsFav(boolean isFav) {
            this.isFav = isFav;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscountInfo() {
            return discountInfo;
        }

        public void setDiscountInfo(String discountInfo) {
            this.discountInfo = discountInfo;
        }

        public List<DetailImageBean> getDetailImage() {
            return detailImage;
        }

        public void setDetailImage(List<DetailImageBean> detailImage) {
            this.detailImage = detailImage;
        }

        public static class CoverImageBean implements Serializable{
            private float ar;
            private List<String> src;

            public float getAr() {
                return ar;
            }

            public void setAr(int ar) {
                this.ar = ar;
            }

            public List<String> getSrc() {
                return src;
            }

            public void setSrc(List<String> src) {
                this.src = src;
            }
        }

        public static class DetailImageBean implements Serializable{
            private String src;
            private float ar;

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public float getAr() {
                return ar;
            }

            public void setAr(float ar) {
                this.ar = ar;
            }
        }


        public static class SkuInfo implements Serializable {
            private List<PropertiesBean> properties;
            private LinkedHashMap<String, StockInfoBean> stockInfo;

            public List<PropertiesBean> getProperties() {
                return properties;
            }

            public void setProperties(List<PropertiesBean> properties) {
                this.properties = properties;
            }

            public LinkedHashMap<String, StockInfoBean> getStockInfo() {
                return stockInfo;
            }

            public void setStockInfo(LinkedHashMap<String, StockInfoBean> stockInfo) {
                this.stockInfo = stockInfo;
            }

            public static class PropertiesBean implements Serializable{
                private String propertyName;
                private List<PropertyValueListBean> propertyValueList;

                public String getPropertyName() {
                    return propertyName;
                }

                public void setPropertyName(String propertyName) {
                    this.propertyName = propertyName;
                }

                public List<PropertyValueListBean> getPropertyValueList() {
                    return propertyValueList;
                }

                public void setPropertyValueList(List<PropertyValueListBean> propertyValueList) {
                    this.propertyValueList = propertyValueList;
                }

                public static class PropertyValueListBean implements Serializable{
                    private String k;
                    private String v;

                    public String getK() {
                        return k;
                    }

                    public void setK(String k) {
                        this.k = k;
                    }

                    public String getV() {
                        return v;
                    }

                    public void setV(String v) {
                        this.v = v;
                    }
                }
            }

            public static class StockInfoBean implements Serializable{
                private String image;
                private String quantity;
                private String price;
                private String skuId;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getQuantity() {
                    return quantity;
                }

                public void setQuantity(String quantity) {
                    this.quantity = quantity;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getSkuId() {
                    return skuId;
                }

                public void setSkuId(String skuId) {
                    this.skuId = skuId;
                }
            }
        }

    }

    public static class ShopInfoBean implements Serializable{
        private String shopId;
        private String shopName;
        private String sales;
        private String favCount;
        private String itemCount;
        private boolean isFav;
        private String logo;
        private String link;
        private DsrBean dsr;
        private String discountInfo;

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

        public String getItemCount() {
            return itemCount;
        }

        public void setItemCount(String itemCount) {
            this.itemCount = itemCount;
        }

        public boolean isIsFav() {
            return isFav;
        }

        public void setIsFav(boolean isFav) {
            this.isFav = isFav;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public DsrBean getDsr() {
            return dsr;
        }

        public void setDsr(DsrBean dsr) {
            this.dsr = dsr;
        }

        public String getDiscountInfo() {
            return discountInfo;
        }

        public void setDiscountInfo(String discountInfo) {
            this.discountInfo = discountInfo;
        }

        public static class DsrBean implements Serializable{
            /**
             * v : 4.5
             * d : 低
             */

            private MsBean ms;
            /**
             * v : 4.5
             * d : 低
             */

            private ZlBean zl;
            /**
             * v : 4.5
             * d : 低
             */

            private JgBean jg;

            public MsBean getMs() {
                return ms;
            }

            public void setMs(MsBean ms) {
                this.ms = ms;
            }

            public ZlBean getZl() {
                return zl;
            }

            public void setZl(ZlBean zl) {
                this.zl = zl;
            }

            public JgBean getJg() {
                return jg;
            }

            public void setJg(JgBean jg) {
                this.jg = jg;
            }

            public static class MsBean implements Serializable{
                private String v;
                private String d;

                public String getV() {
                    return v;
                }

                public void setV(String v) {
                    this.v = v;
                }

                public String getD() {
                    return d;
                }

                public void setD(String d) {
                    this.d = d;
                }
            }

            public static class ZlBean implements Serializable{
                private String v;
                private String d;

                public String getV() {
                    return v;
                }

                public void setV(String v) {
                    this.v = v;
                }

                public String getD() {
                    return d;
                }

                public void setD(String d) {
                    this.d = d;
                }
            }

            public static class JgBean implements Serializable{
                private String v;
                private String d;

                public String getV() {
                    return v;
                }

                public void setV(String v) {
                    this.v = v;
                }

                public String getD() {
                    return d;
                }

                public void setD(String d) {
                    this.d = d;
                }
            }
        }
    }
}
