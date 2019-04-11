package com.ybbbi.safe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.ybbbi.safe.database.dao.addressDB_DAO;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetManager;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchNum_Activity extends Activity {

	private TextView address;
	private EditText num;
	private Button search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_num);

		init();
		
		if (num != null || num.length() != 0) {

			search.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 查询数据库需要开启子线程
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							String number = num.getText().toString().trim();
							
							String address2 = addressDB_DAO.address(
									SearchNum_Activity.this, number);
							address.setText("归属地:" + address2);
						}
					});
				}
			});
		} else {
			Toast.makeText(this, "号码不能为空", 0).show();
		}
	}

	private void init() {
		address = (TextView) findViewById(R.id.search_numAddress);
		num = (EditText) findViewById(R.id.search_numEditText);
		search = (Button) findViewById(R.id.Btn_serchnum);

	}

}
