package com.ybbbi.safe.database.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ybbbi.safe.bean.BlackListInfo;
import com.ybbbi.safe.database.BlackListConstants;
import com.ybbbi.safe.database.Myopenhelper;

public class BlackListDAO {

	private Myopenhelper db;

	public BlackListDAO(Context context) {
		db = new Myopenhelper(context);

	}

	public boolean insert(String number, int mode) {
		SQLiteDatabase database = db.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(BlackListConstants.NUM, number);
		values.put(BlackListConstants.MODE, mode);
		// insert into blacklist (number) values (1399999999)
		long insert = database.insert(BlackListConstants.TABLE_NAME, null,
				values);

		return insert != -1;
	}

	public boolean delete(String number) {

		// delete from blacklist where number=1377777
		SQLiteDatabase database = db.getReadableDatabase();
		int delete = database.delete(BlackListConstants.TABLE_NAME,
				BlackListConstants.NUM + "=?", new String[] { number });
		return delete != 0;

	}

	public boolean delete1(int mode) {

		// delete from blacklist where number=1377777
		SQLiteDatabase database = db.getReadableDatabase();
		int delete = database.delete(BlackListConstants.TABLE_NAME,
				BlackListConstants.MODE + "=?", new String[] { mode + "" });
		return delete != 0;

	}

	public boolean update(String number, int mode) {
		// update blacklist set number=123 where mode=2
		SQLiteDatabase database = db.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(BlackListConstants.NUM, number);
		int update = database.update(BlackListConstants.TABLE_NAME, values,
				BlackListConstants.MODE + "=?", new String[] { mode + "" });

		return update != 0;
	}

	public boolean update1(String number, int mode) {
		// update blacklist set number=123 where mode=2
		SQLiteDatabase database = db.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(BlackListConstants.MODE, mode);
		int update = database.update(BlackListConstants.TABLE_NAME, values,
				BlackListConstants.NUM + "=?", new String[] { number });

		return update != 0;
	}

	public int query(String number) {
		// select number from blacklist where mode=2
		SQLiteDatabase database = db.getReadableDatabase();
		int mode = 3;
		Cursor cursor = database.query(BlackListConstants.TABLE_NAME,
				new String[] { BlackListConstants.MODE },
				BlackListConstants.NUM + "=?", new String[] { number + "" },
				null, null, null);
		if (cursor.moveToNext()) {

			mode = cursor.getInt(0);

		}
		cursor.close();
		database.close();
		return mode;
	}

	public ArrayList<BlackListInfo> query1() {
		ArrayList<BlackListInfo> list = new ArrayList<BlackListInfo>();
		SQLiteDatabase database = db.getReadableDatabase();
		Cursor cursor = database
				.query(BlackListConstants.TABLE_NAME, new String[] {
						BlackListConstants.NUM, BlackListConstants.MODE },
						null, null, null, null, "_id desc");
		while (cursor.moveToNext()) {
			String num = cursor.getString(0);
			int mode = cursor.getInt(1);
			BlackListInfo bl = new BlackListInfo(num, mode);
			list.add(bl);
		}
		cursor.close();
		database.close();
		return list;

	}

	public ArrayList<BlackListInfo> query2(int limit,int offset){
		ArrayList<BlackListInfo> list=new ArrayList<BlackListInfo>();
		SQLiteDatabase database = db.getReadableDatabase();
		//select number,mode from Blacklist order by _id desc limit 20 offset 0
		Cursor cursor = database.rawQuery("select number,mode from Blacklist order by _id desc limit ? offset ? ", new String[]{limit+"",offset+""});
		while(cursor.moveToNext()){
			String number = cursor.getString(0);
			int mode = cursor.getInt(1);
			BlackListInfo bl=new BlackListInfo(number,mode);
			
			list.add(bl);
		}
		return list;
	}
}
