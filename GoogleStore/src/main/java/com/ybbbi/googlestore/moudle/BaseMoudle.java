package com.ybbbi.googlestore.moudle;

import android.view.View;

import com.ybbbi.googlestore.global.MyApp;

import butterknife.ButterKnife;

/**
 *
 * ybbbi
 * 2019-06-27 13:26
 */
public abstract class BaseMoudle<T> {
    protected View moudleView;

    public BaseMoudle() {
      moudleView=View.inflate(MyApp.context,getLayout(),null);
        ButterKnife.bind(this, moudleView);
    }

    public View getView() {
        return moudleView;
    }

    /**
     *  初始化加载布局
     */
    public abstract int getLayout();

    /**
     * 根据泛型加载数据
     * @param t
     */
    public abstract void loadData(T t);

}
