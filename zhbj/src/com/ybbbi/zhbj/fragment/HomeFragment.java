package com.ybbbi.zhbj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends BaseFragment {
	
	@Override
	public View initView() {
		TextView tv=new TextView(activity);
		tv.setText("首页");
		return tv;
	}

	@Override
	public void initData() {

		
	}

}
