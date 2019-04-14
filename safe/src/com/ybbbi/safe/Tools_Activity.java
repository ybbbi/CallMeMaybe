package com.ybbbi.safe;

import com.ybbbi.safe.view.SettingView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Tools_Activity extends Activity implements OnClickListener{

	private SettingView search;
	private SettingView commNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tools);
		init();
		
	}

	private void init() {
		search = (SettingView) findViewById(R.id.Tools_settingview_search);
		commNum = (SettingView) findViewById(R.id.tools_commonNum);
		search.setOnClickListener(this);
		commNum.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		
		case R.id.Tools_settingview_search:
			
			Intent intent =new Intent(Tools_Activity.this,SearchNum_Activity.class);
			startActivity(intent);
			break;
		case R.id.tools_commonNum:
			Intent intent2=new Intent(Tools_Activity.this,CommomNum_searchActivity.class);
			startActivity(intent2);
			break;
		}
	}

	

}
