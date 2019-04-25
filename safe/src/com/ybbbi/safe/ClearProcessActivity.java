package com.ybbbi.safe;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.ybbbi.safe.bean.AppCacheInfo;
import com.ybbbi.safe.bean.Appinfo;
import com.ybbbi.safe.manager.AppManager;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ClearProcessActivity extends Activity implements OnClickListener {
	private ImageView mPicon;
	private RelativeLayout relative;
	private RelativeLayout relative_finish;
	private myAdapter adapter;
	private ListView listview;
	private ProgressBar progressbar;
	private TextView mPname;
	private TextView mPsize;
	private ImageView line;
	private PackageManager mPm;
	private myAsyncTask asynctask;
	private List<AppCacheInfo> list = new ArrayList<AppCacheInfo>();
	private List<AppCacheInfo> cacheList = new ArrayList<AppCacheInfo>();
	private int process = 0;
	private TextView info;
	private Button btn_again;
	private Button btn_all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear_process);
		init();
	}

	private void init() {

		btn_all = (Button) findViewById(R.id.btn_clear_all);
		btn_again = (Button) findViewById(R.id.btn_clear_again);
		info = (TextView) findViewById(R.id.clear_tv_info);
		progressbar = (ProgressBar) findViewById(R.id.clear_progressBar1);
		mPname = (TextView) findViewById(R.id.clear_tv_name);
		relative = (RelativeLayout) findViewById(R.id.clear_relative);
		relative_finish = (RelativeLayout) findViewById(R.id.clear_relative_finish);
		mPsize = (TextView) findViewById(R.id.clear_tv_size);
		line = (ImageView) findViewById(R.id.clear_iv_line);
		listview = (ListView) findViewById(R.id.clear_lv);
		mPicon = (ImageView) findViewById(R.id.clear_iv_icon);
		mPm = getPackageManager();
		asynctask = new myAsyncTask();
		asynctask.execute();
		btn_again.setOnClickListener(this);
		btn_all.setOnClickListener(this);
	}

	private class myAsyncTask extends AsyncTask<Void, AppCacheInfo, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			List<Appinfo> appinfo = AppManager.Appinfo(getApplicationContext());
			progressbar.setMax(appinfo.size());
			for (Appinfo appinfo2 : appinfo) {
				if (asynctask.isCancelled()) {
					return null;
				}
				try {
					process += 1;
					Method method = PackageManager.class.getDeclaredMethod(
							"getPackageSizeInfo", String.class,
							IPackageStatsObserver.class);
					method.invoke(mPm, appinfo2.packagename, mStatsObserver);
					SystemClock.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return null;
		}

		public void showUpdate(AppCacheInfo appCacheInfo) {
			publishProgress(appCacheInfo);
		}

		@Override
		protected void onPreExecute() {
			if (asynctask.isCancelled()) {
				return;
			}
			list.clear();
			process = 0;
			progressbar.setProgress(0);
			adapter = new myAdapter();
			listview.setAdapter(adapter);
			Animation animation = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.clearline);
			line.startAnimation(animation);
			relative_finish.setVisibility(View.GONE);
			relative.setVisibility(View.VISIBLE);
			btn_all.setEnabled(false);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			if (asynctask.isCancelled()) {
				return;
			}
			listview.smoothScrollToPosition(0);
			line.clearAnimation();
			relative.setVisibility(View.GONE);
			relative_finish.setVisibility(View.VISIBLE);
			long CacheSize = 0;
			for (AppCacheInfo info : cacheList) {
				CacheSize += info.cache;
			}
			info.setText("总共有"
					+ cacheList.size()
					+ "个缓存软件,总共"
					+ Formatter.formatFileSize(getApplicationContext(),
							CacheSize));
			btn_all.setEnabled(true);
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(AppCacheInfo... values) {
			if (asynctask.isCancelled()) {
				return;
			}
			AppCacheInfo appCacheInfo = values[0];
			if (appCacheInfo.cache > 0) {
				list.add(0, appCacheInfo);
				cacheList.add(appCacheInfo);
			} else {
				list.add(appCacheInfo);

			}
			listview.smoothScrollToPosition(list.size()-1);
			adapter.notifyDataSetChanged();

			progressbar.setProgress(process);
			mPname.setText(appCacheInfo.pkgName);
			mPsize.setText("缓存大小:"
					+ Formatter.formatFileSize(getApplicationContext(),
							appCacheInfo.cache));
			mPicon.setImageDrawable(appCacheInfo.icon);

			super.onProgressUpdate(values);
		}

	}

	IPackageStatsObserver.Stub mStatsObserver = new IPackageStatsObserver.Stub() {
		public void onGetStatsCompleted(PackageStats stats, boolean succeeded) {

			AppCacheInfo appCacheInfo = new AppCacheInfo();

			appCacheInfo.cache = stats.cacheSize;
			appCacheInfo.pkgName = stats.packageName;
			try {
				ApplicationInfo applicationInfo = mPm.getApplicationInfo(
						stats.packageName, 0);
				appCacheInfo.icon = applicationInfo.loadIcon(mPm);
				appCacheInfo.name = applicationInfo.loadLabel(mPm).toString()
						.trim();
				if (asynctask.isCancelled()) {
					return;
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			asynctask.showUpdate(appCacheInfo);

		}
	};

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
			Holder h;
			if (convertView == null) {
				h = new Holder();
				convertView = convertView.inflate(getApplicationContext(),
						R.layout.clearcache_listview, null);
				h.brush = (ImageView) convertView
						.findViewById(R.id.imageview_clear_brush);
				h.icon = (ImageView) convertView
						.findViewById(R.id.image_clearicon);
				h.name = (TextView) convertView
						.findViewById(R.id.tv_clear_name);
				h.size = (TextView) convertView
						.findViewById(R.id.tv_clear_safe);
				convertView.setTag(h);
			} else {
				h = (Holder) convertView.getTag();
			}
			final AppCacheInfo appcacheinfo = (AppCacheInfo) getItem(position);
			h.icon.setImageDrawable(appcacheinfo.icon);
			h.name.setText(appcacheinfo.name);
			h.size.setText(Formatter.formatFileSize(getApplicationContext(),
					appcacheinfo.cache));
			h.brush.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
					intent.addCategory("android.intent.category.DEFAULT");
					intent.setData(Uri.parse("package:" + appcacheinfo.pkgName));
					startActivityForResult(intent, 106);
				}
			});
			if (appcacheinfo.cache != 0) {
				h.brush.setVisibility(View.VISIBLE);
			} else {
				h.brush.setVisibility(View.GONE);

			}
			return convertView;

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		mPm = getPackageManager();
		asynctask = new myAsyncTask();
		asynctask.execute();
		super.onActivityResult(106, resultCode, data);
	}

	static class Holder {
		private ImageView icon, brush;
		private TextView name, size;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_clear_again:
			mPm = getPackageManager();
			asynctask = new myAsyncTask();
			asynctask.execute();

			break;
		case R.id.btn_clear_all:
			try {
				// 请求最大内存,释放其他应用缓存,实现清理缓存操作
				Method method = PackageManager.class.getDeclaredMethod(
						"freeStorageAndNotify", long.class,
						IPackageDataObserver.class);
				method.invoke(mPm, Long.MAX_VALUE,
						new IPackageDataObserver.Stub() {

							@Override
							public void onRemoveCompleted(String packageName,
									boolean succeeded) throws RemoteException {
								// TODO Auto-generated method stub
								runOnUiThread(new Runnable() {
									public void run() {
										mPm = getPackageManager();
										asynctask = new myAsyncTask();
										asynctask.execute();
									}
								});
							}
						});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		asynctask.cancel(true);
		super.onDestroy();
	}

}
