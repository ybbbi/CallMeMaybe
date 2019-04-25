package com.ybbbi.safe;

import java.util.ArrayList;
import java.util.List;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.ybbbi.safe.bean.Appinfo;
import com.ybbbi.safe.database.dao.AntivirusDAO;
import com.ybbbi.safe.manager.AppManager;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class VirusActivity extends Activity {

	private List<Appinfo> list = new ArrayList<Appinfo>();
	private List<Appinfo> viruslist = new ArrayList<Appinfo>();
	private myAsyncTask asyncTask;
	private ListView listview;
	private List<Appinfo> appinfo;
	private ArcProgress progressbar;
	private Myadapter adapter;
	private int max;
	private TextView pkgname;
	private LinearLayout virus_ll;
	private LinearLayout virus_ll_scan;
	private LinearLayout virus_ll_again;
	private TextView number;
	private Button btn_rescan;
	private LinearLayout virus_ll_imageview;

	private ImageView right;
	private ImageView left;
	private int width;
	private myReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_virus);
		init();
		setUninstallreceiver();
	}
	private class myReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String dataString = intent.getDataString();
			String[] split = dataString.split(":");
			String pkgname = split[1];
			for (int i=0;i<appinfo.size();i++) {
				if(appinfo.get(i).packagename.equals(pkgname)){
					list.remove(appinfo.get(i));
					adapter.notifyDataSetChanged();
					viruslist.remove(appinfo.get(i));
					if (viruslist.size() > 0) {
						number.setText("发现病毒威胁" + viruslist.size() + "个");

					} else {

						number.setText("手机安全");
					}
				}
			}
		}
		
	}
	private void setUninstallreceiver() {
		receiver = new myReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		filter.addDataScheme("package");
		registerReceiver(receiver, filter);
	
	}

	private void init() {
		virus_ll_imageview = (LinearLayout) findViewById(R.id.virus_ll_imageview);
		virus_ll = (LinearLayout) findViewById(R.id.virus_ll);
		virus_ll_scan = (LinearLayout) findViewById(R.id.virus_ll_scan);

		left = (ImageView) findViewById(R.id.iv_left);
		right = (ImageView) findViewById(R.id.iv_right);
		pkgname = (TextView) findViewById(R.id.virus_tv_name);
		btn_rescan = (Button) findViewById(R.id.virus_btn_rescan);
		number = (TextView) findViewById(R.id.virus_tv_state);
		listview = (ListView) findViewById(R.id.virus_listview);
		progressbar = (ArcProgress) findViewById(R.id.arc_progress);
		asyncTask = new myAsyncTask();
		asyncTask.execute();

		btn_rescan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				backAnimation();
				
			}
		});
	}

	public Bitmap getright(Bitmap cache) {
		width = cache.getWidth() / 2;
		int height = cache.getHeight();
		Bitmap bitmap2 = Bitmap.createBitmap(width, height, cache.getConfig());
		Canvas canvas = new Canvas(bitmap2);

		Matrix matrix = new Matrix();
		matrix.postTranslate(-width, 0);
		Paint paint = new Paint();

		canvas.drawBitmap(cache, matrix, paint);
		return bitmap2;
	}

	public Bitmap getleft(Bitmap cache) {
		int width = cache.getWidth() / 2;
		int height = cache.getHeight();
		Bitmap bitmap2 = Bitmap.createBitmap(width, height, cache.getConfig());
		Canvas canvas = new Canvas(bitmap2);

		Matrix matrix = new Matrix();

		Paint paint = new Paint();

		canvas.drawBitmap(cache, matrix, paint);
		return bitmap2;
	}

	private void startAnimation() {

		ObjectAnimator animator1 = ObjectAnimator.ofFloat(left,"translationX", 0, -width);
		ObjectAnimator animator2 = ObjectAnimator.ofFloat(left,"alpha", 1.0f, 0.0f);
		ObjectAnimator animator3 = ObjectAnimator.ofFloat(right,"translationX", 0, width);
		ObjectAnimator animator4 = ObjectAnimator.ofFloat(right,"alpha", 1.0f, 0.0f);
		ObjectAnimator animator5 = ObjectAnimator.ofFloat(virus_ll,"alpha", 0.0f, 1.0f);
		AnimatorSet animatior = new AnimatorSet();
		animatior.playTogether(animator1, animator2, animator3, animator4,animator5);
		animatior.setDuration(2000);
		animatior.start();
	}


	private void backAnimation() {

		ObjectAnimator animator1 = ObjectAnimator.ofFloat(left,"translationX",  -width,0);
		ObjectAnimator animator2 = ObjectAnimator.ofFloat(left,"alpha",  0.0f,1.0f);
		ObjectAnimator animator3 = ObjectAnimator.ofFloat(right,"translationX", width, 0);
		ObjectAnimator animator4 = ObjectAnimator.ofFloat(right,"alpha",  0.0f,1.0f);
		ObjectAnimator animator5 = ObjectAnimator.ofFloat(virus_ll,"alpha", 1.0f, 0.0f);
		AnimatorSet animatior = new AnimatorSet();
		animatior.playTogether(animator1, animator2, animator3, animator4,animator5);
		animatior.setDuration(2000);
		animatior.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				asyncTask = new myAsyncTask();
				asyncTask.execute();
				virus_ll_scan.setVisibility(View.VISIBLE);
				virus_ll.setVisibility(View.GONE);
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
		animatior.start();
	}

	private class myAsyncTask extends AsyncTask<Void, Appinfo, Void> {

		

		@Override
		protected Void doInBackground(Void... params) {
			appinfo = AppManager.Appinfo(getApplicationContext());
			max = appinfo.size();
			for (Appinfo appinfo2 : appinfo) {

				if (asyncTask.isCancelled()) {
					return null;
				}
				boolean b = AntivirusDAO.isVirus(getApplicationContext(),
						appinfo2.md5);
				if (b) {
					appinfo2.isVirus = true;
				} else {
					appinfo2.isVirus = false;

				}

				publishProgress(appinfo2);
				SystemClock.sleep(100);
			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			if (asyncTask.isCancelled()) {
				return;
			}
			virus_ll_imageview.setVisibility(View.GONE);
			 virus_ll.setVisibility(View.GONE);
			 virus_ll_scan.setVisibility(View.VISIBLE);
			list.clear();
			viruslist.clear();
			progressbar.setProgress(0);
			adapter = new Myadapter();
			listview.setAdapter(adapter);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			if (asyncTask.isCancelled()) {
				return;
			}
			listview.smoothScrollToPosition(0);
			virus_ll_scan.setVisibility(View.GONE);
			virus_ll_imageview.setVisibility(View.VISIBLE);
			virus_ll.setVisibility(View.VISIBLE);
			virus_ll_scan.setDrawingCacheEnabled(true);
			virus_ll_scan.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
			Bitmap bitmap = virus_ll_scan.getDrawingCache();
			
			Bitmap rightB = getright(bitmap);
			Bitmap leftB = getleft(bitmap);
			right.setImageBitmap(rightB);
			left.setImageBitmap(leftB);
			startAnimation();
			if (viruslist.size() > 0) {
				number.setText("发现病毒威胁" + viruslist.size() + "个");

			} else {

				number.setText("手机安全");
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Appinfo... values) {
			if (asyncTask.isCancelled()) {
				return;
			}

			Appinfo appinfos = values[0];

			if (appinfos.isVirus) {
				list.add(0, appinfos);
				viruslist.add(appinfos);
			} else {

				list.add(appinfos);
			}
			pkgname.setText(appinfos.packagename);
			adapter.notifyDataSetChanged();
			listview.smoothScrollToPosition(list.size() - 1);
			int progress = (int) (list.size() * 100f / max + 0.5f);
			progressbar.setProgress(progress);
			super.onProgressUpdate(values);
		}

	}

	private class Myadapter extends BaseAdapter {

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
				convertView = convertView.inflate(getApplicationContext(),R.layout.virus_listview, null);

				h.brush = (ImageView) convertView
						.findViewById(R.id.imageview_virus_brush);
				h.icon = (ImageView) convertView
						.findViewById(R.id.image_virusicon);
				h.name = (TextView) convertView
						.findViewById(R.id.tv_virus_name);
				h.virus = (TextView) convertView
						.findViewById(R.id.tv_virus_safe);
				convertView.setTag(h);
			} else {
				h = (Holder) convertView.getTag();
			}
			final Appinfo appinfo = (Appinfo) getItem(position);
			h.icon.setImageDrawable(appinfo.icon);
			h.brush.setVisibility(View.GONE);
			if (appinfo.isVirus) {
				h.virus.setText("病毒");
				h.virus.setTextColor(Color.RED);
				h.brush.setVisibility(View.VISIBLE);
			} else {
				h.virus.setText("安全");
				h.virus.setTextColor(Color.GREEN);
				h.brush.setVisibility(View.GONE);

			}
			h.brush.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					intent.setAction("android.intent.action.DELETE");

					intent.setData(Uri.parse("package:" + appinfo.packagename));
					intent.addCategory("android.intent.category.DEFAULT");
					startActivity(intent);
				}
			});
			h.name.setText(appinfo.label);

			return convertView;
		}

	}

	static class Holder {
		private ImageView icon, brush;
		private TextView name, virus;

	}

	@Override
	protected void onDestroy() {
		asyncTask.cancel(true);
		unregisterReceiver(receiver);
		super.onDestroy();
	}

}
