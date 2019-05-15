package com.ybbbi.zhbj;

import com.ybbbi.zhbj.tool.Constants;
import com.ybbbi.zhbj.tool.SharedpreferencesTool;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

	private RelativeLayout splash_rl_root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		init();
	}

	private void init() {
		splash_rl_root = (RelativeLayout) findViewById(R.id.splash_rl_root);
		setAnimation();

	}

	private void setAnimation() {
		// 旋转
		RotateAnimation rAnimation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rAnimation.setDuration(2000);
		rAnimation.setFillAfter(true);

		ScaleAnimation sAnimation = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		sAnimation.setDuration(2000);
		sAnimation.setFillAfter(true);

		AlphaAnimation aAnimation = new AlphaAnimation(0, 1);
		aAnimation.setDuration(2500);
		aAnimation.setFillAfter(true);

		AnimationSet animationset = new AnimationSet(true);
		animationset.addAnimation(aAnimation);
		animationset.addAnimation(sAnimation);
		animationset.addAnimation(rAnimation);
		splash_rl_root.startAnimation(animationset);

		animationset.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				SharedpreferencesTool spt = new SharedpreferencesTool();
				boolean isfirstin = spt.getBoolean(getApplicationContext(), Constants.isFirst, true);
				if(isfirstin){
					Intent intent=new Intent(SplashActivity.this,GuideActivity.class);
					startActivity(intent);
					
				}
				else{
					Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
					startActivity(intent);
				}
				finish();
			}
		});
	}

}
