package com.ybbbi.safe;

import com.ybbbi.safe.utils.MD5utils;
import com.ybbbi.safe.utils.Sharedpreferences;
import com.ybbbi.safe.utils.constants;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {

	private ImageView image_logo;
	private final String[] Title = new String[] { "手机防盗", "骚扰拦截", "软件管家",
			"进程管理", "流量统计", "手机杀毒", "缓存清理", "常用工具" };
	private final String[] Info = new String[] { "远程定位手机", "全面拦截骚扰", "管理您的软件",
			"管理运行进程", "流量一目了然", "病毒无处藏身", "系统快如火箭", "工具大全" };
	private final int[] Icon = new int[] { R.drawable.sjfd, R.drawable.srlj,
			R.drawable.rjgj, R.drawable.jcgl, R.drawable.lltj, R.drawable.sjsd,
			R.drawable.hcql, R.drawable.cygj };
	private GridView home_GV_gridview;

	private AlertDialog alertDialog;
	private AlertDialog alertDialog_Confirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);

		image_logo = (ImageView) findViewById(R.id.Image_logo);
		animationStart();
		home_GV_gridview = (GridView) findViewById(R.id.home_GV_gridview);
		MyBaseAdapter mybaseadapter = new MyBaseAdapter();
		home_GV_gridview.setAdapter(mybaseadapter);

		home_GV_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:

					String pw = Sharedpreferences.getString(
							getApplicationContext(), constants.isSettingPW, "");
					if (pw == null || pw.length() == 0) {

						showPwDialog();
					} else {
						// Toast.makeText(getApplicationContext(), "弹出验证对话框",
						// 0).show();
						showConfirm();
					}
					break;
				case 1:
					Intent intent=new Intent(HomeActivity.this,BlackListActivity.class);
					startActivity(intent);
					
					
					
					break;
				case 2:
					Intent intent2 =new Intent(HomeActivity.this,ProgressManagerActivity.class);
					startActivity(intent2);

					break;
				case 3:
					Intent intent3=new Intent(HomeActivity.this,ProcessedManagerActivity.class);
					startActivity(intent3);
					break;
				case 4:

					break;
				case 5:

					break;
				case 6:

					break;
				case 7:
					Intent intent7 =new Intent(HomeActivity.this,Tools_Activity.class);
					startActivity(intent7);
					break;

				}

			}
		});
	}
	
	private void showConfirm() {

		AlertDialog.Builder builder = new Builder(this);
		
		final View view = View.inflate(getApplicationContext(),
				R.layout.home_passwordconfirm_dialog, null);
		builder.setView(view);
		final TextView mPassword = (TextView) view
				.findViewById(R.id.edittext_Home_passwordConfirm);

		Button button_password_sure = (Button) view
				.findViewById(R.id.button_passwordConfirm_sure);
		Button button_password_cancel = (Button) view
				.findViewById(R.id.button_passwordConfirm_cancel);
		button_password_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alertDialog_Confirm.dismiss();
			}
		});
		button_password_sure.setOnClickListener(new OnClickListener() {
			// 匿名内部类访问成员变量需要加final修饰
			@Override
			public void onClick(View v) {
				String password = mPassword.getText().toString().trim();

				if (password.length() == 0 || password == null) {
					Toast.makeText(getApplicationContext(), "密码不能为空",
							Toast.LENGTH_SHORT).show();
					
					return;
				}
				String pwd = Sharedpreferences.getString(
						getApplicationContext(), constants.isSettingPW, "");
				if (MD5utils.toMD5(password).equals(pwd)) {
					Toast.makeText(getApplicationContext(), "验证成功",
							Toast.LENGTH_SHORT).show();
					alertDialog_Confirm.dismiss();
					
					enterSetActivity();

				} else {
					Toast.makeText(getApplicationContext(), "密码错误,请重新输入",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		alertDialog_Confirm = builder.create();
		alertDialog_Confirm.show();

	}

	private void showPwDialog() {
		AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(getApplicationContext(),
				R.layout.home_password_dialog, null);

		final TextView mPassword = (TextView) view
				.findViewById(R.id.edittext_Home_password);
		final TextView mRepassword = (TextView) view
				.findViewById(R.id.edittext_Home_repassword);

		Button button_password_sure = (Button) view
				.findViewById(R.id.button_password_sure);
		Button button_password_cancel = (Button) view
				.findViewById(R.id.button_password_cancel);
		// 这里如果使用findviewbyid会报空指针异常,在oncreate方法的布局文件中找,当然找不到

		button_password_sure.setOnClickListener(new OnClickListener() {
			// 匿名内部类访问成员变量需要加final修饰
			@Override
			public void onClick(View v) {
				String password = mPassword.getText().toString().trim();
				String repassword = mRepassword.getText().toString().trim();
				if (password.length() == 0 || password == null) {
					Toast.makeText(getApplicationContext(), "密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (repassword.equals(password)) {
					Toast.makeText(getApplicationContext(), "设置成功",
							Toast.LENGTH_SHORT).show();
					alertDialog.dismiss();
					//将密码保存到sharedpreferences
					Sharedpreferences.saveString(getApplicationContext(),
							constants.isSettingPW, MD5utils.toMD5(password));
					enterSetActivity();
					

				} else {
					Toast.makeText(getApplicationContext(), "密码设置不一致,请重新输入",
							Toast.LENGTH_SHORT).show();
				}

			}

		});
		
		button_password_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});

		builder.setView(view);
		alertDialog = builder.create();
		alertDialog.show();

	}
	private void enterSetActivity() {
		boolean b = Sharedpreferences.getBoolean(getApplicationContext(), constants.isInit, false);
		if(b){
			Intent intent=new Intent(this,FindActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.anim_enter_next_activity, R.anim.anim_exit_next_activty);
		}else{
			
			Intent intent=new Intent(this,SetFirstActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.anim_enter_next_activity, R.anim.anim_exit_next_activty);
		}
	}

	private class MyBaseAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return Icon.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view = View.inflate(getApplicationContext(),
					R.layout.gridview_home, null);

			ImageView gridview_imageview = (ImageView) view
					.findViewById(R.id.gridview_imageview);
			TextView textview_title = (TextView) view
					.findViewById(R.id.Textview_title);
			TextView textview_info = (TextView) view
					.findViewById(R.id.Textview_info);

			gridview_imageview.setImageResource(Icon[position]);
			textview_title.setText(Title[position]);
			textview_info.setText(Info[position]);

			return view;
		}

	}

	public void Setting_Activity(View v) {
		Intent intent = new Intent(this, Setting_Activity.class);
		startActivity(intent);
	}

	private void animationStart() {
		ObjectAnimator objectanimator = ObjectAnimator.ofFloat(image_logo,
				"rotationY", 0f, 180f, 360f);
		objectanimator.setDuration(4000);
		objectanimator.setRepeatCount(ObjectAnimator.INFINITE);
		objectanimator.setRepeatMode(ObjectAnimator.RESTART);
		// REVERSE代表从动画结束的地方开始下一次动画,RESTART代表从动画最初的地方开始下一次动画
		objectanimator.start();

	}

}
