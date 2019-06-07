package com.ybbbi.zhbj.pager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.ybbbi.zhbj.HomeActivity;
import com.ybbbi.zhbj.bean.NewsCenterInfo;
import com.ybbbi.zhbj.net.NetUrl;
import com.ybbbi.zhbj.pager.menu.baseMenuPager;
import com.ybbbi.zhbj.pager.menu.menuComunicationCenterPager;
import com.ybbbi.zhbj.pager.menu.menuNewsCenterPager;
import com.ybbbi.zhbj.pager.menu.menuPhotosCenterPager;
import com.ybbbi.zhbj.pager.menu.menuTitleCenterPager;
import com.ybbbi.zhbj.tool.Constants;
import com.ybbbi.zhbj.tool.SharedpreferencesTool;

public class NewsCenterpager extends BasePager {
	private List<String> title;
	private List<baseMenuPager> baselist;
	public NewsCenterpager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
	/*	TextView tv = new TextView(activity);
		tv.setText("新闻中心");

		tv.setTextColor(Color.RED);
		tv.setTextSize(20);
		tv.setGravity(Gravity.CENTER);*/
		
		tv_title.setText("新闻");
		title_btn_menu.setVisibility(View.VISIBLE);

		String string = SharedpreferencesTool.getString(activity,
				Constants.result, "");
		if (!TextUtils.isEmpty(string)) {
			processJson(string);
		}
		getData();

		super.initData();
	}

	private void processJson(String json) {
		Gson gson = new Gson();
		NewsCenterInfo newsCenterInfo = gson.fromJson(json,
				NewsCenterInfo.class);
		setMenuFragment(newsCenterInfo);
	}

	private void setMenuFragment(NewsCenterInfo info) {
		title = new ArrayList<String>();

		title.clear();
		for (int i = 0; i < info.data.size(); i++) {
			title.add(info.data.get(i).title);
		}

		((HomeActivity) activity).getFragment().initList(title);
		baselist=new ArrayList<baseMenuPager>();
		baselist.clear();
		baselist.add(new menuNewsCenterPager(activity,info.data.get(0)));
		baselist.add(new menuTitleCenterPager(activity));
		baselist.add(new menuPhotosCenterPager(activity));
		baselist.add(new menuComunicationCenterPager(activity));
		switchPage(0);
	}

	private void getData() {
		HttpUtils httputils = new HttpUtils();
		httputils.send(HttpMethod.GET, NetUrl.NEWSCENTERURL,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException error, String msg) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						SharedpreferencesTool.saveString(activity,
								Constants.result, result);
						processJson(result);
					}
				});
	}

	public void switchPage(int position) {
		tv_title.setText(title.get(position));
		baseMenuPager basemenuPager = baselist.get(position);
		View rootView = basemenuPager.view;
		mContent.removeAllViews();
		mContent.addView(rootView);
		basemenuPager.initData();
		if(basemenuPager instanceof menuPhotosCenterPager){
			mImage.setVisibility(View.VISIBLE);
		}else{
			mImage.setVisibility(View.GONE);
			
		}
	}

}
