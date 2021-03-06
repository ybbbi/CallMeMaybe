package com.ybbbi.safe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.ybbbi.safe.service.ForeService;
import com.ybbbi.safe.utils.Sharedpreferences;
import com.ybbbi.safe.utils.constants;
import com.ybbbi.safe.utils.packageUtil;

public class SplashActivity extends Activity {

	protected static final String TAG = null;
	
	private String CONNECTURL = "http://10.0.2.2:8080/update.json";// 手机模拟端默认为10.0.2.2
	private int code;
	private String apkUrlString;
	private String msg;
	private String url = "http://10.0.2.2:8080/safe.apk";
	private String targetAPK = "mnt/sdcard/safe.apk";
	private ProgressDialog progressdialog;

	private TextView tv_version;

	// 电脑端为127.0.1.1

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initView();
		//create();
		//copyDatabase("address.db");会进行耗时操作,开启子线程
		
		
	startService(new Intent(this,ForeService.class));
				copyDatabase(constants.CPYDBADDRESS);
				copyDatabase(constants.VIUSDB);
				copyDatabase(constants.CPYDBCOMMONNUM);
	}
	private void copyDatabase(String name) {
		AssetManager assets = getAssets();
		File file=new File(getFilesDir(),name);
		if(!file.exists()){
			
			InputStream open = null;
			FileOutputStream fos = null;
			try {
				open = assets.open(name);
				fos = new FileOutputStream(file);
				byte[] arr = new byte[1024];
				int len = -1;
				while ((len = open.read(arr)) != -1) {
					fos.write(arr, 0, len);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (open != null) {
					try {
						open.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}
	}

	private void initView() {
		tv_version = (TextView) findViewById(R.id.tv_version);
		/*PackageManager pm = this.getPackageManager();
		
		try {
			PackageInfo packageInfo = pm.getPackageInfo("com.ybbbi.safe", 0);
			System.out.println(packageInfo.versionName+"失败");
			tv_version.setText("版本:" + packageInfo.versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		tv_version.setText("版本:"+packageUtil.getVersionName(this));
		
		
		
		
		//延时更新操作
		new Handler(){
			@Override
			public void handleMessage(Message msg) {
				boolean b = Sharedpreferences.getBoolean(getApplicationContext(), constants.isUpdate, true);
				if(b){
					update();
				}else
				{
					Home();
				}
			}
		}.sendEmptyMessageDelayed(0, 2000);
		//update();
	}

	private void update() {
		HttpUtils httputils = new HttpUtils(2000);
		httputils.send(HttpMethod.GET, CONNECTURL, null,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// 直接跳转界面到主页
						Home();
						System.out.println("失败");

					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;// 获取服务器返回数据
						
						version_fromService(result);

					}

					private void version_fromService(String result) {
						try {
							JSONObject jsonobject = new JSONObject(result);
							code = jsonobject.getInt("code");
							apkUrlString = jsonobject.getString("apkurl");
							msg = jsonobject.getString("msg");
							System.out.println(code);

							// 判断是否与tomcat服务器版本一致
							if (code == packageUtil
									.getVersionCode(getApplicationContext())) {
								// 开启首页
								Home();

							}

							// 不一致时弹出更新Dialog
							else {
								// 更新版本的对话框
								showDialog();

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				});

	}

	public void Home() {
		Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
		startActivity(intent);
		finish();
	}

	private void showDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("新版本:" + code + ".0");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage(msg);
		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				Home();
			}
		});

		builder.setPositiveButton("前往更新",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 前往网页跳转下载apk,保存到sdk目录
						Download();
						//弹出进度条显示下载进度
						showDownloadDialog();
					}

				

				});
		builder.setNegativeButton("忽略更新",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// stub

						dialog.dismiss();

						Home();
					}

				});

		builder.show();
	}
	
	private void Download() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			HttpUtils httputils = new HttpUtils();

			httputils.download(url, targetAPK, new RequestCallBack<File>() {

				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					progressdialog.dismiss();
					InstallNewApp();
				}

				

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					progressdialog.dismiss();
					Home();
				}
				
				//下载进度的完成度
				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					progressdialog.setMax((int)total);
					progressdialog.setProgress((int)current);
					// TODO Auto-generated method stub
					super.onLoading(total, current, isUploading);
				}

			});
		}else{
			Toast.makeText(getApplicationContext(), "sdcard未插入,请检查后再试", Toast.LENGTH_SHORT).show();
		}
	}
	private void showDownloadDialog() {
		progressdialog = new ProgressDialog(this);
		//设置进度条样式
		progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		//进度条是否消失
		progressdialog.setCancelable(false);
		progressdialog.show();
	}
	private void InstallNewApp() {
		// TODO Auto-generated method stub
/*
 *          <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:scheme="content" />
                <data android:scheme="file" />
                <data android:mimeType="application/vnd.android.package-archive" />
            </intent-filter>
 * */
		//唤醒其他界面用隐示意图跳转,intent-filter
		Intent intent =new Intent();
		intent.setAction("android.intent.action.VIEW");
		//intent.setData(Uri.fromFile(new File(targetAPK)));  data 与 type 会相互覆盖所以用 intent.setDataAndType
		intent.setDataAndType(Uri.fromFile(new File(targetAPK)), "application/vnd.android.package-archive");
		startActivityForResult(intent, 1);
		//重写onactivityresult 方法  ,取消安装跳转主页
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Home();
		super.onActivityResult(requestCode, 1, data);
		
		
	}
//	public void create(){
//		
//		Intent intent=new Intent();
//		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "safe");
//		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.logo2));
//		Intent intent2=new Intent(this,SplashActivity.class);
//		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,intent2);
//		sendBroadcast(intent);
//		
//	}
	
	
}
