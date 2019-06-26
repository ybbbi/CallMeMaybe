package com.ybbbi.googlestore.adapter;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * ybbbi
 * 2019-06-22 11:12
 */
public abstract class MyBaseAdapter<R> extends BaseAdapter {
    ArrayList<R> list;

    public MyBaseAdapter(ArrayList<R> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object holder;
        if (convertView == null) {
            convertView = convertView.inflate(parent.getContext(), bindLayout(position), null);
            holder = createHolder(convertView,position);


            convertView.setTag(holder);
        } else {
            holder = convertView.getTag();
        }
        R r = list.get(position);
        setLayoutValue(r, holder, position);
        addAnimation(convertView);
        return convertView;
    }

    protected void addAnimation(View convertView) {
        convertView.setScaleX(0.5f);
        convertView.setScaleY(0.5f);
       /* ScaleAnimation sa=new ScaleAnimation(1f,2f,1f,2f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(500);
        sa.setFillAfter(true);
        convertView.startAnimation(sa);*/
        ViewCompat
                .animate(convertView)
                .scaleX(1f).scaleY(1f)
                .setDuration(700)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }

    /**
     * 将数据设置到控件中
     *
     * @Time:2019-06-22||22:46
     */
    public abstract void setLayoutValue(R r, Object holder, int position);

    /**
     * 绑定布局
     *
     * @Time:2019-06-22||22:43
     */
    public abstract int bindLayout(int position);

    /**
     * 创建holder对象
     *
     * @Time:2019-06-22||22:43
     */
    public abstract Object createHolder(View convertView,int position);

}
