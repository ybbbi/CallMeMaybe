package com.ybbbi.zhbj.pager;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ybbbi.zhbj.HomeActivity;
import com.ybbbi.zhbj.R;

public class BasePager {
	public Activity activity;
	public View view;
	public TextView tv_title;
	public FrameLayout mContent;
	public ImageButton title_btn_menu;
	public SlidingMenu slidingMenu;
	public ImageButton mImage;
	public BasePager (Activity activity){
		this.activity=activity;
		view=initView();
		slidingMenu = ((HomeActivity)activity).getSlidingMenu();
	}
	public View initView(){
		
		view = View.inflate(activity, R.layout.basepager, null);
		mImage = (ImageButton) view.findViewById(R.id.title_btn_image);
		tv_title = (TextView) view.findViewById(R.id.title_tv);
		mContent = (FrameLayout) view.findViewById(R.id.content);
		title_btn_menu = (ImageButton) view.findViewById(R.id.title_btn_menu);
		title_btn_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				slidingMenu.toggle();
			}
		});
		return view;
	}
	public void initData(){
		
	}
}
