package com.ybbbi.zhbj.pager.menu.item;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;
import com.ybbbi.zhbj.R;
import com.ybbbi.zhbj.bean.NewBean;
import com.ybbbi.zhbj.bean.NewBean.News;
import com.ybbbi.zhbj.net.NetUrl;
import com.ybbbi.zhbj.pager.menu.baseMenuPager;
import com.ybbbi.zhbj.tool.SharedpreferencesTool;

public class menuNewsCenterItemPager extends baseMenuPager {
	private String url;
	private View v_viewpager;
	private View v_listview;
	@ViewInject(R.id.menuNewsCenter_listview)
	private ListView mListView;
	@ViewInject(R.id.menuNewsCenter_viewpager)
	public ViewPager mViewpager;
	@ViewInject(R.id.menuNewsCenter_textview)
	public TextView mTextview;
	@ViewInject(R.id.menuNewsCenter_indicator)
	public CirclePageIndicator mIndicator;
	private List<String> imageUrl = new ArrayList<String>();
	private List<String> titles = new ArrayList<String>();
	private List<News> news = new ArrayList<News>();
	private MyAdapter myAdapter;
	private listviewadapter myadapter2;

	public menuNewsCenterItemPager(Activity activity, String url) {
		super(activity);
		this.url = url;
	}

	@Override
	public View initView() {
		v_viewpager = View.inflate(activity,
				R.layout.menunewscenteritem_viewpager, null);
		v_listview = View.inflate(activity,
				R.layout.menunewscenteritem_listview, null);
		ViewUtils.inject(this, v_listview);
		ViewUtils.inject(this, v_viewpager);
		return v_listview;
	}

	@Override
	public void initData() {
		String string = SharedpreferencesTool.getString(activity,
				NetUrl.SERVERURL + url, "");
		if (TextUtils.isEmpty(string)) {
			processJson(string);

		}
		getData();

	}

	private void processJson(String json) {
		Gson gson = new Gson();
		NewBean newBean = gson.fromJson(json, NewBean.class);
		showMsg(newBean);

	}

	private void showMsg(NewBean bean) {
		if (bean.data.topnews.size() > 0) {
			imageUrl.clear();
			titles.clear();
			for (int i = 0; i < bean.data.topnews.size(); i++) {
				imageUrl.add(bean.data.topnews.get(i).topimage);
				titles.add(bean.data.topnews.get(i).title);
			}

			if (myAdapter == null) {
				myAdapter = new MyAdapter();

				mViewpager.setAdapter(myAdapter);
			} else {
				myAdapter.notifyDataSetChanged();
			}
			mIndicator.setViewPager(mViewpager);
			mIndicator.setSnap(true);
			mTextview.setText(titles.get(0));
			mIndicator.onPageSelected(0);
			if (mListView.getHeaderViewsCount() < 1) {
				mListView.addHeaderView(v_viewpager);
			}
		}
		if (bean.data.news.size() > 0) {
			news = bean.data.news;
			if (myadapter2 == null) {

				myadapter2 = new listviewadapter();
				mListView.setAdapter(myadapter2);
			} else {
				myadapter2.notifyDataSetInvalidated();
			}

		}
	}

	private class listviewadapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return news.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return news.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView textView = new TextView(activity);
			textView.setText("11");
			return textView;
		}

	}

	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageUrl.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(activity,
					R.layout.menunewscenteritem_vp_item, null);
			ImageView img = (ImageView) view
					.findViewById(R.id.menunewscenteritem_vp_iv);
			String url = imageUrl.get(position);
			BitmapUtils bitmap = new BitmapUtils(activity);
			bitmap.display(img, url);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

	}

	private void getData() {
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, NetUrl.SERVERURL + url,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;

						SharedpreferencesTool.saveString(activity,
								NetUrl.SERVERURL + url, result);
						processJson(result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}

				});
	}

}
