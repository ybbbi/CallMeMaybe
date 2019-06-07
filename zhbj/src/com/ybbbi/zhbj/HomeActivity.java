package com.ybbbi.zhbj;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.ybbbi.zhbj.fragment.HomeFragment;
import com.ybbbi.zhbj.fragment.MenuFragment;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.Window;

public class HomeActivity extends SlidingFragmentActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		init();
		initFragment();
		
	}

	private void initFragment() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
		beginTransaction.replace(R.id.home_root, new HomeFragment(), "home");
		beginTransaction.replace(R.id.menu_root, new MenuFragment(), "menu");
		beginTransaction.commit();
		
		
		
		
	}

	private void init() {
		SlidingMenu slidingMenu = getSlidingMenu();
		
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		setBehindContentView(R.layout.menu);
		slidingMenu.setBehindWidth(350);
	}
	public MenuFragment getFragment(){
		MenuFragment findFragmentByTag = (MenuFragment) getSupportFragmentManager().findFragmentByTag("menu");
		return findFragmentByTag;
	}
	public HomeFragment getHomeFragment(){
		HomeFragment home = (HomeFragment) getSupportFragmentManager().findFragmentByTag("home");
		return home;
	}

}
