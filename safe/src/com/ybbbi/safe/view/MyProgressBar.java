package com.ybbbi.safe.view;

import com.ybbbi.safe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyProgressBar extends RelativeLayout {

	private View v;
	private TextView memory;
	private ProgressBar probar;
	private TextView tv_left;
	private TextView tv_right;

	public MyProgressBar(Context context) {
		this(context, null);
	}

	public MyProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		v = View.inflate(context, R.layout.myprogressbar, null);
		addView(v);
		init();
	}

	private void init() {
		memory = (TextView) v.findViewById(R.id.myprogressbar_tv_memory);
		probar = (ProgressBar) v.findViewById(R.id.progressBar1);
		tv_left = (TextView) v.findViewById(R.id.myprogressbar_tv_use);
		tv_right = (TextView) v.findViewById(R.id.myprogressbar_tv_useless);
	}

	public void setText(String title) {
		memory.setText(title);
	}

	public void setText_left(String left) {
		tv_left.setText(left);
	}

	public void setText_right(String right) {
		tv_right.setText(right);
	}
	public void setProgressBar(int progress){
		probar.setProgress(progress);
	}

}
