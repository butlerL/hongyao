package com.hongyao.hongbao.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wjf on 16/8/11.
 */
public class SplashPageBean implements Serializable{

    private List<GuidePagesBean> guidePages;

    public List<GuidePagesBean> getGuidePages() {
        return guidePages;
    }

    public void setGuidePages(List<GuidePagesBean> guidePages) {
        this.guidePages = guidePages;
    }

    public static class GuidePagesBean implements Serializable {
        private String image;
        private String link;
        private int resImage;          // 前端添加

        public GuidePagesBean(String image, String link, int resImage) {
            this.image = image;
            this.link = link;
            this.resImage = resImage;
        }

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

        public int getResImage() {
            return resImage;
        }

        public void setResImage(int resImage) {
            this.resImage = resImage;
        }
    }
}
