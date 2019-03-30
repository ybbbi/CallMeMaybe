package com.ybbbi.safe;

import com.ybbbi.safe.utils.Sharedpreferences;
import com.ybbbi.safe.utils.constants;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SetThirdActivity extends myActivity {

	private EditText edittext_3rd_number;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_third);
		init();
	}

	private void init() {
		edittext_3rd_number = (EditText) findViewById(R.id.edittext_3rd_number);
		String string = Sharedpreferences.getString(getApplicationContext(),
				constants.savenum, "");
		edittext_3rd_number.setText(string);
		button = (Button) findViewById(R.id.btn_3rd_sure);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SetThirdActivity.this,
						Contact_3rd_Activity.class);
				startActivityForResult(intent, 2);
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == 1) {
			if (data != null) {
				String num = data.getStringExtra("num");
				edittext_3rd_number.setText(num);
			}
		}
		
		super.onActivityResult(2, 1, data);
	}


	@Override
	protected boolean ButtonPre() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, SetSencondActivity.class);
		startActivity(intent);

		return true;
	}

	@Override
	protected boolean ButtonNext() {
		// TODO Auto-generated method stub
		String trim = edittext_3rd_number.getText().toString().trim();
		if (trim == null || trim.length() == 0) {
			Toast.makeText(getApplicationContext(), "请设置电话号", 0).show();
			return false;
		}
		Sharedpreferences.saveString(getApplicationContext(),
				constants.savenum, trim);

		Intent intent = new Intent(this, SetFourthActivity.class);
		startActivity(intent);
		return true;
	}

}
