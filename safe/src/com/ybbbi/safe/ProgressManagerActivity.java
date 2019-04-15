package com.ybbbi.safe;

import java.io.File;
import java.util.ArrayList;

import com.ybbbi.safe.bean.Appinfo;
import com.ybbbi.safe.view.AppManager;
import com.ybbbi.safe.view.MyProgressBar;

import android.os.Bundle;
import android.os.Environment;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ProgressManagerActivity extends Activity {

	private MyProgressBar sdcard;
	private MyProgressBar memory;
	private PopupWindow pop;
	private ListView listview;
	private ArrayList<Appinfo> list;
	private ArrayList<Appinfo> Userlist;
	private ArrayList<Appinfo> Systemlist;
	private TextView tv_User;
	private Appinfo appinfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress_manager);
		init();
		setview();
		initdata();
		Scroll();
		onitemclick();
	}

	private void onitemclick() {
		listview.setOnItemClickListener(new OnItemClickListener() {


			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//如果为用户程序信息textview
				if(position==0||position==Userlist.size()+1){
					return;
				}
				if(position<=Userlist.size()){
					appinfo=Userlist.get(position-1);
				}else{
					appinfo=Systemlist.get(position-Userlist.size()-2);
				}
				popdismiss();
					View Contentview=View.inflate(getApplicationContext(), R.layout.popwindow, null);
					pop = new PopupWindow(Contentview, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					
					pop.showAsDropDown(view, view.getWidth()/2+10, -view.getHeight());	
				
				
				
				
				
				
			}

			
		});
	}

	private void popdismiss() {
		if(pop !=null){
			pop.dismiss();
			pop=null;
		}
	}
	private void Scroll() {
		listview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				popdismiss();
				if (Systemlist != null && Userlist != null) {//不判断会空指针
					if (firstVisibleItem >= Userlist.size() + 1) {
						tv_User.setText("系统应用(" + Systemlist.size() + "):");
					} else {
						tv_User.setText("用户应用(" + Userlist.size() + "):");

					}
				}
			}
		});
	}
	@Override
	protected void onDestroy() {
		popdismiss();
		super.onDestroy();
	}

	private void initdata() {
		listview = (ListView) findViewById(R.id.progress_manager_lv);

		new Thread() {
			private myAdapter adapter;

			public void run() {

				list = AppManager.Appinfo(ProgressManagerActivity.this);
				// 初始化 报空指针异常
				Systemlist = new ArrayList<Appinfo>();
				Userlist = new ArrayList<Appinfo>();

				for (Appinfo info : list) {
					System.out.println(info);
					if (info.system) {
						Systemlist.add(info);

					} else {
						Userlist.add(info);

					}

				}
				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						listview.setAdapter(new myAdapter());
						tv_User.setText("用户应用(" + Userlist.size() + "):");

					}
				});
			};
		}.start();

	}

	private class myAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return Userlist.size() + Systemlist.size() + 2;
		}

		@Override
		// 通知getview,哪个listview使用自定义样式
		public int getItemViewType(int position) {
			if (position == 0 || position == Userlist.size() + 1) {// 使用自定义样式
				return 0;
			} else {
				return 1;
			}

		}

		@Override
		// 通知系统 使用几个自定义样式listview
		public int getViewTypeCount() {
			return 2;
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
			int type = getItemViewType(position);
			if (type == 0) {
				if (position == 0) {
					View view = View.inflate(getApplicationContext(),
							R.layout.commonnum_search_group, null);
					TextView tv = (TextView) view.findViewById(R.id.tv_group);
					tv.setText("用户应用(" + Userlist.size() + "):");
					tv.setTextSize(20);
					return view;
				} else {
					View view = View.inflate(getApplicationContext(),
							R.layout.commonnum_search_group, null);

					TextView tv = (TextView) view.findViewById(R.id.tv_group);
					tv.setText("系统应用(" + Systemlist.size() + "):");
					tv.setTextSize(20);
					return view;
				}
			}
			if (type == 1) {
				holder h;
				if (convertView == null) {
					convertView = View.inflate(getApplicationContext(),
							R.layout.appmanager_listview, null);
					h = new holder();
					h.icon = (ImageView) convertView
							.findViewById(R.id.image_icon);
					h.size = (TextView) convertView.findViewById(R.id.tv_size);
					h.issdcard = (TextView) convertView
							.findViewById(R.id.tv_issdcard);
					h.packagename = (TextView) convertView
							.findViewById(R.id.tv_packagename);

					convertView.setTag(h);
				} else {
					h = (holder) convertView.getTag();
				}
				

				if (position <= Userlist.size()) {
					// 通过系统list长度作比较判断是否为系统程序
					appinfo = Userlist.get(position - 1);

				} else {

					appinfo = Systemlist.get(position - Userlist.size() - 2);
				}

				h.icon.setImageDrawable(appinfo.icon);
				if (appinfo.sdcard) {
					h.issdcard.setText("sd卡内存");
				} else {
					h.issdcard.setText("手机内存");

				}

				h.size.setText(appinfo.size);

				h.packagename.setText(appinfo.packagename);
			}
			return convertView;

		}

	}

	static class holder {
		public ImageView icon;
		public TextView packagename, issdcard, size;

	}

	private void init() {
		memory = (MyProgressBar) findViewById(R.id.app_memory);
		sdcard = (MyProgressBar) findViewById(R.id.app_sdcard);
		tv_User = (TextView) findViewById(R.id.tv_userApp);

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
