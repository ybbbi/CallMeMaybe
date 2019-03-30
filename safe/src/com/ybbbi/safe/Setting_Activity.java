package com.ybbbi.safe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.ybbbi.safe.utils.Sharedpreferences;
import com.ybbbi.safe.utils.constants;
import com.ybbbi.safe.view.SettingView;

public class Setting_Activity extends Activity {

	private SettingView setting_settingview_update;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		init();
	}

	private void init() {
		setting_settingview_update = (SettingView) findViewById(R.id.setting_settingview_update);
		
		
	
		boolean b = Sharedpreferences.getBoolean(getApplicationContext(), constants.isUpdate, false);
		setting_settingview_update.isButtonOn(b);
		setting_settingview_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*if(setting_settingview_update.Buttonstate()){
					
					setting_settingview_update.isButtonOn(false);
				}else{
					setting_settingview_update.isButtonOn(true);
					
				}*/
				setting_settingview_update.setButtonstate();
				Sharedpreferences.saveBoolean(getApplicationContext(),constants.isUpdate, setting_settingview_update.Buttonstate());
			}
		});
	}

	

}
