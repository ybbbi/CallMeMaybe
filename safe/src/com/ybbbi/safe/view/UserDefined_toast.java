package com.ybbbi.safe.view;

import com.ybbbi.safe.database.dao.addressDB_DAO;

import android.R;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class UserDefined_toast {

	private Context context1;
	private WindowManager wm;
	private TextView tv;
	private View v;
	private WindowManager.LayoutParams params;

	public UserDefined_toast(Context context) {
		// 有参构造
		this.context1 = context;
	}

	public void show(String number) {
		wm = (WindowManager) context1.getSystemService(context1.WINDOW_SERVICE);
		params = new WindowManager.LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				//| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.format = PixelFormat.TRANSLUCENT;
		params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
		v = View.inflate(context1, com.ybbbi.safe.R.layout.toast_user_defined,
				null);
		tv = (TextView) v.findViewById(com.ybbbi.safe.R.id.toast_tv);

		tv.setText(number);
		v.setOnTouchListener(new OnTouchListener() {

			private float startx;
			private float starty;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				// 按下
				case MotionEvent.ACTION_DOWN:
					startx = event.getRawX();
					starty = event.getRawY();
					
					break;
					//抬起
				case MotionEvent.ACTION_UP:
					
					break;
					//移动
				case MotionEvent.ACTION_MOVE:
					float movex = event.getRawX();
					float movey =event.getRawY();
					
					int x=(int) (movex-startx);
					int y=(int)(movey-starty);
					
					params.x+=x;
					params.y+=y;
					wm.updateViewLayout(v, params);
					
					
					startx=movex;
					starty=movey;
					break;
				}
				return true;
			}
		});
		wm.addView(v, params);
	}

	public void hide() {
		// 查看toast.handlehide()方法
		if (v != null) {
			if (v.getParent() != null) {

				wm.removeView(v);
			}
			wm = null;
			v = null;
		}
	}
}
