package com.ybbbi.safe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.ybbbi.safe.service.AddressService;
import com.ybbbi.safe.service.BlacknumService;
import com.ybbbi.safe.utils.Sharedpreferences;
import com.ybbbi.safe.utils.blacknum_shieldUtils;
import com.ybbbi.safe.utils.constants;
import com.ybbbi.safe.view.SettingView;

public class Setting_Activity extends Activity {

	private SettingView setting_settingview_update;
	private SettingView blacknum_shield;
	private SettingView addrservice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		init();
	}

	private void init() {
		// 号码来电显示,开启服务并设置按钮显示
		addrservice = (SettingView) findViewById(R.id.addressService_sv);
		address();

		setting_settingview_update = (SettingView) findViewById(R.id.setting_settingview_update);
		blacknum_shield = (SettingView) findViewById(R.id.blacknum_shield);

		boolean b = Sharedpreferences.getBoolean(getApplicationContext(),
				constants.isUpdate, false);
		setting_settingview_update.isButtonOn(b);
		update_version();
		shield();
	}

	private void address() {
		final Intent intent = new Intent(this, AddressService.class);
		addrservice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean b = blacknum_shieldUtils.isRunning(
						Setting_Activity.this,
						"com.ybbbi.safe.service.AddressService");
				if (b) {
					stopService(intent);
				} else {

					startService(intent);
				}

				addrservice.setButtonstate();
			}
		});
	}

	private void shield() {
		final Intent intent = new Intent(this, BlacknumService.class);
		blacknum_shield.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean b = blacknum_shieldUtils.isRunning(
						Setting_Activity.this,
						"com.ybbbi.safe.service.BlacknumService");
				if (b) {
					stopService(intent);
				} else {

					startService(intent);
				}

				blacknum_shield.setButtonstate();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在界面可操作的时候获取服务是否开启信息,设置按钮图片
		boolean b = blacknum_shieldUtils.isRunning(this,
				"com.ybbbi.safe.service.BlacknumService");
		blacknum_shield.isButtonOn(b);
		boolean b2 = blacknum_shieldUtils.isRunning(this,
				"com.ybbbi.safe.service.AddressService");
		addrservice.isButtonOn(b2);
	}

	private void update_version() {
		setting_settingview_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * if(setting_settingview_update.Buttonstate()){
				 * 
				 * setting_settingview_update.isButtonOn(false); }else{
				 * setting_settingview_update.isButtonOn(true);
				 * 
				 * }
				 */
				setting_settingview_update.setButtonstate();
				Sharedpreferences.saveBoolean(getApplicationContext(),
						constants.isUpdate,
						setting_settingview_update.Buttonstate());
			}
		});
	}

}
