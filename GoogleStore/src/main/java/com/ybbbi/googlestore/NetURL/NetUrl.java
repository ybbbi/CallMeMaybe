package com.ybbbi.googlestore.NetURL;

/**
 * ybbbi
 * 2019-06-20 21:23
 */
public interface NetUrl {
    /**
     * 本机主机服务器IP
     */
    String URL_HOST = "http://127.0.0.1:8090/";
    /**
     *图片地址前缀
     */
    String URL_IMAGE_PREFIX=URL_HOST+"image?name=";
    /**
     *主页地址
     */
    String URL_HOME=URL_HOST+"home?index=";
    String URL_APP=URL_HOST+"app?index=";
    String URL_GAME=URL_HOST+"game?index=";
    String URL_SUBJECT=URL_HOST+"subject?index=";
    String URL_RECOMMEND=URL_HOST+"recommend?index=0";
    String URL_CATEGORY=URL_HOST+"category?index=0";
    String URL_HOT=URL_HOST+"hot?index=0";
    String DETAIL =URL_HOST+"detail?packageName=" ;
}
