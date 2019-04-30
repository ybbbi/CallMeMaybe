package com.ybbbi.button;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener {

	private Button home;
	private Button menu;
	private boolean animationShow;
	private boolean isShow3 = true;

	private boolean isShow2 = true;
	private RelativeLayout level3;
	private RelativeLayout level2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();

	}

	private void init() {
		home = (Button) findViewById(R.id.button1_level1);
		menu = (Button) findViewById(R.id.button3_level2);
		
		level3 = (RelativeLayout) findViewById(R.id.rl_level3);
		level2 = (RelativeLayout) findViewById(R.id.rl_level2);
		
		home.setOnClickListener(this);
		menu.setOnClickListener(this);

		
		
	}

	private void hideAnimation(View view) {
		AnimationSet as = new AnimationSet(true);
		RotateAnimation r = new RotateAnimation(0, 180,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				1.0f);
		r.setDuration(1000);
		// r.setFillAfter(true);
		AlphaAnimation a = new AlphaAnimation(1, 0);
		a.setDuration(1000);
		// a.setFillAfter(true);
		as.addAnimation(r);
		as.addAnimation(a);
		as.setFillAfter(true);
		as.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				animationShow = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				animationShow = false;
			}
		});
		

			view.startAnimation(as);
		
	}

	private void showAnimation(View view) {
		AnimationSet as = new AnimationSet(true);
		RotateAnimation r = new RotateAnimation(180, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				1.0f);
		r.setDuration(1000);
		// r.setFillAfter(true);
		AlphaAnimation a = new AlphaAnimation(0, 1);
		a.setDuration(1000);
		// a.setFillAfter(true);
		as.addAnimation(r);
		as.addAnimation(a);
		as.setFillAfter(true);
		as.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				animationShow = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				animationShow = false;
			}
		});
		

			view.startAnimation(as);
		
	}

	private void clickable(View v, boolean isClickable) {
		v.setEnabled(isClickable);
		if (v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) v;
			for (int i = 0; i < vg.getChildCount(); i++) {
				View childAt = vg.getChildAt(i);
				childAt.setEnabled(isClickable);
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1_level1:
			// 主页
			if(animationShow){
				return;
			}
			if (isShow3) {
				// 隐藏所有
				hideAnimation(level3);
				clickable(level3, false);
				/*
				 * new Handler().postDelayed(new Runnable() {
				 * 
				 * @Override public void run() { // TODO Auto-generated method
				 * stub hideAnimation(level2); } }, 300);
				 */
				new Handler() {
					public void handleMessage(android.os.Message msg) {
						hideAnimation(level2);
						clickable(level2, false);
					};
				}.sendEmptyMessageDelayed(0, 300);

				isShow3 = false;
				isShow2 = false;
			} else if (isShow2) {
				// 隐藏二级
				hideAnimation(level2);
				clickable(level2, false);
				isShow2 = false;

			} else {
				isShow2 = true;
				// 显示二级

				showAnimation(level2);
				clickable(level2, true);
			}
			break;

		case R.id.button3_level2:
			// 菜单
			if(animationShow){
				return;
			}
			if (isShow3) {
				// 隐藏三级
				clickable(level3, false);
				hideAnimation(level3);

			} else {

				// 显示三级
				showAnimation(level3);
				clickable(level3, true);
			}
			isShow3 = !isShow3;
			break;
		}
	}

}
