package com.ybbbi.safe.database.dao;

import java.io.File;
import java.util.ArrayList;

import com.ybbbi.safe.bean.commNumFatherInfo;
import com.ybbbi.safe.bean.commNumSonInfo;
import com.ybbbi.safe.utils.constants;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class commNumDAO {
	
	//查询父框数据
	public static ArrayList<commNumFatherInfo> getFather(Context context){
		ArrayList<commNumFatherInfo> arraylist=new ArrayList<commNumFatherInfo>();
		File file =new File(context.getFilesDir(),constants.CPYDBCOMMONNUM);
		SQLiteDatabase db=SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = db.query("classlist", new String[]{"name","idx"}, null, null, null, null, null);
		while(cursor.moveToNext()){
			String name = cursor.getString(0);
			String idx = cursor.getString(1);
			//将两条查询数据关联起来
			ArrayList<commNumSonInfo> son = getSon(context, Integer.parseInt(idx));
			
			commNumFatherInfo com=new commNumFatherInfo(name,Integer.parseInt(idx),son);
			arraylist.add(com);
			
		}
		cursor.close();
		db.close();
		return arraylist;
		
	}
	
	
	//查询子框数据
	public static ArrayList<commNumSonInfo> getSon(Context context,int idx){
		ArrayList<commNumSonInfo> arraylist=new ArrayList<commNumSonInfo>();
		File file =new File(context.getFilesDir(),constants.CPYDBCOMMONNUM);
		SQLiteDatabase db=SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = db.query("table"+idx, new String[]{"name","number"}, null, null, null, null, null);
		while(cursor.moveToNext()){
			String name = cursor.getString(0);
			String number = cursor.getString(1);
			commNumSonInfo com=new commNumSonInfo(name,number);
			arraylist.add(com);
		}
		cursor.close();
		db.close();
		return arraylist;
	}
}
