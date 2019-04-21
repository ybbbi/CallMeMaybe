package com.ybbbi.safe.broad;

import com.ybbbi.safe.service.ForeService;
import com.ybbbi.safe.utils.Sharedpreferences;
import com.ybbbi.safe.utils.constants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class BOOT_COMPLETED_broad extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		// 获取sim卡信息

		boolean boolean1 = Sharedpreferences.getBoolean(context,
				constants.CHECKBOX, false);
		if (boolean1) {
			TelephonyManager t = (TelephonyManager) context
					.getSystemService(context.TELEPHONY_SERVICE);
			String s1 = t.getSimSerialNumber();
			String s2 = Sharedpreferences.getString(context, constants.sim, "");
			if (s1 != null & s2 != null) {
				if (!s1.equals(s2)) {

					SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(Sharedpreferences.getString(context,
							constants.savenum, ""), null, "warning", null, null);
				}
			}
		}
		context.startService(new Intent(context,ForeService.class));

	}

}
