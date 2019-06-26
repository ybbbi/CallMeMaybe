package com.ybbbi.googlestore.bean;

import java.util.ArrayList;

/**
 * ybbbi
 * 2019-06-26 17:42
 */
public class AppInfo {
    public String author;
    public String date;
    public String downloadNum;
    public String version;
    public String downlaodUrl;
    public String iconUrl;
    public String id;
    public String name;
    public String packageName;
    public long size;
    public long stars;
    public String des;
    public ArrayList<String> screen;
    public ArrayList<safeInfo> safe;

    public static class safeInfo{
        public String safeDes;
        public String safeDesUrl;
        public String safeUrl;
        public String safeDesColor;

    }

}
