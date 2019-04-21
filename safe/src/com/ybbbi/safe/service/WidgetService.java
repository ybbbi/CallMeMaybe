package com.ybbbi.safe.service;

import com.ybbbi.safe.ProcessedManagerActivity;
import com.ybbbi.safe.R;
import com.ybbbi.safe.broad.widget;
import com.ybbbi.safe.manager.AppManager;
import com.ybbbi.safe.manager.ProcessManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.format.Formatter;
import android.view.ViewDebug.FlagToString;
import android.widget.RemoteViews;

public class WidgetService extends Service {

	private myreceiver m;
	private PendingIntent operation;
	private AlarmManager am;
	private AppWidgetManager awm;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private class myreceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			ComponentName provider = new ComponentName(context, widget.class);
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.process_widget);
			views.setTextViewText(R.id.process_count, "正在运行的软件:"
					+ ProcessManager.getProcess(context) + "个");
			views.setTextViewText(
					R.id.process_memory,
					"可用内存:"
							+ Formatter.formatFileSize(context,
									ProcessManager.getMemory(context)));
			Intent process=new Intent(context,ProcessedManagerActivity.class);
			
			PendingIntent pendingIntent =PendingIntent.getActivity(context, 51, process, PendingIntent.FLAG_UPDATE_CURRENT);
			views.setOnClickPendingIntent(R.id.process_widget_ll, pendingIntent);


			
			Intent broad =new Intent();
			broad.setAction("com.ybbbi.safe.Widgetservice");
			
			PendingIntent clear=PendingIntent.getBroadcast(context, 52, broad, PendingIntent.FLAG_UPDATE_CURRENT);
			
			views.setOnClickPendingIntent(R.id.process_btn_clear, clear);
			
			awm.updateAppWidget(provider, views);
			
			
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		awm = AppWidgetManager.getInstance(this);
		am = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent();
		intent.setAction("com.ybbbi.safe.broad.WIDGET");
		operation = PendingIntent.getBroadcast(this, 3, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		//3秒刷新
		am.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(),
				3000, operation);

		m = new myreceiver();

		IntentFilter filter = new IntentFilter();
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
