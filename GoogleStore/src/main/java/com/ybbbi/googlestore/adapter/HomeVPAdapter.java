package com.ybbbi.googlestore.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.global.OPTIONS;

import java.util.ArrayList;

/**
 * ybbbi
 * 2019-06-24 10:21
 */
public class HomeVPAdapter extends BasePagerAdapter {

    public HomeVPAdapter(ArrayList<String> list) {
        super(list);
    }

    @Override
    public Object instantiateItem( ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX+list.get(position),imageView, OPTIONS.options);
        container.addView(imageView);
        return imageView;
    }
}
