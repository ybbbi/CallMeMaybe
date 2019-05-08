package com.ybbbi.pulltorefreshlistview.view;


import com.ybbbi.pulltorefreshlistview.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class pullListview extends ListView {

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public pullListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		add();
	}

	private void add() {
		View view = View.inflate(getContext(), R.layout.header, null);
		this.addHeaderView(view);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public pullListview(Context context, AttributeSet attrs) {
		this(context, attrs,-1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public pullListview(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}

}
