package com.ybbbi.googlestore.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/*
 *ybbbi
 *2019-06-19 13:36
 */public class MyPagerAdapter extends FragmentPagerAdapter {
     private ArrayList<Fragment> fragmentsList;
     private String [] title;
    public MyPagerAdapter(FragmentManager fm,ArrayList<Fragment> list,String [] title) {
        super(fm);
        this.fragmentsList=list;
        this.title=title;
    }

    @Override
    public Fragment getItem(int i) {

        return fragmentsList.get(i);
    }

    @Override
    public int getCount() {

        return fragmentsList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return title[position];
    }
}
