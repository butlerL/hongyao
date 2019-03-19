package com.hongyao.hongbao.util;

import com.hongyao.hongbao.R;

import java.io.Serializable;

/**
 * Created by liaolan on 16/3/28.
 */
public class HongBaoThemeUtil {
    public static final Bean[] beans = {
            new Bean(
                    "8001",
                    "普通",
                    R.drawable.ic_hb_theme_8001,
                    R.drawable.ic_hb_theme_8001_gotten,
                    R.drawable.ic_hb_theme_8001_empty,
                    R.drawable.ic_hb_theme_8001_preview),
            new Bean(
                    "8002",
                    "新婚快乐",
                    R.drawable.ic_hb_theme_8002,
                    R.drawable.ic_hb_theme_8002_gotten,
                    R.drawable.ic_hb_theme_8002_empty,
                    R.drawable.ic_hb_theme_8002_preview),
            new Bean(
                    "8003",
                    "生日快乐",
                    R.drawable.ic_hb_theme_8003,
                    R.drawable.ic_hb_theme_8003_gotten,
                    R.drawable.ic_hb_theme_8003_empty,
                    R.drawable.ic_hb_theme_8003_preview),
            new Bean(
                    "8004",
                    "喜盈门",
                    R.drawable.ic_hb_theme_8004,
                    R.drawable.ic_hb_theme_8004_gotten,
                    R.drawable.ic_hb_theme_8004_empty,
                    R.drawable.ic_hb_theme_8004_preview),
            new Bean(
                    "8005",
                    "爱情祝福",
                    R.drawable.ic_hb_theme_8005,
                    R.drawable.ic_hb_theme_8005_gotten,
                    R.drawable.ic_hb_theme_8005_empty,
                    R.drawable.ic_hb_theme_8005_preview),
            new Bean(
                    "8006",
                    "感谢",
                    R.drawable.ic_hb_theme_8006,
                    R.drawable.ic_hb_theme_8006_gotten,
                    R.drawable.ic_hb_theme_8006_empty,
                    R.drawable.ic_hb_theme_8006_preview),
    };

    public static Bean getTheme(String themeId) {
        for (Bean item : beans) {
            if (item.code.equals(themeId)) {
                return item;
            }
        }
        return beans[0];
    }

    public static class Bean implements Serializable {
        public String code;
        public String name;
        public int drawableNormal, drawableGotten, drawableEmpty, drawablePreview;

        public Bean(String code, String name, int drawableNormal, int drawableGotten, int drawableEmpty, int drawablePreview) {
            this.code = code;
            this.name = name;
            this.drawableNormal = drawableNormal;
            this.drawableGotten = drawableGotten;
            this.drawableEmpty = drawableEmpty;
            this.drawablePreview = drawablePreview;
        }
    }
}
