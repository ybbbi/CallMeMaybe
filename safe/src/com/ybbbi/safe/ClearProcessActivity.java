package com.ybbbi.safe;

import java.lang.reflect.Method;
import java.util.List;

import com.ybbbi.safe.bean.Appinfo;
import com.ybbbi.safe.manager.AppManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ClearProcessActivity extends Activity {

	private ImageView line;
	private PackageManager mPm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear_process);
		init();
	}

	private void init() {
		line = (ImageView) findViewById(R.id.clear_iv_line);
		mPm = getPackageManager();
		myAsyncTask asynctask = new myAsyncTask();
		asynctask.execute();
	}

	private class myAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			List<Appinfo> appinfo = AppManager.Appinfo(getApplicationContext());
			for (Appinfo appinfo2 : appinfo) {
				try {
					Method method = PackageManager.class.getDeclaredMethod("getPackageSizeInfo", String.class,IPackageStatsObserver.class);
					method.invoke(mPm, appinfo2.packagename,mStatsObserver);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			Animation animation = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.clearline);
			line.startAnimation(animation);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

	IPackageStatsObserver.Stub mStatsObserver = new IPackageStatsObserver.Stub() {
		public void onGetStatsCompleted(PackageStats stats, boolean succeeded) {
			long size= stats.cacheSize;
			String pkgName=stats.packageName;
			System.out.println(pkgName+size);
			
		}
	};

}
