package com.ybbbi.googlestore.http;

import com.ybbbi.googlestore.global.MyApp;
import com.ybbbi.googlestore.utils.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

/**
 * 缓存数据管理类:
 * 1.将网络返回的json数据缓存到sdcard中
 * 2.维护缓存数据有效区
 * ybbbi
 * 2019-06-21 10:49
 */
public class CacheManager {
    //data/data/包名/cache
    public static final String CACHE_DIR = "data/data/" + MyApp.context.getPackageName() + File.separator + "cache";
    private static CacheManager manager = new CacheManager();


    private CacheManager() {
        //文件目录
        File dir = new File(CACHE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static CacheManager getInstance() {
        return manager;
    }

    /**
     * 保存缓存数据
     *
     * @Time:2019-06-21||11:30
     */
    public  void saveCacheData(String url, String json) {
        //文件
        try {
            File file = new File(CACHE_DIR, URLEncoder.encode(url.substring(url.indexOf("?"))));
            if (!file.exists()) {

                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public  String getCacheData(String url) {
        StringBuffer sb = new StringBuffer();

        try {
            File file = new File(CACHE_DIR, URLEncoder.encode(url.substring(url.indexOf("?"))));
            if (file.exists()) {

                //再次判断文件是否有效
                if (isValid(file)) {

                    FileInputStream stream = new FileInputStream(file);

                    InputStreamReader isr = new InputStreamReader(stream);
                    //FileReader reader=new FileReader(file);
                    BufferedReader br = new BufferedReader(isr);
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    stream.close();
                    isr.close();
                    // reader.close();
                } else {
                    //文件无效删除文件
                    LogUtil.d("文件失效");
                    file.delete();
                    return null;
                }


            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * 判断文件是否有效
     *
     * @Time:2019-06-21||23:27
     */
    private boolean isValid(File file) {
        long validTime = System.currentTimeMillis() - file.lastModified();

        return validTime < 1000*60*60 ;
    }


}
