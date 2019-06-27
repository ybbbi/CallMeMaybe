package com.ybbbi.googlestore.moudle;

import android.animation.ValueAnimator;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.animate.AnimateHeight;
import com.ybbbi.googlestore.bean.AppInfo;
import com.ybbbi.googlestore.global.OPTIONS;
import com.ybbbi.googlestore.utils.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * ybbbi
 * 2019-06-27 13:40
 */
public class TagMoudle extends BaseMoudle<AppInfo> implements View.OnClickListener {


    @BindView(R.id.iv_image1)
    ImageView ivImage1;
    @BindView(R.id.iv_image2)
    ImageView ivImage2;
    @BindView(R.id.iv_image3)
    ImageView ivImage3;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.iv_icon1)
    ImageView ivIcon1;
    @BindView(R.id.tv_text1)
    TextView tvText1;
    @BindView(R.id.iv_icon2)
    ImageView ivIcon2;
    @BindView(R.id.tv_text2)
    TextView tvText2;
    @BindView(R.id.iv_icon3)
    ImageView ivIcon3;
    @BindView(R.id.tv_text3)
    TextView tvText3;
    @BindView(R.id.ll_tag)
    LinearLayout llTag;

    @Override
    public int getLayout() {
        return R.layout.tag;
    }

    @Override
    public void loadData(AppInfo info) {
        moudleView.setOnClickListener(this);
        ArrayList<AppInfo.safeInfo> safe = info.safe;
        AppInfo.safeInfo safeInfo = safe.get(0);
        ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX + safeInfo.safeUrl, ivImage1, OPTIONS.options);
        ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX + safeInfo.safeDesUrl, ivIcon1, OPTIONS.options);
        tvText1.setText(safeInfo.safeDes);
        if (safe.size() > 1) {
            AppInfo.safeInfo safeInfo1 = safe.get(1);
            ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX + safeInfo1.safeUrl, ivImage2, OPTIONS.options);
            ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX + safeInfo1.safeDesUrl, ivIcon2, OPTIONS.options);
            tvText2.setText(safeInfo1.safeDes);
        } else {
            ((ViewGroup) tvText2.getParent()).setVisibility(View.GONE);
        }
        if (safe.size() > 2) {
            AppInfo.safeInfo safeInfo2 = safe.get(2);
            ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX + safeInfo2.safeUrl, ivImage3, OPTIONS.options);
            ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX + safeInfo2.safeDesUrl, ivIcon3, OPTIONS.options);
            tvText3.setText(safeInfo2.safeDes);
        } else {
            ((ViewGroup) tvText3.getParent()).setVisibility(View.GONE);

        }
        llTag.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llTag.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                height=llTag.getHeight();
                ViewGroup.LayoutParams params = llTag.getLayoutParams();

                params.height=0;
                llTag.setLayoutParams(params);
            }
        });

    }
    private int height;
    private boolean isExpanding=false;
    private AnimateHeight animator;
    private  boolean israt;
    @Override
    public void onClick(View v) {
        if(israt){
            return;
        }
        if(isExpanding){
             animator=new AnimateHeight(height,0,llTag);


        }else{

            animator=new AnimateHeight(0,height,llTag);


        }
        animator.start(500);
        isExpanding=!isExpanding;
        ViewCompat.animate(ivArrow).rotationBy(180).setListener(new ViewPropertyAnimatorListener(){

            @Override
            public void onAnimationStart(View view) {
                israt=true;
            }

            @Override
            public void onAnimationEnd(View view) {israt=false;
                }

            @Override
            public void onAnimationCancel(View view) {

            }
        }).setDuration(500).start();

    }
}
