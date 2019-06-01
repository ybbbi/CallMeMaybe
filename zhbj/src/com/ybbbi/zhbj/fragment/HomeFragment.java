package com.ybbbi.zhbj.fragment;

import java.util.ArrayList;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ybbbi.zhbj.R;
import com.ybbbi.zhbj.pager.BasePager;
import com.ybbbi.zhbj.pager.GOVpager;
import com.ybbbi.zhbj.pager.Homepager;
import com.ybbbi.zhbj.pager.NewsCenterpager;
import com.ybbbi.zhbj.pager.Settingpager;
import com.ybbbi.zhbj.pager.Smartservicepager;
import com.ybbbi.zhbj.ui.NoScrollviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class HomeFragment extends BaseFragment {
	
	public NoScrollviewpager mViewpager;
	private ArrayList<BasePager> mList;
	private Myadapter myadapter;
	private RadioGroup radioGroup;
	@Override
	public View initView() {
		 view = View.inflate(activity, R.layout.homefragment,null);
		
		return view;
	}

	@Override
	public void initData() {

		mViewpager = (NoScrollviewpager) view.findViewById(R.id.homefragment_vp);
		radioGroup = (RadioGroup) view.findViewById(R.id.fragment_radioGroup);
		mList=new ArrayList<BasePager>();
		mList.clear();
		mList.add(new Homepager(activity));
		mList.add(new NewsCenterpager(activity));
		mList.add(new Smartservicepager(activity));
		mList.add(new GOVpager(activity));
		mList.add(new Settingpager(activity));
		radioGroup.check(R.id.home_radio_first);
		if(myadapter==null){
			myadapter = new Myadapter();
			
			mViewpager.setAdapter(myadapter);
		}else{
			myadapter.notifyDataSetChanged();
		}
		mList.get(0).initData();
		
		mViewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				BasePager basePager = mList.get(position);
				basePager.initData();
				
				if(position==0||position==4){
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}else{
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					
				}
				
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.home_radio_first:
					mViewpager.setCurrentItem(0,false);
					break;
				case R.id.home_radio_news:
					mViewpager.setCurrentItem(1,false);
					break;
				case R.id.home_radio_service:
					mViewpager.setCurrentItem(2,false);
					break;
				case R.id.home_radio_gov:
					mViewpager.setCurrentItem(3,false);
					break;
				case R.id.home_radio_setting:
					mViewpager.setCurrentItem(4,false);
					break;
				}
			}
		});
	}
	private class Myadapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			BasePager pager = mList.get(position);
			View view = pager.view;
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view==object;
		}
		
	}

}
