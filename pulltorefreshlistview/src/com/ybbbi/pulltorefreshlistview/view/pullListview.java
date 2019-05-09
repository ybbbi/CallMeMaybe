package com.ybbbi.pulltorefreshlistview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ybbbi.pulltorefreshlistview.R;

public class pullListview extends ListView implements OnScrollListener{

	private ImageView imageview;
	private int y;
	private View view;
	private int measuredHeight;
	private final int PULL_DOWN = 1;
	private final int REFRESH = 2;
	private final int REFRESHING = 3;
	private int CURRENTSTATE = PULL_DOWN;
	private TextView text;
	private ProgressBar progressbar;
	private RotateAnimation animation_up;
	private RotateAnimation animation_down;
	private boolean isLoad=false;
	public interface onRefreshlistener{
		void Refresh();
		void load();
	}
	private onRefreshlistener listener;
	private View footView;
	private int footheight;
	public void setonRefreshListener(onRefreshlistener listener){
		this.listener=listener;
	}
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public pullListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setOnScrollListener(this);
		view = View.inflate(getContext(), R.layout.header, null);
		view.measure(0, 0);
		measuredHeight = view.getMeasuredHeight();
		view.setPadding(0, -measuredHeight, 0, 0);


		animation();

		imageview = (ImageView) view.findViewById(R.id.im);
		text = (TextView) view.findViewById(R.id.text);
		progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
		this.addHeaderView(view);
		
		
		
		footView = View.inflate(getContext(), R.layout.footer	, null);
		
		footView.measure(0, 0);
		footheight = footView.getMeasuredHeight();
		footView.setPadding(0, 0, 0, -footheight);
		
		this.addFooterView(footView);

	}
	

	private void animation() {
		animation_up = new RotateAnimation(0, -180,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation_down = new RotateAnimation(-180, -360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation_up.setDuration(500);
		animation_up.setFillAfter(true);

		animation_down.setDuration(500);
		animation_down.setFillAfter(true);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public pullListview(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public pullListview(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			y = (int) ev.getY();

			break;
		case MotionEvent.ACTION_MOVE:
			int y2 = (int) ev.getY();
			int distance = y2 - y;
			if (distance > 0 && getFirstVisiblePosition() == 0) {
				int paddingtop = distance - measuredHeight;
				view.setPadding(0, paddingtop, 0, 0);
				if (paddingtop > 0 && CURRENTSTATE == PULL_DOWN) {
					CURRENTSTATE = REFRESH;
					switchState();
				}
				if (paddingtop < 0&&CURRENTSTATE==REFRESH) {
					CURRENTSTATE = PULL_DOWN;
					switchState();
				}

				return true;
			}

			break;
		case MotionEvent.ACTION_UP:
			if (CURRENTSTATE == REFRESH) {
				CURRENTSTATE = REFRESHING;
				switchState();
				view.setPadding(0, 0, 0, 0);
				if(listener!=null){
					listener.Refresh();
				}

			}
			if (CURRENTSTATE == PULL_DOWN) {
				view.setPadding(0, -measuredHeight, 0, 0);

			}
			break;

		}
		return super.onTouchEvent(ev);
	}

	private void switchState() {
		switch (CURRENTSTATE) {
		case PULL_DOWN:
			imageview.startAnimation(animation_down);
			text.setText("下拉刷新");
			break;
		case REFRESH:
			imageview.startAnimation(animation_up);
			text.setText("松开刷新");
			break;
		case REFRESHING:
			imageview.clearAnimation();
			imageview.setVisibility(View.GONE);
			progressbar.setVisibility(View.VISIBLE);
			text.setText("正在刷新");
			break;

		}
	}
	public void finish(){
		if(CURRENTSTATE==REFRESHING){
			CURRENTSTATE=REFRESH;
			text.setText("下拉刷新");
			progressbar.setVisibility(View.GONE);
			imageview.setVisibility(View.VISIBLE);
			view.setPadding(0, -measuredHeight, 0, 0);
		}
		if(isLoad){
			isLoad=false;
			footView.setPadding(0, 0, 0, -footheight);
		}
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
			
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState==OnScrollListener.SCROLL_STATE_IDLE&&getAdapter().getCount()-1==getLastVisiblePosition()){
			footView.setPadding(0, 0, 0, 0);
			setSelection(getAdapter().getCount()-1);
			isLoad=true;
			if(listener!=null){
				
				listener.load();
			}
		}
	}

}
