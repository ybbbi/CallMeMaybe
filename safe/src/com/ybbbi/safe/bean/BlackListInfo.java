package com.ybbbi.safe.bean;

public class BlackListInfo {
	public String number;
	public int mode;
	public BlackListInfo(String number, int mode) {
		super();
		this.number = number;
		this.mode = mode;
	}
	@Override
	public String toString() {
		return "BlackListInfo [number=" + number + ", mode=" + mode + "]";
	}
	
}
