package com.ybbbi.safe.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ybbbi.safe.R;
import com.ybbbi.safe.bean.ProcessAppInfo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.hardware.Camera.Size;
import android.os.Build;
import android.text.format.Formatter;

public class ProcessManager {
	public static int getProcess(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		return am.getRunningAppProcesses().size();
	}

	public static int getAllProcess(Context context) {
		Set<String> set = new HashSet<String>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> list = pm
				.getInstalledPackages(PackageManager.GET_ACTIVITIES
						| PackageManager.GET_SERVICES
						| PackageManager.GET_RECEIVERS
						| PackageManager.GET_PROVIDERS);
		for (PackageInfo packageInfo : list) {
			set.add(packageInfo.applicationInfo.processName);
			ActivityInfo[] activities = packageInfo.activities;
			if (activities != null) {
				for (ActivityInfo activityInfo : activities) {
					set.add(activityInfo.processName);
				}
			}
			ProviderInfo[] providers = packageInfo.providers;
			if (providers != null) {
				for (ProviderInfo providerInfo : providers) {
					set.add(providerInfo.processName);
				}
			}
			ActivityInfo[] receivers = packageInfo.receivers;
			if (receivers != null) {
				for (ActivityInfo activityInfo : receivers) {
					set.add(activityInfo.processName);
				}
			}
			ServiceInfo[] services = packageInfo.services;
			if (services != null) {
				for (ServiceInfo serviceInfo : services) {
					set.add(serviceInfo.processName);
				}
			}

		}

		return set.size();
	}

	public static long getMemory(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		return outInfo.availMem;

	}

	@SuppressLint("NewApi")
	public static long getMemoryAll(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		long m = -1;
		am.getMemoryInfo(outInfo);
		if (Build.VERSION.SDK_INT >= 16) {
			m = outInfo.totalMem;

		} else {
			m = getMemoryAll();

		}
		return m;
	}

	// 方法重载
	@Deprecated
	public static long getMemoryAll() {
		try {
			File file = new File("proc/meminfo");
			BufferedReader br;
			br = new BufferedReader(new FileReader(file));
			String readLine = br.readLine();
			String[] split = readLine.split(":");
			readLine = split[1];
			readLine.replace("kb", "");
			Long m = Long.valueOf(readLine);
			return m * 1024;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return 0;
	}

	public static List<ProcessAppInfo> getProcessApp(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningAppProcesses = am
				.getRunningAppProcesses();
		List<ProcessAppInfo> list = new ArrayList<ProcessAppInfo>();
		PackageManager pm = context.getPackageManager();
		for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
			ProcessAppInfo pa = new ProcessAppInfo();

			String processName = runningAppProcessInfo.processName;
			pa.packageName = processName;
			android.os.Debug.MemoryInfo[] memoryInfo = am
					.getProcessMemoryInfo(new int[] { runningAppProcessInfo.pid });
			int totalPss = memoryInfo[0].getTotalPss();
			long size = totalPss * 1024;
			String Size = Formatter.formatFileSize(context, size);
			pa.size = Size;
			try {
				ApplicationInfo applicationInfo = pm.getApplicationInfo(
						processName, 0);
				String name = applicationInfo.loadLabel(pm).toString();
				pa.name = name;
				Drawable loadIcon = applicationInfo.loadIcon(pm);
				pa.icon = loadIcon;
				int flags = applicationInfo.flags;
				boolean isSystem;
				if ((flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
					isSystem = true;
				} else {
					isSystem = false;
				}
				pa.isSystem = isSystem;

			} catch (NameNotFoundException e) {
				pa.name = processName;
				pa.icon = context.getResources()
						.getDrawable(R.drawable.defalut);
				pa.isSystem = true;
				e.printStackTrace();
			}
			list.add(pa);

		}
		return list;

	}

	public static void cleanScreenoff(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningAppProcesses = am
				.getRunningAppProcesses();
		for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
			if (!runningAppProcessInfo.processName.equals(context
					.getPackageName())) {

				am.killBackgroundProcesses(runningAppProcessInfo.processName);
			}

		}
	}

}
