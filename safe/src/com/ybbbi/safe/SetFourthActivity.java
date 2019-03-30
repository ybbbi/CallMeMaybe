package com.ybbbi.safe;

import com.ybbbi.safe.broad.deviceAdminReceiver;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SetFourthActivity extends myActivity {

	private ComponentName cp;
	private DevicePolicyManager dpm;
	private RelativeLayout relative;
	private ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_fourth);
		init();
		
	}

	private void init() {
		relative = (RelativeLayout) findViewById(R.id.relative_4th);
		img = (ImageView) findViewById(R.id.img_4th);
		 cp = new ComponentName(this,deviceAdminReceiver.class);
		 dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		relative.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(dpm.isAdminActive(cp)){
					dpm.removeActiveAdmin(cp);
					dpm.resetPassword("", 0);
					img.setImageResource(R.drawable.admin_inactivated);
					
					
				}else{
				
					Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
					intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,cp );
					intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
							"安全卫士safe");
					startActivityForResult(intent, 3);
				}

			}
		});
		
		
		if(dpm.isAdminActive(cp)){
			img.setImageResource(R.drawable.admin_activated);
		}else{
			img.setImageResource(R.drawable.admin_inactivated);
			
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(dpm.isAdminActive(cp)){
			img.setImageResource(R.drawable.admin_activated);
		}else{
			img.setImageResource(R.drawable.admin_inactivated);
			
		}
		super.onActivityResult(requestCode, resultCode, data);
		
		
		
	}

	@Override
	protected boolean ButtonPre() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,SetThirdActivity.class);
		startActivity(intent);
		return true;
	}

	@Override
	protected boolean ButtonNext() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,SetFifthActivity.class);
		startActivity(intent);
		return true;
	}

}
