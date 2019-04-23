package com.ybbbi.safe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ybbbi.safe.bean.SmsCopy;
import com.ybbbi.safe.manager.SmsManager;
import com.ybbbi.safe.manager.SmsManager.querySmsListener;
import com.ybbbi.safe.service.BlacknumService;
import com.ybbbi.safe.service.WatchDogService;
import com.ybbbi.safe.utils.blacknum_shieldUtils;
import com.ybbbi.safe.view.SettingView;

import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class Tools_Activity extends Activity implements OnClickListener {

	private SettingView search;
	private SettingView commNum;
	private SettingView readSms;
	private SettingView writeSms;
	private SettingView applock;
	private SettingView watchDog;
	private SettingView accessibility;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tools);
		init();

	}

	private void init() {
		readSms = (SettingView) findViewById(R.id.tools_sv_readsms);
		writeSms = (SettingView) findViewById(R.id.tools_sv_writesms);
		search = (SettingView) findViewById(R.id.Tools_settingview_search);
		commNum = (SettingView) findViewById(R.id.tools_commonNum);
		applock = (SettingView) findViewById(R.id.tools_sv_applock);
		watchDog = (SettingView) findViewById(R.id.tools_watchdog_sv);
		accessibility = (SettingView) findViewById(R.id.tools_accessibility);
		search.setOnClickListener(this);
		commNum.setOnClickListener(this);
		readSms.setOnClickListener(this);
		writeSms.setOnClickListener(this);
		applock.setOnClickListener(this);
		watchDog.setOnClickListener(this);
		accessibility.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.Tools_settingview_search:

			Intent intent = new Intent(Tools_Activity.this,
					SearchNum_Activity.class);
			startActivity(intent);
			break;
		case R.id.tools_commonNum:
			Intent intent2 = new Intent(Tools_Activity.this,
					CommomNum_searchActivity.class);
			startActivity(intent2);
			break;
		case R.id.tools_sv_readsms:
			final ProgressDialog p = new ProgressDialog(this);
			p.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			p.setCancelable(true);
			p.show();

			new Thread() {
				public void run() {

					SmsManager.querySMS(Tools_Activity.this,
							new querySmsListener() {

								@Override
								public void setProgress(int progress) {
									// TODO Auto-generated method stub
									p.setProgress(progress);
								}

								@Override
								public void setMax(int max) {
									// TODO Auto-generated method stub
									p.setMax(max);
								}
							});
					p.dismiss();
				};
			}.start();

			break;
		case R.id.tools_sv_writesms:
			try {
				BufferedReader bf = new BufferedReader(new FileReader(new File(
						"mnt/sdcard/sms.txt")));
				String readLine = bf.readLine();
				if (readLine != null) {

					Gson gson = new Gson();
					final List<SmsCopy> list = gson.fromJson(readLine,
							new TypeToken<List<SmsCopy>>() {
							}.getType());
					final ContentResolver content = getContentResolver();
					final ProgressDialog d = new ProgressDialog(this);
					d.setCancelable(true);
					d.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					d.setMax(list.size());
					d.show();
					new Thread() {
						public void run() {

							int progress = 0;
							for (SmsCopy sms : list) {
								ContentValues values = new ContentValues();
								values.put("address", sms.address);
								values.put("date", sms.date);
								values.put("body", sms.body);
								SystemClock.sleep(100);

								values.put("type", sms.type);
								content.insert(Uri.parse("content://sms/"),
										values);
								d.setProgress(++progress);
							}
							d.dismiss();

						};
					}.start();
				} else {
					Toast.makeText(getApplicationContext(), "备份失败,发生灾难性错误", 0)
							.show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case R.id.tools_sv_applock:
			Intent intent3 = new Intent(Tools_Activity.this,
					APPLockActivity.class);
			startActivity(intent3);
			break;

		case R.id.tools_watchdog_sv:
			Intent intent4 = new Intent(this, WatchDogService.class);
			boolean b = blacknum_shieldUtils.isRunning(this,
					"com.ybbbi.safe.service.WatchDogService");
			if (b) {
				stopService(intent4);
			} else {

				startService(intent4);
			}

			watchDog.setButtonstate();

			break;

		case R.id.tools_accessibility:
			/*
			 * <action android:name="android.intent.action.MAIN" /> <action
			 * android:name="android.settings.ACCESSIBILITY_SETTINGS" /> <!--
			 * Wtf... this action is bogus! Can we remove it? --> <action
			 * android:name="ACCESSIBILITY_FEEDBACK_SETTINGS" /> <category
			 * android:name="android.intent.category.DEFAULT" /> <category
			 * android:name="android.intent.category.VOICE_LAUNCH" />
			 * </intent-filter>
			 */
			Intent intent5 = new Intent();
			intent5.setAction("android.intent.action.MAIN");
			intent5.setAction("android.settings.ACCESSIBILITY_SETTINGS");
			// intent5.setAction("ACCESSIBILITY_FEEDBACK_SETTINGS");
			// intent5.addCategory("android.intent.category.DEFAULT");
			// intent5.addCategory("android.intent.category.VOICE_LAUNCH");

			startActivity(intent5);

			break;

		}
	}

	@Override
	protected void onResume() {
		boolean b = blacknum_shieldUtils.isRunning(this,
				"com.ybbbi.safe.service.WatchDogService");
		watchDog.isButtonOn(b);

		boolean b2 = blacknum_shieldUtils.isRunning(this,
				"com.ybbbi.safe.service.myAccessbility");
		accessibility.isButtonOn(b2);

		super.onResume();

	}

}
