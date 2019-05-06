package com.ybbbi.demo1;

import android.os.Bundle;
import android.os.Handler;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {

	private String[] text = { "涂鸦文化，到底何去何从", "餐饮安全问题谁来带头", "习大大会晤各国代表",
			"民声问题汇报", "杰出代表发言，就新闻发声", "今年张学友演唱会门票一票难求！" };
	private int[] iconId = { R.drawable.a, R.drawable.b, R.drawable.c,
			R.drawable.d, R.drawable.e, R.drawable.f };
	private ImageView[] img = new ImageView[text.length];
	private View[] view = new View[iconId.length];
	private TextView tv;
	private ViewPager vp;
	private LinearLayout ll;
	private View convertView;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			int currentItem = vp.getCurrentItem();
			currentItem++;
			vp.setCurrentItem(currentItem);
			handler.sendEmptyMessageDelayed(0, 3000);
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();

	}
	@Override
	protected void onStart() {
		handler.sendEmptyMessageDelayed(0, 3000);
		super.onStart();
	};
	@Override
	protected void onStop() {
		handler.removeMessages(0);
		super.onStop();
	};
	private void init() {
		vp = (ViewPager) findViewById(R.id.vp);
		tv = (TextView) findViewById(R.id.tv);
		ll = (LinearLayout) findViewById(R.id.ll);
		for (int i = 0; i < iconId.length; i++) {
			img[i] = new ImageView(this);
			img[i].setBackgroundResource(iconId[i]);

			point(i);
		}
		change(0);
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				change(position);
				
				
				/*if(position!=0){
					
					view[position-1].setSelected(false);
				}if(position!=iconId.length-1){
					view[position+1].setSelected(false);
					
				}*/
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				/*switch(state){
				case ViewPager.SCROLL_STATE_IDLE:
					handler.sendEmptyMessageDelayed(0, 3000);
				case ViewPager.SCROLL_STATE_DRAGGING:
					handler.removeMessages(0);
				case ViewPager.SCROLL_STATE_SETTLING:
					handler.sendEmptyMessageDelayed(0, 3000);
					
					break;
				}*/
				if(state==ViewPager.SCROLL_STATE_IDLE){
					handler.sendEmptyMessageDelayed(0, 3000);
				}else{
					handler.removeMessages(0);
				}
			}
		});

		vp.setAdapter(new myAdapter());
		vp.setCurrentItem(iconId.length*100000/2);
		 
		
			
		
	}


	private void change(int position) {
		position=position%iconId.length;
		tv.setText(text[position]);

		if(convertView!=null){
			convertView.setSelected(false);
			
		}
		convertView=view[position];
		view[position].setSelected(true);
	}

	private void point(int i) {
		view[i] = new View(this);
		view[i].setBackgroundResource(R.drawable.select_point);
		LinearLayout.LayoutParams params = new LayoutParams(5, 5);
		params.rightMargin=5;
		view[i].setLayoutParams(params);
		ll.addView(view[i]);
	}

	private class myAdapter extends PagerAdapter {

		@Override
		public int getCount() {

			return iconId.length*1000*100;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			position=position%iconId.length;
			ImageView im = img[position];
			container.addView(im);
			return im;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			// super.destroyItem(container, position, object);
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

	}

}
