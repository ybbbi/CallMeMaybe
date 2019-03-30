package com.ybbbi.safe.database;

//create table info(_id integer primary key autoincrement ,
//number varchar(30),mode varchar(2))

public interface BlackListConstants {
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "ybbbi.db";
	public static final String TABLE_NAME = "Blacklist";
	public static final String ID = "_id";
	public static final String NUM = "number";
	public static final String MODE = "mode";
	public static final String SQL_LANGUAGE = "create table " + TABLE_NAME + "("
			+ ID + " integer primary key autoincrement, " + NUM + " varchar(30),"
			+ MODE + " varchr(2))";
}
