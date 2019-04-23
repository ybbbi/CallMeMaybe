package com.ybbbi.safe.service;

import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.view.accessibility.AccessibilityEvent;

import com.ybbbi.safe.UnlockActivity;
import com.ybbbi.safe.database.dao.ApplockDAO;

public class myAccessbility extends AccessibilityService {
	private myReceiver myReceiver;
	private List<String> queryAll;
	private ApplockDAO ad;
	private String unlockpkgname;

	@Override
	public void onCreate() {
		super.onCreate();
		ad=new ApplockDAO(this);
		queryAll = ad.queryAll();
		getContentResolver().registerContentObserver(
				Uri.parse("content://com.ybbbi.safe.updatedb"), true,
				new ContentObserver(null) {
					@Override
					public void onChange(boolean selfChange) {
						queryAll.clear();
						queryAll = ad.queryAll();
					}
				});

		myReceiver = new myReceiver();

		IntentFilter filter = new IntentFilter();
		filter.addAction("com.ybbbi.safe.UNLOCK");
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		registerReceiver(myReceiver, filter);
	}

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		// TODO Auto-generated method stub
		String pkgname = event.getPackageName().toString();
		if (queryAll.contains(pkgname)) {
			if (!pkgname.equals(unlockpkgname)) {

				Intent intent = new Intent(myAccessbility.this,
						UnlockActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("packagename", pkgname);
				startActivity(intent);
			}
		}

	}

	@Override
	public void onInterrupt() {
		// TODO Auto-generated method stub

	}

	private class myReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();
			if (("com.ybbbi.safe.UNLOCK").equals(action)) {

				unlockpkgname = intent.getStringExtra("pkgNAME");
			}
			if (Intent.ACTION_SCREEN_OFF.equals(action)) {
				unlockpkgname = null;
			}
		}

	}
	@Override
	public void onDestroy() {
		unregisterReceiver(myReceiver);
		super.onDestroy();
	}

}
