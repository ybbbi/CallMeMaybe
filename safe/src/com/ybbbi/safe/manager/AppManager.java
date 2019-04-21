package com.ybbbi.safe.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ybbbi.safe.bean.Appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.format.Formatter;

public class AppManager {
	public static ArrayList<Appinfo> Appinfo(Context context) {
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
		ArrayList<Appinfo> list=new ArrayList<Appinfo>();
		for (PackageInfo packageInfo : installedPackages) {

			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			String packageName = packageInfo.packageName;
			Drawable loadIcon = applicationInfo.loadIcon(pm);
			String label = applicationInfo.loadLabel(pm).toString();
			String path = applicationInfo.sourceDir;
			long length = new File(path).length();
			
			String size = Formatter.formatFileSize(context, length);
			boolean system;
			
			boolean sdcard;
			int flags = applicationInfo.flags;
			// 利用系统标识与运算,的其本身为系统标识
			if ((flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
				system = true;
			} else {
				// 非系统程序
				system = false;
			}
			// 判断是否在sd卡中
			if ((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE) {
				sdcard = true;
			} else {
				sdcard = false;
			}
			com.ybbbi.safe.bean.Appinfo info = new Appinfo(packageName, label,
					loadIcon, size, system, sdcard);

			list.add(info);
		}
		return list;
	}
}
