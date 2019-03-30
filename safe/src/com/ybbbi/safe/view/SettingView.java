package com.ybbbi.safe.view;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingView extends RelativeLayout {
	private boolean mButtonOn;
	private ImageView imageview_setting;
	private TextView textview_setting;
	private String NAMESPACE = "http://schemas.android.com/apk/res/com.ybbbi.safe";

	public SettingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		View view = View.inflate(getContext(),
				com.ybbbi.safe.R.layout.settingview, null);
		this.addView(view);
		init();
		// String setting_auto =
		// attrs.getAttributeValue("http://schemas.android.com/apk/res/com.ybbbi.safe",
		// "Title");
		String setting_auto = attrs.getAttributeValue(NAMESPACE, "Title");
		textview_setting.setText(setting_auto);
		boolean isCache = attrs.getAttributeBooleanValue(NAMESPACE, "cache",
				false);
		imageview_setting.setVisibility(isCache ? View.VISIBLE : View.GONE);
	}

	private void init() {
		textview_setting = (TextView) findViewById(com.ybbbi.safe.R.id.textview_setting);
		imageview_setting = (ImageView) findViewById(com.ybbbi.safe.R.id.imageview_setting);

	}

	public SettingView(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, -1);
	}

	public SettingView(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public void isButtonOn(boolean buttonOn) {
		mButtonOn = buttonOn;
		if(buttonOn){
			imageview_setting.setImageResource(com.ybbbi.safe.R.drawable.on);
		}else{
			imageview_setting.setImageResource(com.ybbbi.safe.R.drawable.off);
			
		}
		
		
	}
	public boolean Buttonstate(){
		/*保存按钮状态
		*/
		return mButtonOn;
	}
	public void setButtonstate(){
		isButtonOn(!mButtonOn);
	}

}
