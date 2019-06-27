package com.ybbbi.googlestore.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.adapter.FullImageAdapter;
import com.ybbbi.googlestore.global.OPTIONS;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScreenActivity extends AppCompatActivity {


    @BindView(R.id.vp_full)
    ViewPager vpFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        ButterKnife.bind(this);
        ArrayList<String> url = getIntent().getStringArrayListExtra("url");
        vpFull.setAdapter(new FullImageAdapter(url));
       int i=getIntent().getIntExtra("position",0);
        vpFull.setCurrentItem(i);

    }


}
