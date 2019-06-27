package com.ybbbi.googlestore.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;


import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.global.MyApp;
import com.ybbbi.googlestore.global.OPTIONS;

import java.util.ArrayList;

/**
 * ybbbi
 * 2019-06-27 19:50
 */
public class FullImageAdapter extends BasePagerAdapter {
    public FullImageAdapter(ArrayList<String> list) {
        super(list);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PhotoView pv=new PhotoView(MyApp.context);
       ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX+list.get(position),pv, OPTIONS.options);
       container.addView(pv);

        return pv;
    }
}
