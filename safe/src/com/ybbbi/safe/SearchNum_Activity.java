package com.ybbbi.safe;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ybbbi.safe.database.dao.addressDB_DAO;

public class SearchNum_Activity extends Activity {

	private TextView address;
	private EditText num;
	private Button search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_num);

		init();

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String number = num.getText().toString().trim();
				if (!(num.length()==0||num==null)) {

					String address2 = addressDB_DAO.address(
							SearchNum_Activity.this, number);
					address.setText("归属地:" + address2);
				} else {
					Animation shake = AnimationUtils.loadAnimation(
							SearchNum_Activity.this, R.anim.shake);
					num.startAnimation(shake);
					Vibrator vb=(Vibrator) getSystemService(VIBRATOR_SERVICE);
					vb.vibrate(Long.MAX_VALUE);
					
					
					Toast.makeText(getApplicationContext(), "号码不能为空", 0).show();
				}

			}
		});

	}

	private void init() {
		address = (TextView) findViewById(R.id.search_numAddress);
		num = (EditText) findViewById(R.id.search_numEditText);
		search = (Button) findViewById(R.id.Btn_serchnum);
		// 实时查询数据库
		num.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String num = s.toString();
				String address2 = addressDB_DAO.address(
						SearchNum_Activity.this, num);
				if (address2.length() != 0 || address2 != null) {
					address.setText("归属地:" + address2);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

}
