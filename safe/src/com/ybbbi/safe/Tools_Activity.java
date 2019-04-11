package com.ybbbi.safe;

import com.ybbbi.safe.view.SettingView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class Tools_Activity extends Activity implements OnClickListener{

	private SettingView search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tools);
		init();
		
	}

	private void init() {
		search = (SettingView) findViewById(R.id.Tools_settingview_search);
		search.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		
		case R.id.Tools_settingview_search:
			
			Intent intent =new Intent(Tools_Activity.this,SearchNum_Activity.class);
			startActivity(intent);
			break;
		
		}
	}

	

}
