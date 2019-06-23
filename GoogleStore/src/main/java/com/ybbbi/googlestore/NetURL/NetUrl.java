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
}
