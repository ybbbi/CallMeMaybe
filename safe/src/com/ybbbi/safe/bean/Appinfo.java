package com.ybbbi.safe.bean;

import android.graphics.drawable.Drawable;

public class Appinfo {
	public String packagename;
	public String label;
	public Drawable icon;
	public String size;
	public Boolean system;
	public Boolean sdcard;
	public int uid;
	public String md5;
	public boolean isVirus;
	public Appinfo(String packagename, String label, Drawable icon,
			String size, Boolean system, Boolean sdcard,int uid,String md5) {
		super();
		this.uid=uid;
		this.packagename = packagename;
		this.label = label;
		this.icon = icon;
		this.size = size;
		this.system = system;
		this.sdcard = sdcard;
		this.md5=md5;
	
	}
}
