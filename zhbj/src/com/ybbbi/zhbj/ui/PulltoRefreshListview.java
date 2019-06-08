package com.ybbbi.zhbj.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ybbbi.zhbj.R;

public class PulltoRefreshListview extends ListView  implements OnScrollListener{
	private static final int PULL_DOWN = 1;
	private static final int REFRESH = 2;
	private static final int REFRESHING = 3;
	private static int CURRENTSTATE = PULL_DOWN;

	private View headerView;
	private LinearLayout mHeaderRoot;
	private LinearLayout refresh_ll_header;
	private TextView refresh_time;
	private int downY = -1;
	private int measuredHeight;
	private ImageView refresh_iv_arrow;
	private ProgressBar refresh_pb_loading;
	private TextView title;
	private RotateAnimation rAnimation_up;
	private RotateAnimation rAnimation_down;
	private View viewpager;
	private View footerView;
	private int mfooterView;
	private boolean isLoadMore=false;

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public PulltoRefreshListview(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initAnimation();
		getTime();
		addHeader();
		addFooter();
		setOnScrollListener(this);
	}

	private String getTime() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String time = simpleDateFormat.format(date);
		return time;
	}

	private void addHeader() {
		headerView = View.inflate(getContext(), R.layout.refresh_header, null);
		mHeaderRoot = (LinearLayout) headerView
				.findViewById(R.id.refresh_ll_root);
		refresh_iv_arrow = (ImageView) headerView
				.findViewById(R.id.refresh_iv_arrow);
		refresh_pb_loading = (ProgressBar) headerView
				.findViewById(R.id.refresh_pb_loading);
		title = (TextView) headerView.findViewById(R.id.refresh_tv_text);


		refresh_ll_header = (LinearLayout) headerView
				.findViewById(R.id.refresh_ll_header);
		refresh_ll_header.measure(0, 0);
		measuredHeight = refresh_ll_header.getMeasuredHeight();
		refresh_ll_header.setPadding(0, -measuredHeight, 0, 0);
		addHeaderView(headerView);
		refresh_time = (TextView) headerView.findViewById(R.id.refresh_tv_time);
		refresh_time.setText(getTime());
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public PulltoRefreshListview(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	/**
	 * @param context
	 */
	public PulltoRefreshListview(Context context) {
		this(context, null);
	}

	public void setViewpager(View view) {
		viewpager = view;
		mHeaderRoot.addView(view);

	}

	public void initAnimation() {
		rAnimation_up = new RotateAnimation(0, -180,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rAnimation_up.setDuration(500);
		rAnimation_up.setFillAfter(true);

		rAnimation_down = new RotateAnimation(-180, -360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rAnimation_down.setDuration(500);
		rAnimation_down.setFillAfter(true);

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = (int) ev.getY();
			break;

		case MotionEvent.ACTION_MOVE:
			if(CURRENTSTATE==REFRESHING){
				return true;
			}
			int [] locationOnScreen=new int [2];
			getLocationOnScreen(locationOnScreen);
			int listivewY = locationOnScreen[1];
			
			int [] viewpagerlocation=new int[2];
			viewpager.getLocationOnScreen(viewpagerlocation);
			int viewpagerlocationY=viewpagerlocation[1];
			if(listivewY>viewpagerlocationY){
				downY=-1;
				break;
			}
			if (downY == -1) {
				downY = (int) ev.getY();
			}
			int moveY = (int) ev.getY();
			if (moveY - downY > 0 && getFirstVisiblePosition() == 0) {
				int distanceY = moveY - downY - measuredHeight;
				refresh_ll_header.setPadding(0, distanceY, 0, 0);
				if (distanceY > 0 && CURRENTSTATE == PULL_DOWN) {
					CURRENTSTATE = REFRESH;
					switchOPTION();
				} else if (distanceY < 0 && CURRENTSTATE == REFRESH) {
					CURRENTSTATE = PULL_DOWN;
					switchOPTION();
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (CURRENTSTATE == REFRESH) {
				CURRENTSTATE = REFRESHING;
				refresh_ll_header.setPadding(0, 0, 0, 0);
				switchOPTION();
				if(listener!=null){
					listener.refresh();
				}
			} else if (CURRENTSTATE == PULL_DOWN) {
				refresh_ll_header.setPadding(0, -measuredHeight, 0, 0);

			}
			break;

		}
		return super.onTouchEvent(ev);
	}

	private void switchOPTION() {
		switch (CURRENTSTATE) {
		case PULL_DOWN:
			title.setText("下拉刷新");
			refresh_iv_arrow.startAnimation(rAnimation_down);
			
			break;

		case REFRESH:

			title.setText("松开刷新");
			refresh_iv_arrow.startAnimation(rAnimation_up);
			break;
		case REFRESHING:
			title.setText("正在刷新");
			refresh_pb_loading.setVisibility(View.VISIBLE);
			refresh_iv_arrow.clearAnimation();
			refresh_iv_arrow.setVisibility(View.GONE);
			refresh_time.setText(getTime());
			
			break;
		}
	}
	public void finish(){
		if(CURRENTSTATE==REFRESHING){
			CURRENTSTATE=PULL_DOWN;
			title.setText("下拉刷新");
			refresh_pb_loading.setVisibility(View.GONE);
			refresh_iv_arrow.setVisibility(View.VISIBLE);
			refresh_ll_header.setPadding(0, -measuredHeight, 0, 0);
		}
		if(isLoadMore){
			
			footerView.setPadding(0, 0, 0, -mfooterView);
			isLoadMore=false;
		}
	}
	private void addFooter(){
		footerView = View.inflate(getContext(), R.layout.refresh_footer, null);
		footerView.measure(0, 0);
		mfooterView = footerView.getMeasuredHeight();
		footerView.setPadding(0, 0, 0, -mfooterView);
		
		addFooterView(footerView);
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState==OnScrollListener.SCROLL_STATE_IDLE&&getLastVisiblePosition()==getAdapter().getCount()-1&&isLoadMore==false){
			isLoadMore=true;
			footerView.setPadding(0, 0, 0,0);
			setSelection(getAdapter().getCount()-1);
			if(listener!=null){
				listener.loadMore();
			}
		}
	}	

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}
	
	public interface onRefreshListener{
		public void refresh();
		public void loadMore();
	}
	private onRefreshListener listener;
	public void setOnRefreshListener(onRefreshListener listener){
		this.listener=listener;
	}

}
