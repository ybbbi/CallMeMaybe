package com.ybbbi.safe.database.dao;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AntivirusDAO {

	public static boolean isVirus(Context context, String md5) {
		boolean b=false; 
		File file = new File(context.getFilesDir(), "antivirus.db");
		SQLiteDatabase database = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null,
				SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = database.query("datable", new String[]{"md5"}, "md5=?",new String[]{md5}	,  null, null, null);
		if(cursor.moveToNext()){
			
			b=true;
		}
		cursor.close();
		database.close();
		return b;
		
		
	}
}
