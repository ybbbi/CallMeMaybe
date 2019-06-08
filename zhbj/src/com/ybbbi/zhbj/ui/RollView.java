package com.ybbbi.zhbj.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class RollView extends ViewPager {

	private int downX;
	private int downY;

	/**
	 * @param context
	 * @param attrs
	 */
	public RollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public RollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:

			getParent().requestDisallowInterceptTouchEvent(true);
			downX = (int) ev.getX();
			downY = (int) ev.getY();

			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) ev.getX();
			int moveY = (int) ev.getY();
			if (Math.abs(downX - moveX) - Math.abs(downY - moveY) > 0) {
				if (downX - moveX > 0
						&& getCurrentItem() == getAdapter().getCount() - 1) {
					getParent().requestDisallowInterceptTouchEvent(false);

				} else if (downX - moveX > 0
						&& getCurrentItem() < getAdapter().getCount() - 1) {
					getParent().requestDisallowInterceptTouchEvent(true);
					
				} else if (downX - moveX < 0 && getCurrentItem() == 0) {
					getParent().requestDisallowInterceptTouchEvent(false);
					
				} else if (downX - moveX < 0 && getCurrentItem() > 0) {
					getParent().requestDisallowInterceptTouchEvent(true);
					
				}
			} else {
				getParent().requestDisallowInterceptTouchEvent(false);

			}
			break;
		case MotionEvent.ACTION_UP:

			break;

		}
		return super.dispatchTouchEvent(ev);
	}

}
