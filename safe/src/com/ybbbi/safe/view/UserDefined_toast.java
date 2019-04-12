package com.ybbbi.safe.view;

import android.R;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class UserDefined_toast {

	private Context context1;
	private WindowManager wm;
	private TextView tv;
	public   UserDefined_toast(Context context){
		//有参构造
		this.context1=context;
	}
	public void show(String number){
		wm = (WindowManager) context1.getSystemService(context1.WINDOW_SERVICE);
		WindowManager.LayoutParams params=new WindowManager.LayoutParams();
		  params.height = WindowManager.LayoutParams.WRAP_CONTENT;
          params.width = WindowManager.LayoutParams.WRAP_CONTENT;
          params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                  | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                  | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
          params.format = PixelFormat.TRANSLUCENT;
          params.type = WindowManager.LayoutParams.TYPE_TOAST;
		View v=View.inflate(context1, com.ybbbi.safe.R.layout.toast_user_defined, null);
		tv = (TextView) v.findViewById(com.ybbbi.safe.R.id.toast_tv);
		tv.setText(number);
		wm.addView(v, params);
	}
}	
