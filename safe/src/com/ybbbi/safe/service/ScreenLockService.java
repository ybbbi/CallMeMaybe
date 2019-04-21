package com.ybbbi.safe.service;

import com.ybbbi.safe.manager.ProcessManager;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class ScreenLockService extends Service {

	private myreceiver receiver;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	private class myreceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			ProcessManager.cleanScreenoff(ScreenLockService.this);
		}
		
	}
	@Override
	public void onCreate() {
		super.onCreate();
		receiver = new myreceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		
		registerReceiver(receiver, filter);
		
		
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
		
	}
	
	
	

}
