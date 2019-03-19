package com.hongyao.hongbao.model.bean;

import java.util.List;

/**
 * Created by liaolan on 16/3/26.
 */
public class CompanyListResult {
    private CompaniesBean companies;

    public CompaniesBean getCompanies() {
        return companies;
    }

    public void setCompanies(CompaniesBean companies) {
        this.companies = companies;
    }

    public static class CompaniesBean {
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
            private String name;
            private String desc;
            private String link;
            private boolean isV;

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

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public boolean isIsV() {
                return isV;
            }

            public void setIsV(boolean isV) {
                this.isV = isV;
            }
        }
    }
}
