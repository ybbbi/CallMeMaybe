package com.ybbbi.safe;

import com.ybbbi.safe.utils.Sharedpreferences;
import com.ybbbi.safe.utils.constants;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SetSencondActivity extends myActivity {

	private RelativeLayout relative;
	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_sencond);
		init();

	}

	private void init() {
		image = (ImageView) findViewById(R.id.image_2nd);
		relative = (RelativeLayout) findViewById(R.id.relativelayout_2nd);

		String string = Sharedpreferences.getString(getApplicationContext(),
				constants.sim, "");
		if (string.length() == 0 || string == null) {
			image.setImageResource(R.drawable.unlock);
		}else{
			image.setImageResource(R.drawable.lock);
			
		}

		relative.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String sim = Sharedpreferences.getString(
						getApplicationContext(), constants.sim, "");
				if (sim.length() == 0 || sim == null) {
					TelephonyManager t = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					String number = t.getSimSerialNumber();
					Sharedpreferences.saveString(getApplicationContext(),
							constants.sim, number);
					image.setImageResource(R.drawable.lock);
				} else {
					Sharedpreferences.saveString(getApplicationContext(),
							constants.sim, "");
					image.setImageResource(R.drawable.unlock);
				}

			}
		});

	}

	@Override
	protected boolean ButtonPre() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, SetFirstActivity.class);
		startActivity(intent);
		return true;
	}

	@Override
	protected boolean ButtonNext() {
		String string = Sharedpreferences.getString(getApplicationContext(), constants.sim, "");
		if(string.length()==0||string==null){
			Toast.makeText(getApplicationContext(), "请先绑定sim卡", 0).show();
			return false;
		}
		Intent intent = new Intent(this, SetThirdActivity.class);
		startActivity(intent);
		return true;
	}

}
