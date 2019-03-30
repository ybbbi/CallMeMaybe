package com.ybbbi.safe.broad;

import com.ybbbi.safe.service.GPSservice;

import android.R;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class smsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		for (Object obj : objs) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
			String from = smsMessage.getOriginatingAddress();
			String content = smsMessage.getMessageBody();
			ismessage(content,context);
		}
	}

	private void ismessage(String content,Context context) {
		DevicePolicyManager pm=(DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName cp =new ComponentName(context,deviceAdminReceiver.class);
		
		
		
		// TODO Auto-generated method stub
		if(content.equals("#*location*#")){
			System.out.println("收到发送GPS短信了");
			context.startService(new Intent(context,GPSservice.class));
			
			abortBroadcast();
			//销毁数据
		}else if(content.equals("#*wipedata*#")){
			
			if(pm.isAdminActive(cp)){
				
				pm.wipeData(0);
			}
			
			
			
			
			
			abortBroadcast();
		}else if(content.equals("#*alarm*#")){
			
			
			MediaPlayer mp=MediaPlayer.create(context, com.ybbbi.safe.R.raw.jojo);
			mp.setVolume(1.0f, 1.0f);
			mp.setLooping(true);
			mp.start();
			abortBroadcast();
		}else if(content.equals("#*lockscreen*#")){
			if(pm.isAdminActive(cp)){
				
				pm.lockNow();
				pm.resetPassword("aaa", 0);
			}
			
			abortBroadcast();
		}
	}

}
