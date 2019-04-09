package com.ybbbi.safe.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;

public class blacknum_shieldUtils {
	public static boolean isRunning(Context context, String className) {
		ActivityManager a = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningServices = a.getRunningServices(2000);
		for(RunningServiceInfo runningServiceInfo:runningServices){
			ComponentName service = runningServiceInfo.service;//获取服务标识	
			String className2 = service.getClassName();
			if(className.equals(className2)){
				return true;
			}
			
		}
		return false;
	}
}
