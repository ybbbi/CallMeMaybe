package com.ybbbi.safe;

import com.ybbbi.safe.Contact_3rd_Activity.findview;
import com.ybbbi.safe.utils.Sharedpreferences;
import com.ybbbi.safe.utils.constants;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FindActivity extends Activity {

	private TextView find_textview_reset;
	private TextView textview;
	private ImageView imageview;
	private RelativeLayout relative;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find);
		init();
	}

	private void init() {
		textview = (TextView) findViewById(R.id.textview_find);
		imageview = (ImageView) findViewById(R.id.imageview_find);
		
		//将保存的电话号码数据存储到sharedpreferences中,设置到TextView上
		String num = Sharedpreferences.getString(getApplicationContext(), constants.savenum, "");
		textview.setText(num);
		
		boolean b = Sharedpreferences.getBoolean(getApplicationContext(), constants.CHECKBOX, false);
		if(b){
			imageview.setImageResource(android.R.drawable.ic_secure);
		}else{
			imageview.setImageResource(android.R.drawable.ic_partial_secure);

		}
		
		find_textview_reset = (TextView) findViewById(R.id.find_textview_reset);
		find_textview_reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent=new Intent(FindActivity.this,SetFirstActivity.class);//此处为匿名内部类,用this会代表当前内部类,不代表activity
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.anim_enter_pre_activity, R.anim.anim_exit_pre_activity);
			}
		});
		relative = (RelativeLayout) findViewById(R.id.relative_find);
		relative.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 快速开启保护
				boolean b = Sharedpreferences.getBoolean(FindActivity.this, constants.CHECKBOX, false);
				
				if(b){
					
					Sharedpreferences.saveBoolean(FindActivity.this, constants.CHECKBOX, false);
					imageview.setImageResource(android.R.drawable.ic_partial_secure);
				}else{
					Sharedpreferences.saveBoolean(getApplicationContext(), constants.CHECKBOX, true);
					imageview.setImageResource(android.R.drawable.ic_secure);
				}
			}
		});
		
		
	}

}
