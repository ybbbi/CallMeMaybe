package com.ybbbi.safe;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {

	private ImageView image_logo;
	private final String[] Title = new String[] { "手机防盗", "骚扰拦截", "软件管家",
			"进程管理", "流量统计", "手机杀毒", "缓存清理", "常用工具" };
	private final String[] Info = new String[] { "远程定位手机", "全面拦截骚扰", "管理您的软件",
			"管理运行进程", "流量一目了然", "病毒无处藏身", "系统快如火箭", "工具大全" };
	private final int[] Icon = new int[] { R.drawable.sjfd, R.drawable.srlj,
			R.drawable.rjgj, R.drawable.jcgl, R.drawable.lltj, R.drawable.sjsd,
			R.drawable.hcql, R.drawable.cygj };
	private GridView home_GV_gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);

		image_logo = (ImageView) findViewById(R.id.Image_logo);
		animationStart();
		home_GV_gridview = (GridView) findViewById(R.id.home_GV_gridview);
		MyBaseAdapter mybaseadapter=new MyBaseAdapter();
		home_GV_gridview.setAdapter(mybaseadapter);

	}
	private class MyBaseAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Icon.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			View view=View.inflate(getApplicationContext(),R.layout.gridview_home, null);

			ImageView gridview_imageview = (ImageView) view.findViewById(R.id.gridview_imageview);
			TextView textview_title = (TextView) view.findViewById(R.id.Textview_title);
			TextView textview_info = (TextView) view.findViewById(R.id.Textview_info);
			
			gridview_imageview.setImageResource(Icon[position]);
			textview_title.setText(Title[position]);
			textview_info.setText(Info[position]);
			
			return view;
		}
		
	}
	public void Setting_Activity(View v){
		Intent intent=new Intent(this,Setting_Activity.class);
		startActivity(intent);
	}
	private void animationStart() {
		ObjectAnimator objectanimator = ObjectAnimator.ofFloat(image_logo,
				"rotationY", 0f, 180f, 360f);
		objectanimator.setDuration(4000);
		objectanimator.setRepeatCount(ObjectAnimator.INFINITE);
		objectanimator.setRepeatMode(ObjectAnimator.RESTART);
		// REVERSE代表从动画结束的地方开始下一次动画,RESTART代表从动画最初的地方开始下一次动画
		objectanimator.start();

	}

}
