package com.ybbbi.safe.bean;

public class SmsCopy {
	public  String address;
	public String date;
	public String type;
	public String body;
	/**
	 * @param address
	 * @param date
	 * @param type
	 * @param body
	 */
	public SmsCopy(String address, String date, String type, String body) {
		super();
		this.address = address;
		this.date = date;
		this.type = type;
		this.body = body;
	}
	@Override
	public String toString() {
		return "SmsCopy [address=" + address + ", date=" + date + ", type="
				+ type + ", body=" + body + "]";
	}
	
}
