package com.ybbbi.safe.bean;

public class commNumSonInfo {
	public String name;
	public String number;
	public commNumSonInfo(String name,String number) {
		super();
		this.name = name;
		this.number = number;
	}
	@Override
	public String toString() {
		return "commNumSonInfo [name=" + name + ", number=" + number + "]";
	}
	
}
