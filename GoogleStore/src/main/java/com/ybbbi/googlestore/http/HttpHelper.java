package com.ybbbi.googlestore.http;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.ybbbi.googlestore.global.MyApp;
import com.ybbbi.googlestore.utils.LogUtil;

import java.io.IOException;

/**
 * 对okhttp进行封装
 * ybbbi
 * 2019-06-20 15:07
 */
public class HttpHelper {
    private OkHttpClient client;
    private static HttpHelper mInstance = new HttpHelper();

    private HttpHelper() {
        client = new OkHttpClient();
    }


    public static HttpHelper create() {
        return mInstance;
    }

    public void get(String url, OnResultListener listener, Activity activity) {
        String data = CacheManager.getInstance().getCacheData(url);
        if (data == null || data.length() == 0) {
            LogUtil.d("请求网络");
            requestDataFormat(url, listener, activity);
        } else {
            //缓存数据获取成功
            LogUtil.d(data);
            listener.onSuccess(data);
        }
    }

    /**
     * 从网络获取数据
     *
     * @Time:2019-06-21||13:44
     */
    private void requestDataFormat(final String url, final OnResultListener listener, final Activity activity) {

        new Thread() {
            @Override
            public void run() {

               /* Looper.prepare();
                new Handler() {
                }.post(new Runnable() {
                    @Override
                    public void run() {*/

                Request request = new Request.Builder().url(url).build();

                Call call = client.newCall(request);
                try {
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        final String string = response.body().string();
                        CacheManager.getInstance().saveCacheData(url, string);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccess(string);

                            }
                        });
                    }


                } catch (IOException e) {
                    listener.onFail(e);
                    e.printStackTrace();
                }



              /*  });
                Looper.loop();*/
                //super.run();
                //}
            }
        }.start();
    }

    /**
     * 返回数据回调函数
     *
     * @Author:ybbbi
     * @Time:2019-06-20 15:39
     */


    public interface OnResultListener {
        void onSuccess(String result);

        void onFail(Exception e);
    }
}
