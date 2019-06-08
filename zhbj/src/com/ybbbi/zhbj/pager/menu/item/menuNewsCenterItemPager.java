package com.ybbbi.zhbj.pager.menu.item;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.ybbbi.zhbj.tool.Constants;
import com.ybbbi.zhbj.tool.SharedpreferencesTool;
import com.ybbbi.zhbj.ui.PulltoRefreshListview;
import com.ybbbi.zhbj.ui.PulltoRefreshListview.onRefreshListener;
import com.ybbbi.zhbj.ui.RollView;

public class menuNewsCenterItemPager extends baseMenuPager {
	private String url;
	private View v_viewpager;
	private View v_listview;
	@ViewInject(R.id.menuNewsCenter_listview)
	private PulltoRefreshListview mListView;
	@ViewInject(R.id.menuNewsCenter_viewpager)
	public RollView mViewpager;
	@ViewInject(R.id.menuNewsCenter_textview)
	public TextView mTextview;
	@ViewInject(R.id.menuNewsCenter_indicator)
	public CirclePageIndicator mIndicator;
	private List<String> imageUrl = new ArrayList<String>();
	private List<String> titles = new ArrayList<String>();
	private List<News> news = new ArrayList<News>();
	private MyAdapter myAdapter;
	private listviewadapter myadapter2;
	private Handler handler;
	private String loadmoreUrl;
	private List<String> readIds=new ArrayList<String>();
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
		mListView.setViewpager(v_viewpager);
		return v_listview;
	}

	@Override
	public void initData() {
		
		mListView.setOnRefreshListener(new onRefreshListener() {
			
			@Override
			public void refresh() {
				getData(url,false);
			}
			
			@Override
			public void loadMore() {
				getData(loadmoreUrl,true);				
			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(news.get(position-1).isRead==false){
					news.get(position-1).isRead=true;
					myadapter2.notifyDataSetChanged();
					
					String readid = SharedpreferencesTool.getString(activity, Constants.newsread, "");
					
					SharedpreferencesTool.saveString(activity, Constants.newsread, readid+"!"+news.get(position-1).id);
				}
			}
		});
		
		
		
		String string = SharedpreferencesTool.getString(activity,
				NetUrl.SERVERURL + url, "");
		if (!TextUtils.isEmpty(string)) {
			processJson(string, false);

		}
		getData(url,false);

	}

	private void processJson(String json,boolean isLoadMore) {
		Gson gson = new Gson();
		NewBean newBean = gson.fromJson(json, NewBean.class);
		
		showMsg(newBean, isLoadMore);

	}

	private void showMsg(NewBean bean,boolean isLoadMore) {
		 loadmoreUrl = bean.data.more;
		 if(!isLoadMore){
			 
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
				 
				 /*
				  * if (mListView.getHeaderViewsCount() < 1) {
				  * mListView.addHeaderView(v_viewpager); }
				  */
			 }
			 
			 mIndicator.setViewPager(mViewpager);
			 mIndicator.setSnap(true);
			 
			 mTextview.setText(titles.get(0));
			 mIndicator.onPageSelected(0);
			 mViewpager.setCurrentItem(0);
			 if (handler == null) {
				 
				 handler = new Handler() {
					 public void handleMessage(android.os.Message msg) {
						 int currentItem = mViewpager.getCurrentItem();
						 currentItem++;
						 currentItem = currentItem % (imageUrl.size());
						 mViewpager.setCurrentItem(currentItem);
						 handler.sendEmptyMessageDelayed(0, 3000);
					 };
					 
				 };
				 handler.sendEmptyMessageDelayed(0, 3000);
			 }
			 mViewpager.setOnPageChangeListener(new OnPageChangeListener() {
				 
				 @Override
				 public void onPageSelected(int position) {
					 mTextview.setText(titles.get(position));
					 mIndicator.onPageSelected(position);
				 }
				 
				 @Override
				 public void onPageScrolled(int position, float positionOffset,
						 int positionOffsetPixels) {
					 
				 }
				 
				 @Override
				 public void onPageScrollStateChanged(int state) {
					 // TODO Auto-generated method stub
					 
				 }
			 });
		 }
		if (bean.data.news.size() > 0) {
			if(isLoadMore){
				news.addAll(bean.data.news);
			}else{
				news=bean.data.news;
			}
			for (News info : news) {
				if(readIds.contains(info.id)){
					info.isRead=true;
				}else{
					info.isRead=false;
				}
			}
			if (myadapter2 == null) {

				myadapter2 = new listviewadapter();
				mListView.setAdapter(myadapter2);
			} else {
				myadapter2.notifyDataSetInvalidated();
			}

		}
		mListView.finish();
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
			Holder h;

			if (convertView == null) {
				h = new Holder();
				convertView = View.inflate(activity,
						R.layout.menunewscenteritem_listview_item, null);
				h.iv_item = (ImageView) convertView
						.findViewById(R.id.item_iv_icon);
				h.tv_item = (TextView) convertView
						.findViewById(R.id.item_textview_title);
				h.tv_item_time = (TextView) convertView
						.findViewById(R.id.item_textview_time);
				convertView.setTag(h);
			} else {
				h = (Holder) convertView.getTag();

			}
			h.tv_item.setText(news.get(position).title);
			h.tv_item_time.setText(news.get(position).pubdate);
			BitmapUtils bu = new BitmapUtils(activity);
			bu.display(h.iv_item, news.get(position).listimage);
			if(news.get(position).isRead){
				h.tv_item.setTextColor(Color.GRAY);
			}else{
				h.tv_item.setTextColor(Color.BLACK);
				
			}
			return convertView;
		}

	}

	static class Holder {
		private ImageView iv_item;
		private TextView tv_item, tv_item_time;

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
			view.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						handler.removeCallbacksAndMessages(null);
						break;
					case MotionEvent.ACTION_UP:
						handler.sendEmptyMessageDelayed(0, 3000);
						break;
					case MotionEvent.ACTION_CANCEL:
						handler.sendEmptyMessageDelayed(0, 3000);

						break;

					}
					return true;
				}
			});
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

	private void getData(final String url,final boolean isLoadMore) {
		String readId = SharedpreferencesTool.getString(activity, Constants.newsread, "");
		if(!TextUtils.isEmpty(readId)){
			String[] split = readId.split("!");
			readIds.clear();
			for (int i = 0; i < split.length; i++) {
				readIds.add(split[i]);
			}
			
		}
		if(!TextUtils.isEmpty(url)){
			
			HttpUtils http = new HttpUtils();
			http.send(HttpMethod.GET, NetUrl.SERVERURL + url,
					new RequestCallBack<String>() {
				
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String result = responseInfo.result;
					
					SharedpreferencesTool.saveString(activity,
							NetUrl.SERVERURL + url, result);
					processJson(result,isLoadMore);
				}
				
				@Override
				public void onFailure(HttpException error, String msg) {
					
				}
				
			});
		}else{
			Toast.makeText(activity, "已是最新数据，无需再次刷新", 0).show();
			mListView.finish();
		}
	}

}
