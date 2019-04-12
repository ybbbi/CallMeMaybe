package com.ybbbi.safe.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.ybbbi.safe.database.dao.addressDB_DAO;
import com.ybbbi.safe.view.UserDefined_toast;

public class AddressService extends Service {

	private TelephonyManager tm;
	private mylistener listener;
	private UserDefined_toast toast;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		toast = new UserDefined_toast(this);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		listener = new mylistener();
		
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

	}

	private class mylistener extends PhoneStateListener {
		private String address;

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			// 空闲
			case TelephonyManager.CALL_STATE_IDLE:
				break;
			// 响铃
			case TelephonyManager.CALL_STATE_RINGING:
				address = addressDB_DAO.address(AddressService.this, incomingNumber);
				if(!(address==null||address.length()==0)){
					//Toast.makeText(getApplicationContext(), address, Toast.LENGTH_LONG).show();
					toast.show(incomingNumber);
				}
				break;
			// 接听
			case TelephonyManager.CALL_STATE_OFFHOOK:
				break;

			}

			super.onCallStateChanged(state, incomingNumber);
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);//关机监听
	}

}
