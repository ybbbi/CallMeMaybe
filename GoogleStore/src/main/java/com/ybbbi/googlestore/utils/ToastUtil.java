package com.ybbbi.googlestore.utils;

import android.widget.Toast;

import com.ybbbi.googlestore.global.MyApp;

/*
 *ybbbi
 *2019-06-19 17:43
 */public class ToastUtil {
     private static Toast toast;
     public static void showToast(String text){
         if(toast==null){
             toast=Toast.makeText(MyApp.context,text,Toast.LENGTH_SHORT );

         }
         toast.setText(text);
         toast.show();
     }
}
