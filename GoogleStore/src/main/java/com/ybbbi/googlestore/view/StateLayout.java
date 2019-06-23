package com.ybbbi.googlestore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.utils.LogUtil;

/**
 * ybbbi
 * 2019-06-19 18:53
 */
public class StateLayout extends FrameLayout {
    private View successView;
    private View loadingView;
    private View errorView;

    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化view
        initView();
    }

    /**
     * 添加三个状态的View
     */
    private void initView() {
        //loadingView 加载进度条界面
        loadingView = View.inflate(getContext(), R.layout.page_loading, null);
        addView(loadingView);
        //errorView 失败界面
        errorView = View.inflate(getContext(), R.layout.page_error, null);
        addView(errorView);

        hideAllView();

        Button btn_reload = errorView.findViewById(R.id.btn_reload);
        btn_reload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新加载数据,显示loadingview，在连接网络加载数据
                showloadingView();
                // LogUtil.d("重新加载");
                if (listener != null) {
                    listener.onReload();

                }

            }
        });
    }

    /**
     * 展示成功界面
     */
    public void showsuccessView() {
        hideAllView();
        if (successView != null) {

            successView.setVisibility(View.VISIBLE);
        }
    }

    /*
    展示失败界面
     */
    public void showerrorView() {
        hideAllView();
        errorView.setVisibility(View.VISIBLE);
    }

    /*
    展示加载界面
     */
    public void showloadingView() {
        hideAllView();
        loadingView.setVisibility(View.VISIBLE);
    }

    /*
    隐藏所有view
     */
    public void bindSuccessView(View view) {
        this.successView = view;
        if (successView != null) {
            successView.setVisibility(View.GONE);
            addView(successView);
        }
    }

    public void hideAllView() {
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        if (successView != null) {
            successView.setVisibility(View.GONE);
        }
    }

    private OnReloadListener listener;

    /**
     * 回调函数，重新加载数据，交给其他类传递数据
     *
     * @Author:ybbbi
     * @Time:2019-06-20 14:01
     */

    public void setOnReloadListener(OnReloadListener listener) {
        this.listener = listener;
    }


    public interface OnReloadListener {
        void onReload();
    }
}
