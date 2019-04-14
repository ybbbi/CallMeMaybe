package com.ybbbi.safe.bean;

import android.graphics.drawable.Drawable;

public class Appinfo {
	public String packagename;
	public String label;
	public Drawable icon;
	public String size;
	public Boolean system;
	public Boolean sdcard;
	public Appinfo(String packagename, String label, Drawable icon,
			String size, Boolean system, Boolean sdcard) {
		super();
		this.packagename = packagename;
		this.label = label;
		this.icon = icon;
		this.size = size;
		this.system = system;
		this.sdcard = sdcard;
	}
}
