package com.ybbbi.safe;

import com.ybbbi.safe.utils.Sharedpreferences;
import com.ybbbi.safe.utils.constants;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class SetFifthActivity extends myActivity {

	private CheckBox checkbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_fifth);
		init();
	}

	private void init() {
		checkbox = (CheckBox) findViewById(R.id.checkbox_fifth);

		boolean checked = Sharedpreferences.getBoolean(getApplicationContext(),
				constants.CHECKBOX, false);
		checkbox.setChecked(checked);
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Sharedpreferences.saveBoolean(SetFifthActivity.this,
						constants.CHECKBOX, isChecked);

			}
		});
	}

	@Override
	protected boolean ButtonPre() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, SetFourthActivity.class);
		startActivity(intent);
		return true;
	}

	@Override
	protected boolean ButtonNext() {
		if (checkbox.isChecked()) {

			Toast.makeText(getApplicationContext(), "设置完成", 0).show();
			Sharedpreferences.saveBoolean(getApplicationContext(),
					constants.isInit, true);
			Intent intent = new Intent(this, FindActivity.class);
			startActivity(intent);
			return true;
		}
		return true;
		/*Toast.makeText(getApplicationContext(), "请开启保护", 0).show();
		return false;*/

	}

}
