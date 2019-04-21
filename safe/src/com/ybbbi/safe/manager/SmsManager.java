package com.ybbbi.safe.manager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.ybbbi.safe.bean.SmsCopy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.widget.SlidingDrawer;

public class SmsManager {
	public interface querySmsListener{
		public void setMax(int max);
		public void setProgress(int progress);
	}
	public static void querySMS(Context context,querySmsListener listener) {
		ContentResolver content = context.getContentResolver();
		Uri uri = Uri.parse("content://sms/");

		Cursor cursor = content.query(uri, new String[] { "address", "date", "type", "body" },
				null, null, null);
		listener.setMax(cursor.getCount());
		int progress =0;
		
		List<SmsCopy> list=new ArrayList<SmsCopy>();
		while(cursor.moveToNext()){
			String address = cursor.getString(0);
			String date = cursor.getString(1);
			String type	 = cursor.getString(2);
			String body = cursor.getString(3);
			SmsCopy s=new SmsCopy(address, date, type, body);
			list.add(s);
			progress++;
			SystemClock.sleep(100);
			listener.setProgress(progress);
		}
		Gson gson=new Gson();
		String json = gson.toJson(list);
		
		try {
			FileWriter file = new FileWriter(new File("mnt/sdcard/sms.txt"));
			file.write(json);
			file.flush();
			file.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
