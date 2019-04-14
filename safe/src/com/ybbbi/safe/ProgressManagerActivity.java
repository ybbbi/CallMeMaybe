package com.ybbbi.safe;

import java.io.File;
import java.util.ArrayList;

import com.ybbbi.safe.Contact_3rd_Activity.findview;
import com.ybbbi.safe.bean.Appinfo;
import com.ybbbi.safe.view.AppManager;
import com.ybbbi.safe.view.MyProgressBar;

import android.R.integer;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProgressManagerActivity extends Activity {

	private MyProgressBar sdcard;
	private MyProgressBar memory;
	private ListView listview;
	private ArrayList<Appinfo> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress_manager);
		init();
		setview();
		initdata();
	}

	private void initdata() {
		listview = (ListView) findViewById(R.id.progress_manager_lv);
		new Thread() {
			private myAdapter adapter;

			public void run() {
				adapter = new myAdapter();

				list = AppManager.Appinfo(ProgressManagerActivity.this);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						listview.setAdapter(adapter);
					}
				});
			};
		}.start();

	}

	private class myAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			holder h;
			if(convertView==null){
			convertView=View.inflate(getApplicationContext(), R.layout.appmanager_listview, null);
			h=new holder();
			h.icon=(ImageView) convertView.findViewById(R.id.image_icon);
			h.size=(TextView)  convertView.findViewById(R.id.tv_size);
			h.issdcard=(TextView)  convertView.findViewById(R.id.tv_issdcard);
			h.packagename=(TextView)  convertView.findViewById(R.id.tv_packagename);
			
			convertView.setTag(h);
			}else{
			h=(holder) convertView.getTag();
			}
			Appinfo appinfo = list.get(position);
			h.icon.setImageDrawable(appinfo.icon);
			if(appinfo.sdcard){
				h.issdcard.setText("sd卡内存");
			}else{
				h.issdcard.setText("手机内存");
				
			}
			
			h.size.setText(appinfo.size);
			
			h.packagename.setText(appinfo.packagename);
			return convertView;
		}

	}
	static class holder{
		public ImageView icon;
		public TextView packagename,issdcard,size;
		
	}

	private void init() {
		memory = (MyProgressBar) findViewById(R.id.app_memory);
		sdcard = (MyProgressBar) findViewById(R.id.app_sdcard);

	}

	private void setview() {
		File dataDirectory = Environment.getDataDirectory();
		long totalSpace = dataDirectory.getTotalSpace();
		long freeSpace = dataDirectory.getFreeSpace();
		long usedSpace = totalSpace - freeSpace;
		int progress = (int) ((usedSpace * 100f / totalSpace) + 0.5f);
		String used = Formatter.formatFileSize(this, usedSpace);

		String free = Formatter.formatFileSize(this, freeSpace);

		memory.setProgressBar(progress);
		memory.setText_left("已用:" + used);
		memory.setText_right("剩余:" + free);

		memory.setText("内 存:");

		File externalStorageDirectory = Environment
				.getExternalStorageDirectory();
		long totalSpace2 = externalStorageDirectory.getTotalSpace();
		long freeSpace2 = externalStorageDirectory.getFreeSpace();
		long usedSpace2 = totalSpace2 - freeSpace2;
		String used2 = Formatter.formatFileSize(this, usedSpace2);
		sdcard.setText_left("已用:" + used2);
		String free2 = Formatter.formatFileSize(this, freeSpace2);
		sdcard.setText_right("剩余:" + free2);
		sdcard.setText("SD卡:");
		int progress2 = (int) ((usedSpace2 * 100f / totalSpace2) + 0.5f);
		sdcard.setProgressBar(progress2);
	}

}
