package com.ybbbi.safe.service;

import com.ybbbi.safe.view.AppManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class WidgetService extends Service {

	private myreceiver m;
	private PendingIntent operation;
	private AlarmManager am;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	private class myreceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			//System.out.println("更新");
			
		}
		
	}

	@Override
	public void onCreate() {
		super.onCreate();
		am = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent();
		intent.setAction("com.ybbbi.safe.broad.WIDGET");
		operation = PendingIntent.getBroadcast(this, 3, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		am.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(),
				3000, operation);
		
		
		
		m = new myreceiver();
		
		IntentFilter filter=new IntentFilter();
		filter.addAction("com.ybbbi.safe.broad.WIDGET");
		registerReceiver(m, filter);
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(m);
		am.cancel(operation);
	}
}
