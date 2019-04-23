package com.ybbbi.safe;

import com.ybbbi.safe.utils.MD5utils;
import com.ybbbi.safe.utils.Sharedpreferences;
import com.ybbbi.safe.utils.constants;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UnlockActivity extends Activity implements OnClickListener {

	private String packageName;
	private Button btn_cancel;
	private Button btn_confirm;
	private EditText et_pwd;
	private ImageView im_icon;
	private TextView tv_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlock);
		packageName = getIntent().getStringExtra("packagename");
		init();

	}
	@Override
	protected void onStop() {
		finish();
		super.onStop();
	}
	private void init() {
		btn_cancel = (Button) findViewById(R.id.unlock_button_cancel);
		btn_confirm = (Button) findViewById(R.id.unlock_button_confirm);
		et_pwd = (EditText) findViewById(R.id.unlock_edittext);
		im_icon = (ImageView) findViewById(R.id.unlock_imageview);
		tv_name = (TextView) findViewById(R.id.unlock_textview);
		btn_cancel.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		getPackagemsg();
	}

	private void getPackagemsg() {
		PackageManager pm=getPackageManager();
		try {
			ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);
			Drawable icon = applicationInfo.loadIcon(pm);
			String name = applicationInfo.loadLabel(pm).toString();
			im_icon.setImageDrawable(icon);
			tv_name.setText(name);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.unlock_button_cancel:
			home();
			break;
		case R.id.unlock_button_confirm:
			String pwd	 = et_pwd.getText().toString().trim();
			String mPwd = Sharedpreferences.getString(this, constants.isSettingPW, "");
			if(mPwd.equals(MD5utils.toMD5(pwd))){
				Intent intent =new Intent();
				intent.setAction("com.ybbbi.safe.UNLOCK");
				intent.putExtra("pkgNAME", packageName);
				sendBroadcast(intent);
				
				
				
				
				finish();
			}else{
				Toast.makeText(getApplicationContext(), "密码错误", 0).show();
			}
			break;
		}
	}
	@Override
	public void onBackPressed() {
		home();
		
		
		super.onBackPressed();
	}

	private void home() {
		Intent intent=new Intent();
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.HOME");
		startActivity(intent);
	}

}
