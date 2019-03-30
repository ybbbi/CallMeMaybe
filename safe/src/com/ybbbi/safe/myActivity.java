package com.ybbbi.safe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public abstract class myActivity extends Activity {
	private GestureDetector gd;
	public void previous(View v){
		doPre();
	}
	public void next(View v){
		doNext();
	}
	private void doPre() {
		/*Intent intent=new Intent(this,SetFirstActivity.class);
		startActivity(intent);*/
		//不确定的方法定义成抽象类
		if(ButtonPre()){
			
			finish();
			overridePendingTransition(R.anim.anim_enter_pre_activity, R.anim.anim_exit_pre_activity);
		}else{
			return;
		}
		
	}
	private void doNext() {
		/*Intent intent=new Intent(this,SetThirdActivity.class);
		startActivity(intent);*/
	if(ButtonNext()){
		finish();
		overridePendingTransition(R.anim.anim_enter_next_activity, R.anim.anim_exit_next_activty);
	}else{
		return;
	}
		
	}
	protected abstract boolean ButtonPre();
	protected abstract boolean ButtonNext();
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		doPre();
		
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		gd = new GestureDetector(getApplicationContext(), new listener());
		
		
	}
	private class listener extends SimpleOnGestureListener{

		@Override//e1为按下,e2为抬起
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			int downY=(int)e1.getRawY();
			int upY=(int)e2.getRawY();
			
			if(Math.abs(downY-upY)>60){
				return true;
			}
			
			int downX = (int) e1.getRawX();
			int upX = (int) e2.getRawX();
			if(downX-upX>0){
				doNext();
			}if(downX-upX<0){
				doPre();
			}
			
			
			return true;
		}
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		gd.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}
