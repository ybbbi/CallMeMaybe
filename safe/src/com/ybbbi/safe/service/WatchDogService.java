package com.ybbbi.safe.service;

import java.util.ArrayList;
import java.util.List;

import com.ybbbi.safe.UnlockActivity;
import com.ybbbi.safe.database.dao.ApplockDAO;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.SystemClock;

public class WatchDogService extends Service {
	private boolean ServiceRUNNING;
	private ActivityManager am;
	private ApplockDAO ad;
	private String pkgname;
	private myReceiver myReceiver;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private class myReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();
			if (("com.ybbbi.safe.UNLOCK").equals(action)) {

				pkgname = intent.getStringExtra("pkgNAME");
			}
			if (Intent.ACTION_SCREEN_OFF.equals(action)) {
				pkgname = null;
			}
		}

	}

	@Override
	public void onCreate() {
		super.onCreate();
		ServiceRUNNING = true;
		am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		AppisLock();
		ad = new ApplockDAO(this);

		myReceiver = new myReceiver();

		IntentFilter filter = new IntentFilter();
		filter.addAction("com.ybbbi.safe.UNLOCK");
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		registerReceiver(myReceiver, filter);

	}

	private List<String> queryAll;

	private void AppisLock() {

		new Thread() {
			public void run() {

				while (ServiceRUNNING) {
					queryAll = ad.queryAll();
					getContentResolver().registerContentObserver(
							Uri.parse("content://com.ybbbi.safe.updatedb"),
							true, new ContentObserver(null) {
								@Override
								public void onChange(boolean selfChange) {
									queryAll.clear();
									queryAll = ad.queryAll();
								}
							});

					List<RunningTaskInfo> runningTasks = am.getRunningTasks(1);
					RunningTaskInfo runningTaskInfo = runningTasks.get(0);
					ComponentName baseActivity = runningTaskInfo.baseActivity;
					String packageName = baseActivity.getPackageName();

					// 查询数据库,是否已加锁
					if (queryAll.contains(packageName)) {
						if (!packageName.equals(pkgname)) {

							Intent intent = new Intent(WatchDogService.this,
									UnlockActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							intent.putExtra("packagename", packageName);
							startActivity(intent);
						}
					}
					SystemClock.sleep(100);

				}
			};
		}.start();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ServiceRUNNING = false;
		unregisterReceiver(myReceiver);
	}

}
