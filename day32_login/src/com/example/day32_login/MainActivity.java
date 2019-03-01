package com.example.day32_login;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et_name;
	private EditText et_pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_name = (EditText) findViewById(R.id.et_name);
		et_pwd = (EditText) findViewById(R.id.et_pwd);

	}

	public void click1(View v) {
		new Thread() {
			public void run() {

				try {
					String name = et_name.getText().toString().trim();
					String pwd = et_pwd.getText().toString().trim();
					String path = "";

					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					int code = conn.getResponseCode();
					if (code == 200) {
						InputStream in = conn.getInputStream();
						String content = StreamTools.readStream(in);
						showToast(content);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();

	}

	public void click2(View v) {

		new Thread() {
			public void run() {

				try {
					String name = et_name.getText().toString().trim();
					String pwd = et_pwd.getText().toString().trim();
					String path = "";
					String data="";

					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "");
					conn.setRequestProperty("Content-Length", data.length()+"");
					conn.setDoOutput(true);//‘ –Ì ‰≥ˆ
					conn.getOutputStream().write(data.getBytes());

					int code = conn.getResponseCode();
					if (code == 200) {
						InputStream in = conn.getInputStream();
						String content = StreamTools.readStream(in);
						showToast(content);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();

	}

	public void showToast(final String content) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), content, 1).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
