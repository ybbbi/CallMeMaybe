package com.ybbbi.googlestore.moudle;

import android.support.v4.view.ViewCompat;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.bean.AppInfo;
import com.ybbbi.googlestore.global.MyApp;
import com.ybbbi.googlestore.global.OPTIONS;

import butterknife.BindView;

/**
 * 加载到detailactivity的内容布局
 * ybbbi
 * 2019-06-27 13:05
 */

public class DetailMoudle extends BaseMoudle<AppInfo>  {

    @BindView(R.id.listview_home_iv)
    ImageView HomeIv;
    @BindView(R.id.listview_home_tvTitle)
    TextView HomeTvTitle;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.detailinfo_tv_download)
    TextView TvDownload;
    @BindView(R.id.detailinfo_tv_version)
    TextView TvVersion;
    @BindView(R.id.detailinfo_tv_date)
    TextView TvDate;
    @BindView(R.id.detailinfo_tv_size)
    TextView TvSize;
    @BindView(R.id.llinfo)
    LinearLayout llinfo;



    @Override
    public int getLayout(){
        return R.layout.detail_info;
    }
    /**
     * 绑定数据
     */
    @Override
    public void loadData(AppInfo info) {

        ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX + info.iconUrl, HomeIv, OPTIONS.options);
        HomeTvTitle.setText(info.name);

        TvSize.setText("大小 : " + Formatter.formatFileSize(MyApp.context, info.size));
        ratingbar.setRating(info.stars);
        TvDownload.setText("下载 : " + info.downloadNum);
        TvDate.setText("日期 : " + info.date);
        TvVersion.setText("版本 : " + info.version);
        llinfo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llinfo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                llinfo.setTranslationY(-llinfo.getHeight());

                ViewCompat.animate(llinfo).translationY(0).setDuration(800).setStartDelay(200).setInterpolator(new BounceInterpolator()).start();
            }
        });
    }


}
