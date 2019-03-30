package com.ybbbi.safe;

import com.ybbbi.safe.database.dao.BlackListDAO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Update_blacklist_Activity extends Activity {

	private String num;
	private int mode;
	private int position;
	private EditText update_et;
	private RadioGroup radiogroup;
	private BlackListDAO bd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_blacklist);

		getIntentExtra();
		init();

	}

	private void init() {
		update_et = (EditText) findViewById(R.id.Blacklist_et_update_number);
		radiogroup = (RadioGroup) findViewById(R.id.radioGroup_blacklist_update);
		bd = new BlackListDAO(this);

		int id = 3;
		update_et.setText(num);
		switch (mode) {
		case 0:
			id = R.id.radio_number_update;
			// radiogroup.check(id);
			break;
		case 1:
			id = R.id.radio_mode_update;
			// radiogroup.check(id);
			break;
		case 2:
			id = R.id.radio_all_update;
			// radiogroup.check(id);

			break;
		default:
			id = R.id.radio_all_update;
			// radiogroup.check(id);

			break;

		}
		radiogroup.check(id);
	}

	private void getIntentExtra() {
		Intent intent = getIntent();
		num = intent.getStringExtra("number");
		mode = intent.getIntExtra("mode", 3);
		position = intent.getIntExtra("position", -1);

	}

	public void cancel(View v) {
		finish();
	}

	public void sure(View v) {
		
		String updatenum = update_et.getText().toString().trim();
		
		
		// 类型为空,无法保存
		int Id = radiogroup.getCheckedRadioButtonId();
		switch (Id) {
		case R.id.radio_number_update:
			mode = 0;
			break;
		case R.id.radio_mode_update:
			mode = 1;
			break;
		case R.id.radio_all_update:
			mode = 2;
			break;
		default:
			Toast.makeText(getApplicationContext(), "请选择拦截类型", 0).show();
			return;

		}
		boolean update1 = bd.update1(updatenum, mode);
		if(update1){
			Intent data=new Intent();
			data.putExtra("mode", mode);
			data.putExtra("position", position);
			setResult(Activity.RESULT_OK, data);
			Toast.makeText(getApplicationContext(), "更新成功", 0).show();
			
			finish();
		}else{
			Toast.makeText(getApplicationContext(), "服务器繁忙,请稍后再试", 0).show();
			
		}
	}
}
