package com.ybbbi.zhbj.pager.menu;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ybbbi.zhbj.HomeActivity;

import android.app.Activity;
import android.view.View;

public abstract class baseMenuPager {
	public Activity activity;
	public View view;
	public SlidingMenu slidingMenu;
	public baseMenuPager(Activity activity){
		slidingMenu = ((HomeActivity)activity).getSlidingMenu();
		this.activity=activity;
		view = initView();
	}
	
	
	
	public abstract View initView();
		
	
	public  abstract void initData();
	
	
}
