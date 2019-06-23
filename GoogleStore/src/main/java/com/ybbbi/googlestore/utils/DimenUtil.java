package com.ybbbi.googlestore.utils;

import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.global.MyApp;

/*
 *ybbbi
 *2019-06-19 18:37
 *
 *
 *
 */
public class DimenUtil {
    /*
     *获取dimens.xml中定义的dp值，自动转化为像素值
     */
     public static int getDimen(int resId){
         return MyApp.context.getResources().getDimensionPixelSize(resId);
     }
}
