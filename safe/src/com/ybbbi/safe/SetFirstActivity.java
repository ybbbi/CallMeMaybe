package com.ybbbi.safe;

import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class SetFirstActivity extends myActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_first);
		//init();
		
	}


		
	





	@Override
	protected boolean ButtonPre() {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	protected boolean ButtonNext() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,SetSencondActivity.class);
		startActivity(intent);
		return true;
	}

	

}
