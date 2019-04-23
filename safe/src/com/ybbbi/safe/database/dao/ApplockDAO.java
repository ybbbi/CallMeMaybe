package com.ybbbi.safe.database.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.ybbbi.safe.database.AppLockConstants;
import com.ybbbi.safe.database.AppLockOpenhelper;

public class ApplockDAO {
	private AppLockOpenhelper db;
	private Context mContext;
	
	public ApplockDAO(Context context) {
		db = new AppLockOpenhelper(context);
		this.mContext=context;

	}

	public boolean insert(String packagename) {
		SQLiteDatabase database = db.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AppLockConstants.PACKAGENAME, packagename);
		long insert = database
				.insert(AppLockConstants.TABLE_NAME, null, values);

		ContentResolver resolver=mContext.getContentResolver();
		
		
	Uri uri = Uri.parse("content://com.ybbi.safe.updatedb");
		resolver.notifyChange(uri, null);
		
		return insert != -1;
	}

	public boolean delete(String packagename) {

		// delete from blacklist where number=1377777
		SQLiteDatabase database = db.getReadableDatabase();
		int delete = database.delete(AppLockConstants.TABLE_NAME,
				AppLockConstants.PACKAGENAME + "=?",
				new String[] { packagename });
		ContentResolver resolver =mContext.getContentResolver();
		Uri uri = Uri.parse("content://com.ybbbi.safe.updatedb");
		resolver.notifyChange(uri, null);
		return delete != 0;

	}

	public boolean query(String packagename) {
		SQLiteDatabase database = db.getReadableDatabase();

		Cursor cursor = database.query(AppLockConstants.TABLE_NAME,
				new String[] { AppLockConstants.PACKAGENAME },
				AppLockConstants.PACKAGENAME + "=?", new String[] { packagename
						+ "" }, null, null, null);
		boolean b = false;
		if (cursor.moveToNext()) {

			b = true;

		}
		cursor.close();
		database.close();
		return b;
	}

	public List<String> queryAll() {
		List<String> list = new ArrayList<String>();
		SQLiteDatabase database = db.getReadableDatabase();
		Cursor cursor = database.query(AppLockConstants.TABLE_NAME,
				new String[] { AppLockConstants.PACKAGENAME }, null, null,
				null, null, null);
		while (cursor.moveToNext()) {

			list.add(cursor.getString(0));
		}
		cursor.close();
		database.close();
		return list;

	}

}
