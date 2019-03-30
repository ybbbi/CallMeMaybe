package com.ybbbi.safe.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

//自定义textview
public class Home_TextView extends TextView {

	public Home_TextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		

	}

	public Home_TextView(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public Home_TextView(Context context) {
		this(context, null);
	}

	@Override
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		if (focused) {
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
	}
}
