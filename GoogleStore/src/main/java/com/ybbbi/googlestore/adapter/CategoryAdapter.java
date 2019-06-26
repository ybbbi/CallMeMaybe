package com.ybbbi.googlestore.adapter;

import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.bean.CategoryBean;
import com.ybbbi.googlestore.global.OPTIONS;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ybbbi
 * 2019-06-25 10:16
 */
public class CategoryAdapter extends MyBaseAdapter {
    public final int ITEM_TITLE = 0;
    public final int ITEM_SUB = 1;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public CategoryAdapter(ArrayList list) {
        super(list);
    }

    @Override
    public int getItemViewType(int position) {
        Object o = list.get(position);
        if (o instanceof String) {
            return ITEM_TITLE;
        } else {
            return ITEM_SUB;
        }

    }

    @Override
    public void setLayoutValue(Object o, Object holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ITEM_TITLE:
                titleViewHolder h = (titleViewHolder) holder;

                h.tvTitle.setText((String) o);
                break;
            case ITEM_SUB:
                subViewHolder sh = (subViewHolder) holder;
                CategoryBean.InfosBean infosBean = (CategoryBean.InfosBean) o;

                sh.tvName1.setText(infosBean.name1);
                ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX + infosBean.url1, sh.ivImage1, OPTIONS.options);

                ViewGroup parent2 = (ViewGroup) sh.tvName2.getParent();
                if (!TextUtils.isEmpty(infosBean.name2) &&! TextUtils.isEmpty(infosBean.url2)) {
                    sh.tvName2.setText(infosBean.name2);
                    ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX + infosBean.url2, sh.ivImage2, OPTIONS.options);

                    parent2.setVisibility(View.VISIBLE);
                } else {
                    parent2.setVisibility(View.GONE);
                }

                ViewGroup parent3 = (ViewGroup) sh.tvName3.getParent();
                if (!TextUtils.isEmpty(infosBean.name3) && !TextUtils.isEmpty(infosBean.url3)) {
                    sh.tvName3.setText(infosBean.name3);
                    ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX + infosBean.url3, sh.ivImage3, OPTIONS.options);

                    parent3.setVisibility(View.VISIBLE);
                } else {
                    parent3.setVisibility(View.GONE);
                }

                break;
        }
    }

    @Override
    public int bindLayout(int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == ITEM_TITLE) {
            return R.layout.category_title;
        } else if (itemViewType == ITEM_SUB) {
            return R.layout.category_list;
        }
        return 0;
    }

    @Override
    public Object createHolder(View convertView, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case ITEM_TITLE:
                return new titleViewHolder(convertView);

            case ITEM_SUB:
              return new subViewHolder(convertView);
        }
        return null;
    }

    static
    class titleViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        titleViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static
    class subViewHolder {
        @BindView(R.id.iv_image1)
        ImageView ivImage1;
        @BindView(R.id.tv_name1)
        TextView tvName1;
        @BindView(R.id.iv_image2)
        ImageView ivImage2;
        @BindView(R.id.tv_name2)
        TextView tvName2;
        @BindView(R.id.iv_image3)
        ImageView ivImage3;
        @BindView(R.id.tv_name3)
        TextView tvName3;

        subViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    protected void addAnimation(View convertView) {
        ViewCompat.animate(convertView).rotationXBy(360).setDuration(500).setInterpolator(new OvershootInterpolator()).start();
        super.addAnimation(convertView);
    }
}
