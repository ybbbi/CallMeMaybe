package com.ybbbi.safe.database.dao;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class addressDB_DAO {
	public static String address(Context context, String num) {
		String address="";
		File file = new File(context.getFilesDir(), "address.db");

		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
		// select location from data2 where id=(select outkey from data1 where
		// id=1300001)
		Cursor cursor = database.rawQuery(
				"select location from data2 where id=(select outkey from data1 where id=?)",
				new String[] { num.substring(0, 7)});
		if(cursor.moveToNext()){
			 address = cursor.getString(0);
		}
		cursor.close();
		database.close();
		return address;
	
	}
}
