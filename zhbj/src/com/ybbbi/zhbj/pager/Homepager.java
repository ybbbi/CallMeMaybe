package com.ybbbi.zhbj.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ybbbi.zhbj.R;


public class Homepager extends BasePager {

	public Homepager(Activity activity) {
		super(activity);
	}
	@Override
	public void initData() {
		TextView tv=new TextView(activity);
		tv.setText("首页");
		tv.setTextColor(Color.RED);
		tv.setTextSize(20);
		tv.setGravity(Gravity.CENTER);
		mContent.addView(tv);
		tv_title.setText("首页");
		title_btn_menu.setVisibility(View.GONE);
		super.initData();
	}

}
