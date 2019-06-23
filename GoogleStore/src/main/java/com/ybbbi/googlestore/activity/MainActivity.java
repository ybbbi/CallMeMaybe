package com.ybbbi.googlestore.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.gxz.PagerSlidingTabStrip;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.adapter.MyPagerAdapter;
import com.ybbbi.googlestore.fragment.APPFragment;
import com.ybbbi.googlestore.fragment.CategoryFragment;
import com.ybbbi.googlestore.fragment.GameFragment;
import com.ybbbi.googlestore.fragment.HotFragment;
import com.ybbbi.googlestore.fragment.MainFragment;
import com.ybbbi.googlestore.fragment.RecommendFragment;
import com.ybbbi.googlestore.fragment.SubjectFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.drawLayout)
    DrawerLayout drawLayout;
    @BindView(R.id.pagerslidingTab)
    PagerSlidingTabStrip pagerslidingTab;
    @BindView(R.id.google_viewpager)
    ViewPager googleViewpager;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SetActionbar();
        initData();

    }

        /**
            初始化数据
         */
    private void initData() {
        /**
            适配数据
         */
        ArrayList<Fragment> mList = new ArrayList<>();
        mList.add(new MainFragment());
        mList.add(new APPFragment());
        mList.add(new GameFragment());
        mList.add(new SubjectFragment());
        mList.add(new RecommendFragment());
        mList.add(new CategoryFragment());
        mList.add(new HotFragment());
        String[] array = getResources().getStringArray(R.array.tab_names);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mList,array);
        googleViewpager.setAdapter(myPagerAdapter);
        pagerslidingTab.setViewPager(googleViewpager);
        pagerslidingTab.setIndicatorColor(getResources().getColor(R.color.indicator));
        pagerslidingTab.setIndicatorHeight(3);
        drawLayout.addDrawerListener(actionBarDrawerToggle);

    }

    private void SetActionbar() {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawLayout, 0, 0);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        actionBarDrawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }
}
