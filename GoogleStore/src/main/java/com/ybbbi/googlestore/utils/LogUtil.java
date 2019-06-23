package com.ybbbi.googlestore.utils;

import android.util.Log;

/*
 *ybbbi
 *2019-06-19 17:49
 */public class LogUtil {
    //上线更改为false，即可将所有log信息停止打印
    private static boolean isDebug = true;
    /*
     * 打印log d级别
     * */

    public static void d(String msg) {
        if (isDebug) {

            Log.d("LogUtil", "d: " + msg);
        }
    }
    /*
     * 打印log e级别
     * */

    public static void e(String msg) {
        if (isDebug) {
            Log.e("LogUtil", "e: ");
        }
    }


    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

}
