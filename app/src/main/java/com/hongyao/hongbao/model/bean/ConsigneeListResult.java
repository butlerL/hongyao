package com.hongyao.hongbao.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liaolan on 16/3/30.
 */
public class ConsigneeListResult {
    private ConsigneesBean consignees;

    public ConsigneesBean getConsignees() {
        return consignees;
    }

    public void setConsignees(ConsigneesBean consignees) {
        this.consignees = consignees;
    }

    public static class ConsigneesBean {
        private boolean isEnd;
        private String wp;
        private List<Consignee> list;

        public boolean isEnd() {
            return isEnd;
        }

        public void setEnd(boolean end) {
            isEnd = end;
        }

        public String getWp() {
            return wp;
        }

        public void setWp(String wp) {
            this.wp = wp;
        }

        public List<Consignee> getList() {
            return list;
        }

        public void setList(List<Consignee> list) {
            this.list = list;
        }

        public static class Consignee implements Serializable{
            private String consigneeId;
            private String province;
            private String city;
            private String address;
            private String name;
            private String phone;
            private boolean isDefault;

            public String getConsigneeId() {
                return consigneeId;
            }

            public void setConsigneeId(String consigneeId) {
                this.consigneeId = consigneeId;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
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

            public boolean isDefault() {
                return isDefault;
            }

            public void setDefault(boolean aDefault) {
                isDefault = aDefault;
            }
        }
    }
}
