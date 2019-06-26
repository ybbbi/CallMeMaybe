package com.ybbbi.googlestore.activity;

import android.icu.util.Measure;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.bean.AppInfo;
import com.ybbbi.googlestore.global.OPTIONS;
import com.ybbbi.googlestore.http.HttpHelper;
import com.ybbbi.googlestore.view.StateLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ybbbi
 * 2019-06-26 14:51
 */
public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.listview_home_iv)
    ImageView listviewHomeIv;
    @BindView(R.id.listview_home_tvTitle)
    TextView listviewHomeTvTitle;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.detailinfo_tv_download)
    TextView detailinfoTvDownload;
    @BindView(R.id.detailinfo_tv_version)
    TextView detailinfoTvVersion;
    @BindView(R.id.detailinfo_tv_date)
    TextView detailinfoTvDate;
    @BindView(R.id.detailinfo_tv_size)
    TextView detailinfoTvSize;
    @BindView(R.id.llinfo)
    LinearLayout llinfo;
    private StateLayout stateLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String packageName = getIntent().getStringExtra("packageName");
        stateLayout = new StateLayout(this);
        setacionbar();
        setContentView(stateLayout);
        stateLayout.bindSuccessView(getsuccessView());
        stateLayout.showloadingView();
        loadData(packageName);
    }

    private void setacionbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_detail));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取成功的视图
     *
     * @return
     */
    private View getsuccessView() {
        View view = View.inflate(this, R.layout.detail, null);
        ButterKnife.bind(this, view);

        return view;
    }

    /**
     * 加载数据
     */
    private void loadData(String packName) {
        HttpHelper.create().get(NetUrl.DETAIL + packName, new HttpHelper.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Gson json = new Gson();


                AppInfo info = json.fromJson(result, AppInfo.class);
                if (info != null) {
                    ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX + info.iconUrl, listviewHomeIv, OPTIONS.options);
                    listviewHomeTvTitle.setText(info.name);
                    detailinfoTvSize.setText("大小:" + Formatter.formatFileSize(DetailActivity.this, info.size));
                    ratingbar.setRating(info.stars);
                    detailinfoTvDownload.setText("下载：" + info.downloadNum);
                    detailinfoTvDate.setText("日期：" + info.date);
                    detailinfoTvVersion.setText("版本：" + info.version);
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

            @Override
            public void onFail(Exception e) {

            }
        }, this);
        stateLayout.showsuccessView();
    }


}
