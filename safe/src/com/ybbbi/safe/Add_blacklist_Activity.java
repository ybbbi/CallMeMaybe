package com.ybbbi.safe;

import java.util.ArrayList;

import com.ybbbi.safe.bean.BlackListInfo;
import com.ybbbi.safe.database.dao.BlackListDAO;

import android.R.id;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Add_blacklist_Activity extends Activity {

	private TextView addnum;
	private RadioGroup radiogroup;
	private BlackListDAO bd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_blacklist);
		init();

	}

	// 初始化控件
	private void init() {
		addnum = (TextView) findViewById(R.id.Blacklist_et_addnumber);
		radiogroup = (RadioGroup) findViewById(R.id.radioGroup_blacklist);
		bd = new BlackListDAO(this);
	}

	public void sure(View v) {
		int flag = 3;
		String num = addnum.getText().toString().trim();
		// 号码为空,无法保存
		if (num == null || num.length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入号码", 0).show();
			return;
		}
		// 类型为空,无法保存
		int Id = radiogroup.getCheckedRadioButtonId();
		switch (Id) {
		case R.id.radio_number:
			flag = 0;
			break;
		case R.id.radio_mode:
			flag = 1;
			break;
		case R.id.radio_all:
			flag = 2;
			break;
		default:
			Toast.makeText(getApplicationContext(), "请选择拦截类型", 0).show();
			return;

		}
		// 判断号码是否存在,mode初始化值为3 返回0 1 2则存在
		  
		if (bd.query(num) == 3) {

			// 将数据保存到数据库

			boolean isadd = bd.insert(num, flag);
			if(isadd){
				Toast.makeText(getApplicationContext(), "添加成功", 0).show();
				
				Intent data=new Intent();
				data.putExtra("number", num);
				data.putExtra("mode", flag);
				
				setResult(Activity.RESULT_OK, data);
				finish();
				
			}else{
				Toast.makeText(getApplicationContext(), "服务器繁忙,请稍后再试", 0).show();
				
			}
			
			
		}else{
			Toast.makeText(getApplicationContext(), "号码已存在", 0).show();
			
		}

	}

	public void cancel(View v) {
		finish();
	}

}
