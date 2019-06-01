package com.ybbbi.zhbj.pager;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ybbbi.zhbj.R;

public class BasePager {
	public Activity activity;
	public View view;
	public TextView tv_title;
	public FrameLayout mContent;
	public ImageButton title_btn_menu;
	public BasePager (Activity activity){
		this.activity=activity;
		view=initView();
	}
	public View initView(){
		view = View.inflate(activity, R.layout.basepager, null);
		tv_title = (TextView) view.findViewById(R.id.title_tv);
		mContent = (FrameLayout) view.findViewById(R.id.content);
		title_btn_menu = (ImageButton) view.findViewById(R.id.title_btn_menu);
		return view;
	}
	public void initData(){
		
	}
}
