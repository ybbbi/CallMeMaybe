package com.ybbbi.menu;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class SlidingMenu extends ViewGroup {

	private int nextX;
	private View main;
	private View menu;
	private int x;
	private int x2;
	private int width;
	private int distance;
	private Scroller scroller;
	private int downX;
	private int downY;

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		scroller = new Scroller(context);
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, -1);

	}

	private void scroll(int startx, int tox) {

		int dx = tox - startx;
		int time = 1000 / width;

		scroller.startScroll(startx, 0, dx, 0, Math.abs(dx * time));
		invalidate();
	}

	public SlidingMenu(Context context) {
		this(context, null);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		main = getChildAt(0);
		menu = getChildAt(1);
		main.measure(widthMeasureSpec, heightMeasureSpec);
		width = menu.getLayoutParams().width;
		menu.measure(width, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		main.layout(l, t, r, b);
		menu.layout(l - width, t, l, b);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			x = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			x2 = (int) event.getX();

			distance = nextX + x2 - x;
			if (distance > width) {
				distance = width;
			}
			if (distance < 0) {
				distance = 0;
			}
			scrollTo(-distance, 0);
			break;
		case MotionEvent.ACTION_UP:
			nextX = distance;
			if (distance < width / 2) {
				scrollTo(0, 0);
				nextX = 0;
			} else {
				scrollTo(-width, 0);
				nextX = width;
			}
			scroll(distance, nextX);
			break;
		}
		return true;
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			int currX = scroller.getCurrX();
			scrollTo(-currX, 0);
			invalidate();

		}

		super.computeScroll();
	}

	public void menustate() {
		int scrollX = -getScrollX();
		if (scrollX == 0) {
			distance = 0;
			nextX = width;
		}
		if (scrollX > 0) {
			distance = width;
			nextX = 0;
		}
		scroll(distance, nextX);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = (int) ev.getX();
			downY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_UP:

			break;
		case MotionEvent.ACTION_MOVE:
			int movex = (int) ev.getX();
			int movey = (int) ev.getY();
			int distancex = movex - downX;
			int distancey = movey - downY;
			if (Math.abs(distancex) > Math.abs(distancey)) {
				return true;
			}
			break;

		}
		return super.onInterceptTouchEvent(ev);

	}

}
