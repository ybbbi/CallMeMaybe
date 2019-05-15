package com.ybbbi.zhbj;

import java.util.ArrayList;
import java.util.List;

import com.ybbbi.zhbj.tool.Constants;
import com.ybbbi.zhbj.tool.SharedpreferencesTool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class GuideActivity extends Activity {
	private List<ImageView> list;
	private ViewPager mViewpager;
	private int[] mImageIds = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };
	private Button btn_start;
	private LinearLayout guide_ll;
	private ImageView dot_red;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		init();
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedpreferencesTool sp=new SharedpreferencesTool();
				sp.saveBoolean(GuideActivity.this, Constants.isFirst, false);
				Intent intent =new Intent(GuideActivity.this,HomeActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	private void init() {
		dot_red = (ImageView) findViewById(R.id.guide_dot_red);
		guide_ll = (LinearLayout) findViewById(R.id.guide_ll);
		btn_start = (Button) findViewById(R.id.btn_guide_start);
		mViewpager = (ViewPager) findViewById(R.id.viewPager_guide);
		mViewpager.setAdapter(new myAdapter());
		mViewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
		

		

			@Override
			public void onPageSelected(int position) {
			
				if (position == list.size() - 1) {
					btn_start.setVisibility(View.VISIBLE);
				} else {
					btn_start.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) dot_red.getLayoutParams();
				int leftmargin = (int) (20*positionOffset)+position*20;
				layoutParams.leftMargin=(int) (leftmargin);
				dot_red.setLayoutParams(layoutParams);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		list = new ArrayList<ImageView>();
		list.clear();
		for (int i = 0; i < mImageIds.length; i++) {
			createImageview(i);
			createDot();
		}
	}

	private void createDot() {
		ImageView view =new ImageView(this);
		view.setBackgroundResource(R.drawable.shape_guide_dot_bkg);
		LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.rightMargin=10;
		view.setLayoutParams(params);
		guide_ll.addView(view);
	}

	private void createImageview(int i) {
		ImageView img = new ImageView(this);

		img.setBackgroundResource(mImageIds[i]);
		list.add(img);

	}

	private class myAdapter extends PagerAdapter {

		@Override
		public int getCount() {

			return list.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {

			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = list.get(position);
			container.addView(imageView);

			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

}
