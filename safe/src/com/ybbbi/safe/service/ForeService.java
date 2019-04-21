package com.ybbbi.safe.service;

import com.ybbbi.safe.R;
import com.ybbbi.safe.SplashActivity;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class ForeService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		Notification notification=new Notification();
		Intent intent=new Intent(this,SplashActivity.class);
		notification.contentIntent=PendingIntent.getActivity(this, 53, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.icon=R.drawable.logo2;
		notification.tickerText="北京第三交通委提醒您:";
		notification.contentView=new RemoteViews(getPackageName(),R.layout.foreground);
		startForeground(1, notification);
	}

}
