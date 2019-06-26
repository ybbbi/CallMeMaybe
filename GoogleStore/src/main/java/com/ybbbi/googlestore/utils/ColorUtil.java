package com.ybbbi.googlestore.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * 随机颜色生成
 * ybbbi
 * 2019-06-25 22:11
 */
public class ColorUtil {
    public static int randomColor(){
        Random random=new Random() ;
        int red=random.nextInt(200);
                int green=random.nextInt(200);
                        int blue=random.nextInt(200);
       return Color.rgb(red,green,blue);
    }
}
