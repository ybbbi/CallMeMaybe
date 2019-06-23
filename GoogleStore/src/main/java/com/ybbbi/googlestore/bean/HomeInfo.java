package com.ybbbi.googlestore.bean;

import java.util.List;

/**
 * ybbbi
 * 2019-06-21 12:29
 */
public class HomeInfo {

    public List<ListBean> list;
    public List<String> picture;

/*    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }*/

    public static class ListBean {
        /**
         * id : 1525490
         * name : 有缘网
         * packageName : com.youyuan.yyhl
         * iconUrl : app/com.youyuan.yyhl/icon.jpg
         * stars : 4
         * size : 3876203
         * downloadUrl : app/com.youyuan.yyhl/com.youyuan.yyhl.apk
         * des : 产品介绍：有缘是时下最受大众单身男女亲睐的婚恋交友软件。有缘网专注于通过轻松、
         */

        public int id;
        public String name;
        public String packageName;
        public String iconUrl;
        public float stars;
        public long size;
        public String downloadUrl;
        public String des;

       /* public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public float getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public long getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }*/
    }
}
