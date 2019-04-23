package com.ybbbi.safe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import com.ybbbi.safe.bean.Appinfo;
import com.ybbbi.safe.manager.AppManager;
import com.ybbbi.safe.view.MyProgressBar;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.text.format.Formatter;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
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
import android.widget.Toast;

public class ProgressManagerActivity extends Activity implements
		OnClickListener {

	private MyProgressBar sdcard;
	private MyProgressBar memory;
	private PopupWindow pop;
	private StickyListHeadersListView listview;
	private List<Appinfo> list;
	private List<Appinfo> Userlist;
	private List<Appinfo> Systemlist;
	//private TextView tv_User;
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
				// 如果为用户程序信息textview
				/*if (position == 0 || position == Userlist.size() + 1) {
					return;
				}*/
				if (position <= Userlist.size()) {
					appinfo = Userlist.get(position );
				} else {
					appinfo = Systemlist.get(position - Userlist.size() );
				}
				popdismiss();
				View Contentview = View.inflate(getApplicationContext(),
						R.layout.popwindow, null);
				Contentview.findViewById(R.id.popwindow_message)
						.setOnClickListener(ProgressManagerActivity.this);
				Contentview.findViewById(R.id.popwindow_uninstall)
						.setOnClickListener(ProgressManagerActivity.this);
				Contentview.findViewById(R.id.popwindow_open)
						.setOnClickListener(ProgressManagerActivity.this);
				Contentview.findViewById(R.id.popwindow_share)
						.setOnClickListener(ProgressManagerActivity.this);
				pop = new PopupWindow(Contentview, LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				pop.setAnimationStyle(R.style.Animation);
				pop.showAsDropDown(view, view.getWidth() / 2 + 10,
						-view.getHeight());

			}

		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.popwindow_message:
			Intent intent=new Intent();
			intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.setData(Uri.parse("package:"+appinfo.packagename));
			startActivity(intent);
			break;
		case R.id.popwindow_open:
			PackageManager pm = getPackageManager();
			Intent intent2 = pm.getLaunchIntentForPackage(appinfo.packagename);
			if (intent2 != null) {

				startActivity(intent2);
			} else {

				Toast.makeText(getApplicationContext(), "系统程序,无法打开", 0).show();
			}
			break;
		case R.id.popwindow_share:
			Intent intent3 = new Intent();
			intent3.setAction("android.intent.action.SEND");
			intent3.addCategory("android.intent.category.DEFAULT");
			intent3.setType("text/plain");
			intent3.putExtra(Intent.EXTRA_TEXT, appinfo.label
					+ "很好用的一款软件,快去应用市场搜索下载吧");
			startActivity(intent3);
			break;
		case R.id.popwindow_uninstall:
			/*
			 * <intent-filter> <action android:name="android.intent.action.VIEW"
			 * /> <action android:name="android.intent.action.DELETE" />
			 * <category android:name="android.intent.category.DEFAULT" /> <data
			 * android:scheme="package" /> </intent-filter>
			 */
			if (appinfo.packagename.equals(getPackageName())) {
				Toast.makeText(getApplicationContext(), "无法卸载此应用", 0).show();
				return;
			}
			if (appinfo.system) {
				Toast.makeText(getApplicationContext(), "请先获取root权限", 0).show();
			} else {

				Intent intent4 = new Intent();
				intent4.setAction("android.intent.action.VIEW");
				intent4.setAction("android.intent.action.DELETE");

				intent4.setData(Uri.parse("package:" + appinfo.packagename));
				intent4.addCategory("android.intent.category.DEFAULT");
				startActivityForResult(intent4, 1);
			}
			break;
		}
		popdismiss();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		initdata();
		super.onActivityResult(1, 0, data);
	}

	private void popdismiss() {
		if (pop != null) {
			pop.dismiss();
			pop = null;
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
				/*if (Systemlist != null && Userlist != null) {// 不判断会空指针
					if (firstVisibleItem >= Userlist.size() +1) {
						tv_User.setText("系统应用(" + Systemlist.size() + "):");
					} else {
						tv_User.setText("用户应用(" + Userlist.size() + "):");

					}
				}*/
			}
		});
	}

	@Override
	protected void onDestroy() {
		popdismiss();
		super.onDestroy();
	}

	private void initdata() {
		listview = (StickyListHeadersListView) findViewById(R.id.progress_manager_lv);

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
						//tv_User.setText("用户应用(" + Userlist.size() + "):");

					}
				});
			};
		}.start();

	}

	private class myAdapter extends BaseAdapter implements StickyListHeadersAdapter{

		@Override
		public int getCount() {
			return Userlist.size() + Systemlist.size() ;
		}

	/*	@Override
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
		}*/

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
		/*	int type = getItemViewType(position);
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
			if (type == 1) {*/
			
			
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

				if (position < Userlist.size()) {
					// 通过系统list长度作比较判断是否为系统程序
					appinfo = Userlist.get(position );

				} else {

					appinfo = Systemlist.get(position - Userlist.size());
				}

				h.icon.setImageDrawable(appinfo.icon);
				if (appinfo.sdcard) {
					h.issdcard.setText("sd卡内存");
				} else {
					h.issdcard.setText("手机内存");

				}

				h.size.setText(appinfo.size);

				h.packagename.setText(appinfo.label);
			//}
			return convertView;

		}

		@Override
		public View getHeaderView(int position, View convertView,
				ViewGroup parent) {
			TextView text = new TextView(getApplicationContext());
			text.setTextColor(Color.GRAY);
			text.setBackgroundResource(R.drawable.commonnum_bkg_group);
			text.setPadding(10, 10, 8, 8);
			text.setTextSize(20);
			if (position < Userlist.size()) {
				// 通过系统list长度作比较判断是否为系统程序
				appinfo = Userlist.get(position );

			} else {

				appinfo = Systemlist.get(position - Userlist.size() );
			}
			
			text.setText(appinfo.system ?  "系统应用(" + Systemlist.size() + "):":"用户应用(" + Userlist.size() + "):" );

			
			
			return text;
		}

		@Override
		public long getHeaderId(int position) {
			if (position < Userlist.size()) {
				// 通过系统list长度作比较判断是否为系统程序
				appinfo = Userlist.get(position );

			} else {

				appinfo = Systemlist.get(position - Userlist.size() );
			}
			return appinfo.system?0:1;
		}

	}

	static class holder {
		public ImageView icon;
		public TextView packagename, issdcard, size;

	}

	private void init() {
		memory = (MyProgressBar) findViewById(R.id.app_memory);
		sdcard = (MyProgressBar) findViewById(R.id.app_sdcard);
		//tv_User = (TextView) findViewById(R.id.tv_userApp);

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
