package com.ybbbi.googlestore.adapter;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.bean.HomeInfo;
import com.ybbbi.googlestore.global.MyApp;
import com.ybbbi.googlestore.global.OPTIONS;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ybbbi
 * 2019-06-22 11:14
 */
public class HomeAdapter extends MyBaseAdapter<HomeInfo.ListBean> {


    public HomeAdapter(ArrayList list) {
        super(list);


    }


    @Override
    public void setLayoutValue(HomeInfo.ListBean listBean, Object holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        HomeInfo.ListBean info = list.get(position);
        ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX + info.iconUrl, viewHolder.listviewHomeIv, OPTIONS.options);
        viewHolder.listviewHomeTvTitle.setText(info.name);
        viewHolder.listviewHomeTvinfo.setText(info.des);
        viewHolder.listviewHomeTvsize.setText(Formatter.formatFileSize(MyApp.context, info.size));
        viewHolder.ratingbar.setRating(info.stars);
        viewHolder.ratingbar.setIsIndicator(true);
    }

    @Override
    public int bindLayout(int position) {
        return R.layout.listview_home;
    }

    @Override
    public Object createHolder(View convertView) {
        return new ViewHolder(convertView);
    }
  /*  @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = convertView.inflate(parent.getContext(), R.layout.listview_home, null);
            holder = new ViewHolder(convertView);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HomeInfo.ListBean info = (HomeInfo.ListBean) list.get(position);
        holder.listviewHomeTvTitle.setText(info.name);
        holder.listviewHomeTvinfo.setText(info.des);
        holder.listviewHomeTvsize.setText(Formatter.formatFileSize(parent.getContext(),info.size));
        holder.ratingbar.setRating(info.stars);
       holder.ratingbar.setIsIndicator(true);
        return convertView;
    }*/

    static
    class ViewHolder {
        @BindView(R.id.listview_home_iv)
        ImageView listviewHomeIv;
        @BindView(R.id.listview_home_tvTitle)
        TextView listviewHomeTvTitle;
        @BindView(R.id.ratingbar)
        RatingBar ratingbar;
        @BindView(R.id.listview_home_tvsize)
        TextView listviewHomeTvsize;
        @BindView(R.id.listview_home_tvinfo)
        TextView listviewHomeTvinfo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
