package com.ybbbi.safe.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.ybbbi.safe.database.dao.BlackListDAO;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

public class BlacknumService extends Service {

	private smsreceiver smsreceiver;
	private BlackListDAO bd;
	private TelephonyManager tm;
	private myListener listener;

	private class smsreceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Object[] objs = (Object[]) intent.getExtras().get("pdus");
			for (Object obj : objs) {
				SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
				String number = smsMessage.getOriginatingAddress();
				String content = smsMessage.getMessageBody();
				int mode = bd.query(number);
				if (mode == 0 || mode == 2) {
					abortBroadcast();
				}
			}
		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		bd = new BlackListDAO(this);

		smsreceiver = new smsreceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(0x7FFFFFFF);
		registerReceiver(smsreceiver, filter);
listener = new myListener();

		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

	}

	private class myListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// 监听电话状态
			
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:// 空闲状态
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK://通话状态,接听
				break;
			case TelephonyManager.CALL_STATE_RINGING://响铃状态
				int mode = bd.query(incomingNumber);
				if(mode==1||mode==2){
					//挂断电话
					end();
				
					deletebd(incomingNumber);
					//输出通话记录  ,数据库
					
				}
				break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}


		
	}
	private void deletebd(final String number) {
		final ContentResolver con= getContentResolver();
		final Uri url=Uri.parse("content://call_log/calls");
		//当数据改变的时候  ,调用内容观察者的onchange方法删除数据库信息
		con.registerContentObserver(url, true, new ContentObserver(new Handler()) {
			public void onChange(boolean selfChange) {
				
				con.delete(url, "number=?", new String[]{number});
				con.unregisterContentObserver(this);
			};
		});
	}
	private void end() {
		try {
			
			
			//获取字节码文件
			Class<?> forName = Class.forName("android.os.ServiceManager");
			//获得方法
			
			Method method = forName.getDeclaredMethod("getService", String.class);
			//执行方法,获得值
			IBinder invoke = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);
			ITelephony interface1 = ITelephony.Stub.asInterface(invoke);
			interface1.endCall();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(smsreceiver);
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
	}

}
