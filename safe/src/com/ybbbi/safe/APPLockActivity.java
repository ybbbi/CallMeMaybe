package com.ybbbi.safe;

import java.util.ArrayList;
import java.util.List;

import com.ybbbi.safe.bean.Appinfo;
import com.ybbbi.safe.database.dao.ApplockDAO;
import com.ybbbi.safe.manager.AppManager;

import android.os.Bundle;
import android.os.SystemClock;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class APPLockActivity extends Activity implements OnClickListener {

	private Button unlock;
	private Button lock;
	private LinearLayout ll_unlock;
	private LinearLayout ll_lock;
	private ApplockDAO ad;
	private List<Appinfo> unlockList;
	private List<Appinfo> lockList;
	private ListView lv_lock;
	private ListView lv_unlock;

	private myAdapter unlockAdapter;
	private myAdapter lockAdapter;
	private TextView tv_lock;
	private TextView tv_unlock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_applock);
		init();
		initdata();
		
		

	}

	private void initdata() {
		new Thread() {

			public void run() {
				ArrayList<Appinfo> appInfo = AppManager
						.Appinfo(APPLockActivity.this);
				lockList = new ArrayList<Appinfo>();
				unlockList = new ArrayList<Appinfo>();
				for (Appinfo appinfo : appInfo) {
					if (ad.query(appinfo.packagename)) {
						// 已添加
						lockList.add(appinfo);
					} else {
						// 未添加
						unlockList.add(appinfo);

					}
				}

				runOnUiThread(new Runnable() {

					public void run() {
						unlockAdapter = new myAdapter(false);
						lockAdapter = new myAdapter(true);
						lv_unlock.setAdapter(unlockAdapter);
						lv_lock.setAdapter(lockAdapter);
					}
				});

			};
		}.start();
	}

	private class myAdapter extends BaseAdapter {

		private boolean b;
		private TranslateAnimation left;
		private TranslateAnimation right;

		public myAdapter(boolean b2) {
			this.b = b2;
			right = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,Animation.RELATIVE_TO_SELF, 1,Animation.RELATIVE_TO_SELF, 0,Animation.RELATIVE_TO_SELF, 0);
			right.setDuration(600);
			left = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,Animation.RELATIVE_TO_SELF, -1,Animation.RELATIVE_TO_SELF, 0,Animation.RELATIVE_TO_SELF, 0);
			left.setDuration(600);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			tv_lock.setText("已加锁("+lockList.size()+")");
			tv_unlock.setText("未加锁("+unlockList.size()+")");
			return b ? lockList.size() : unlockList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return b ? lockList.get(position) : unlockList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder h;
			final View v;
			if (convertView == null) {
				h = new Holder();
				v = View.inflate(getApplicationContext(),
						R.layout.applock_listview, null);

				h.iv = (ImageView) v.findViewById(R.id.image_applock_icon);
				h.iv_lock = (ImageView) v.findViewById(R.id.image_lock);
				h.tv = (TextView) v.findViewById(R.id.tv_name_applock);

				v.setTag(h);
			} else {
				v = convertView;
				h = (Holder) v.getTag();
			}

			// Appinfo appinfo = unlockList.get(position);
			final Appinfo appinfo = (Appinfo) getItem(position);

			h.iv.setImageDrawable(appinfo.icon);
			h.tv.setText(appinfo.label);
			if (b) {
				h.iv_lock
						.setBackgroundResource(R.drawable.selector_applock_unlock);
			} else {
				h.iv_lock
						.setBackgroundResource(R.drawable.selector_applock_lock);
			}
			h.iv_lock.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					if (b) {
						// 再点击解锁
						// 在数据库中删除已加锁包名,添加未加锁集合中的数据,删除加锁集合数据
						v.startAnimation(left);
						left.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								
								ad.delete(appinfo.packagename);
								lockList.remove(appinfo);
								unlockList.add(appinfo);
								lockAdapter.notifyDataSetChanged();
								unlockAdapter.notifyDataSetChanged();
							}
						});
					} else {
						// 在点击加锁,在数据库中添加已加锁包名,添加加锁集合中的数据,删除未解锁集合数据
						v.startAnimation(right);
						right.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								
								ad.insert(appinfo.packagename);
								unlockList.remove(appinfo);
								lockList.add(appinfo);
								lockAdapter.notifyDataSetChanged();
								unlockAdapter.notifyDataSetChanged();
							}
						});

					}
				}
			});

			return v;
		}

	}

	static class Holder {
		private ImageView iv, iv_lock;
		private TextView tv;
	}

	private void init() {
		ad = new ApplockDAO(this);
		unlock = (Button) findViewById(R.id.applock_unlock);
		lock = (Button) findViewById(R.id.applock_lock);
		ll_unlock = (LinearLayout) findViewById(R.id.applock_ll_unlock);
		ll_lock = (LinearLayout) findViewById(R.id.applock_ll_lock);
		lv_unlock = (ListView) findViewById(R.id.applock_listview_unlock);
		lv_lock = (ListView) findViewById(R.id.applock_listview_lock);
		tv_lock = (TextView) findViewById(R.id.applock_lock_tv);
		tv_unlock = (TextView) findViewById(R.id.applock_unlock_tv);
		
		
		
		
		unlock.setOnClickListener(this);
		lock.setOnClickListener(this);

	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.applock_unlock:
			ll_lock.setVisibility(View.GONE);
			ll_unlock.setVisibility(View.VISIBLE);
			unlock.setBackgroundResource(R.drawable.shape_applock_unlock_btn_select);
			lock.setBackgroundResource(R.drawable.shape_applock_lock_btn_normal);
			unlock.setTextColor(getResources().getColor(
					R.color.applockBtnSelect));
			lock.setTextColor(getResources().getColor(R.color.applockBtnNormal));

			break;
		case R.id.applock_lock:
			ll_lock.setVisibility(View.VISIBLE);
			ll_unlock.setVisibility(View.GONE);
			lock.setTextColor(getResources().getColor(R.color.applockBtnSelect));
			unlock.setTextColor(getResources().getColor(
					R.color.applockBtnNormal));
			lock.setBackgroundResource(R.drawable.shape_applock_lock_btn_select);
			unlock.setBackgroundResource(R.drawable.shape_applock_unlock_btn_normal);

			break;
		}
	}

}
