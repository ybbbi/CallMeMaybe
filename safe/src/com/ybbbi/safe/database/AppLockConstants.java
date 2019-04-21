package com.ybbbi.safe.database;



public interface AppLockConstants {
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "AppLockConstants.db";
	public static final String TABLE_NAME = "AppLock";
	public static final String ID = "_id";
	public static final String PACKAGENAME = "packagename";

	public static final String SQL_LANGUAGE = "create table " + TABLE_NAME + "("
			+ ID + " integer primary key autoincrement, " + PACKAGENAME + " varchar(100))"
			;
}
