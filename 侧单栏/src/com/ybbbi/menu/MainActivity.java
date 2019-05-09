package com.ybbbi.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ImageView imageview;
	private SlidingMenu sliding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	public void text(View v){
		TextView tv= (TextView) v;
		Toast.makeText(getApplicationContext(), tv.getText(), 0).show();
	}
	private void init() {
		imageview = (ImageView) findViewById(R.id.back);
		 sliding = (SlidingMenu) findViewById(R.id.slidingmenu);
		imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sliding.menustate();
			}
		});
	}

}
