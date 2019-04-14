package com.ybbbi.safe.bean;

import java.util.ArrayList;

public class commNumFatherInfo {
	public String name;
	public int idx;
	public ArrayList<commNumSonInfo> list;
	public commNumFatherInfo(String name, int idx,ArrayList<commNumSonInfo> list1) {
		super();
		this.name = name;
		this.idx = idx;
		this.list=list1;
		
	}
	@Override
	public String toString() {
		return "commNumFatherInfo [name=" + name + ", idx=" + idx + ", list="
				+ list + "]";
	}

	
}
