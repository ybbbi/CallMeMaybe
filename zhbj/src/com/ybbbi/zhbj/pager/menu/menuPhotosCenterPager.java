package com.ybbbi.zhbj.pager.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


public class menuPhotosCenterPager extends baseMenuPager {

	public menuPhotosCenterPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView() {
		TextView textview=new TextView(activity);
		textview.setText("组图详情");
		textview.setTextSize(20);
		textview.setGravity(Gravity.CENTER);
		textview.setTextColor(Color.RED);
		return textview;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
	
}
