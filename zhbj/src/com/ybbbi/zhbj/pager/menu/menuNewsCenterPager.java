package com.ybbbi.zhbj.pager.menu;

import java.util.ArrayList;
import java.util.List;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;
import com.ybbbi.zhbj.R;
import com.ybbbi.zhbj.bean.NewsCenterInfo.NewsChildInfo;
import com.ybbbi.zhbj.pager.menu.item.menuNewsCenterItemPager;

import android.app.Activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class menuNewsCenterPager extends baseMenuPager {
	private List<menuNewsCenterItemPager> itempagers;
	private NewsChildInfo info;
	private TabPageIndicator mIndicator;
	private ViewPager mViewpager;
	private myadapter myadapter;
	private ImageButton mIbtn;
	public menuNewsCenterPager(Activity activity,NewsChildInfo info) {
		super(activity);
		this.info=info;
		}
	
	@Override
	public View initView() {
		/*
		 * TextView textview=new TextView(activity); textview.setText("新闻详情");
		 * textview.setTextSize(20); textview.setGravity(Gravity.CENTER);
		 * textview.setTextColor(Color.RED);
		 */
		view= View.inflate(activity, R.layout.menunewscenterpager,
				null);
		return view;
	}

	@Override
	public void initData() {
		mIndicator = (TabPageIndicator) view.findViewById(R.id.menunews_indicator);
		mViewpager = (ViewPager) view.findViewById(R.id.menunews_viewpager);
		mIbtn = (ImageButton) view.findViewById(R.id.menunews_ibtn);
		itempagers=new ArrayList<menuNewsCenterItemPager>();
		itempagers.clear();
		
		for(int i=0;i<info.children.size();i++){
		itempagers.add(new menuNewsCenterItemPager(activity,info.children.get(i).url));
		
		}
		if(myadapter==null){
			
			myadapter = new myadapter();
			mViewpager.setAdapter(myadapter);
		}else{
			myadapter.notifyDataSetChanged();
		}
		mIndicator.setViewPager(mViewpager);
		mIbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int currentPage=mViewpager.getCurrentItem();
				currentPage++;
				
				mViewpager.setCurrentItem(currentPage);
			}
		});
		mViewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				if(position==0){
					
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}else{
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
				mIndicator.setCurrentItem(position);
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
	}
	private class myadapter extends PagerAdapter{
		@Override
		public CharSequence getPageTitle(int position) {
			return info.children.get(position).title;
		}
		@Override
		public int getCount() {
			return itempagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			menuNewsCenterItemPager newsCenterItemPager = itempagers.get(position);
			View view2 = newsCenterItemPager.view;
			container.addView(view2);
			newsCenterItemPager.initData();
			return view2;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
		}
		
		
	}

}
