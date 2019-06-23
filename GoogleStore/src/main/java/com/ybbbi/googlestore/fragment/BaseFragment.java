package com.ybbbi.googlestore.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ybbbi.googlestore.view.StateLayout;

/*
 *ybbbi
 *2019-06-19 21:51
 */public abstract class BaseFragment extends Fragment {


    public StateLayout stateLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (stateLayout == null) {

            stateLayout = new StateLayout(getContext());
            //stateLayout.showloadingView();

            stateLayout.bindSuccessView(getSuccessView());
            stateLayout.setOnReloadListener(new StateLayout.OnReloadListener() {
                /**
                 *处理数据重新加载
                 *
                 *@Author:ybbbi
                 *@Time:2019-06-20  14:13
                 */
                @Override
                public void onReload() {
                    loadData();
                }
            });
            loadData();
        }
        return stateLayout;
    }


    /**
     * 设置view数据
     *
     * @Author:ybbbi
     * @Time:2019-06-19 22:34
     */
    public abstract View getSuccessView();

    /**
     * 设置展示view
     *
     * @Author:ybbbi
     * @Time:2019-06-19 22:35
     */
    public abstract void loadData();

}
