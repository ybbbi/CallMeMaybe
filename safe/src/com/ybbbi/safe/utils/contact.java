package com.ybbbi.safe.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.ybbbi.safe.bean.Contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.ContactsContract;

public class contact {
	public static ArrayList<Contact> getcontact(Context context) {
		//模仿数据过大,加载延迟操作
		SystemClock.sleep(4000);
		
		ArrayList<Contact> list=new ArrayList<Contact>();
		ContentResolver content = context.getContentResolver();
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String[] string = new String[] {
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.NUMBER, 
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

		Cursor cursor = content.query(uri, string, null, null, null);
		while(cursor.moveToNext()){
			String name = cursor.getString(0);
			String num = cursor.getString(1);
			int id=cursor.getInt(2);
			Contact contact =new  Contact(name,num,id);
			list.add(contact);
					
		}
		return list;

	}
	
	public static Bitmap getimage(Context context,int id){
		ContentResolver contentResolver =context.getContentResolver();
		Uri uri=Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, id+"");
		
		
		InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver, uri);
		Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
		if(inputStream !=null){
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bitmap;
	}
	
	
	
	
	
}
