package com.ybbbi.safe.broad;

import com.ybbbi.safe.ProcessedManagerActivity;
import com.ybbbi.safe.manager.AppManager;
import com.ybbbi.safe.manager.ProcessManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CleanProcessReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ProcessManager.cleanScreenoff(context);
		//System.out.println("nihao");
	}

}
