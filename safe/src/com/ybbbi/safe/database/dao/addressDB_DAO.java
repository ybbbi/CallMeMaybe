package com.ybbbi.safe.database.dao;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class addressDB_DAO {
	public static String address(Context context, String num) {
		String address = "";
		File file = new File(context.getFilesDir(), "address.db");

		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
		// select location from data2 where id=(select outkey from data1 where
		// id=1300001)
		if (num.matches("^1[34578]\\d{9}$")) {

			Cursor cursor = database
					.rawQuery(
							"select location from data2 where id=(select outkey from data1 where id=?)",
							new String[] { num.substring(0, 7) });

			if (cursor.moveToNext()) {
				address = cursor.getString(0);
			}
			cursor.close();
		} else {
			switch (num.length()) {
			case 3:
				address = "报警电话";
				break;
			case 5:
				address = "客服电话";
				break;
		
			case 8:
				address = "家庭电话";
				break;
		
				
			default:
				if (num.length() >= 10 && num.length() <= 12
						&& num.startsWith("0")) {

					String a = num.substring(1, 3);
					Cursor cursor = database.rawQuery(
							"select location from data2 where area=?",
							new String[] { a });
					if (cursor.moveToNext()) {
						address = cursor.getString(0);
					} else {
						String a2 = num.substring(1, 4);
						Cursor cursor2 = database.rawQuery(
								"select location from data2 where area=?",
								new String[] { a2 });
						if (cursor2.moveToNext()) {
							address = cursor2.getString(0);
						}

					}
					break;
				}

			}
		}

		database.close();
		return address;

	}
}
