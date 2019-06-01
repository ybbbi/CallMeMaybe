package com.ybbbi.zhbj.fragment;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ybbbi.zhbj.HomeActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	
	public  FragmentActivity activity;
	public View view;
	public SlidingMenu slidingMenu;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		activity = getActivity();
		
		slidingMenu = ((HomeActivity)activity).getSlidingMenu();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = initView();
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initData();
		super.onActivityCreated(savedInstanceState);
	}
	public abstract View initView();  
	public abstract void initData();
}
