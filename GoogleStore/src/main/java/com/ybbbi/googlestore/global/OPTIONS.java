package com.ybbbi.googlestore.global;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.ybbbi.googlestore.R;

/**
 * ybbbi
 * 2019-06-23 16:30
 */
public class OPTIONS {
    public static DisplayImageOptions options= new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_default)
                .showImageForEmptyUri(R.mipmap.ic_empty_page)
                .showImageOnFail(R.mipmap.ic_default)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(2000))
            .build();

}
