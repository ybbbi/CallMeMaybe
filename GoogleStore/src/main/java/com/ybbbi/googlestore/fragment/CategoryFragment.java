package com.ybbbi.googlestore.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

/*
 *ybbbi
 *2019-06-19 13:43
 */public class CategoryFragment extends BaseFragment {


    @Override
    public View getSuccessView() {
        TextView tv=new TextView(getActivity());
        tv.setText(this.getClass().getSimpleName());
        tv.setTextSize(20);
        return tv;
    }

    @Override
    public void loadData() {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                stateLayout.showsuccessView();
            }
        }.sendEmptyMessageDelayed(0,3000);
    }
}
