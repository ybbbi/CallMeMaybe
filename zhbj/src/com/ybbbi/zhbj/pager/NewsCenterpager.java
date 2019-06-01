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
import com.ybbbi.zhbj.tool.Constants;
import com.ybbbi.zhbj.tool.SharedpreferencesTool;


public class NewsCenterpager extends BasePager {
	private List<String> title;

	public NewsCenterpager(Activity activity) {
		super(activity);
	}
	@Override
	public void initData() {
		TextView tv=new TextView(activity);
		tv.setText("新闻中心");
		
		tv.setTextColor(Color.RED);
		tv.setTextSize(20);
		tv.setGravity(Gravity.CENTER);
		mContent.addView(tv);
		tv_title.setText("新闻");
		title_btn_menu.setVisibility(View.VISIBLE);
		
		String string = SharedpreferencesTool.getString(activity,Constants.result, "");
		if(!TextUtils.isEmpty(string)){
			processJson(string);
		}
		getData();
		
		
		super.initData();
	}
	private void processJson(String json) {
		Gson gson=new Gson();
		NewsCenterInfo newsCenterInfo = gson.fromJson(json, NewsCenterInfo.class);
		setMenuFragment(newsCenterInfo);
	}
	private void setMenuFragment(NewsCenterInfo info) {
		title=new ArrayList<String>();
		
		title.clear();
		for (int i = 0; i < info.data.size(); i++) {
			title.add(info.data.get(i).title);
		}
		
		((HomeActivity)activity).getFragment().initList(title);
	
	}
	private void getData() {
		HttpUtils httputils=new HttpUtils();
		httputils.send(HttpMethod.GET, NetUrl.NEWSCENTERURL, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException error, String msg) {
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result=responseInfo.result;
				SharedpreferencesTool.saveString(activity, Constants.result, result);
				processJson(result);
			}
		});
	}

}
