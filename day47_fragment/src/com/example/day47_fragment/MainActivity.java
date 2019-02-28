package com.example.day47_fragment;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener {

	private ImageButton ib_weixin;
	private ImageButton ib_find;
	private ImageButton ib_profile;
	private ImageButton ib_contact;
	private contact_fragment contactfragment;
	private weixin_fragment weixinFragment;
	private find_fragment findFragment;
	private me_fragment profileFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ib_weixin = (ImageButton) findViewById(R.id.ib_weixin);
		ib_find = (ImageButton) findViewById(R.id.ib_find);
		ib_profile = (ImageButton) findViewById(R.id.ib_profile);
		ib_contact = (ImageButton) findViewById(R.id.ib_contact);

		ib_contact.setOnClickListener(this);
		ib_weixin.setOnClickListener(this);
		ib_find.setOnClickListener(this);
		ib_profile.setOnClickListener(this);
		
	}
	@Override
	protected void onResume() {
		ib_weixin.performClick();
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		clearIcon();
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		switch (v.getId()) {
		
		
		case R.id.ib_weixin:
			 
			if(weixinFragment==null){
				weixinFragment = new weixin_fragment();
				
			}transaction.replace(R.id.fragment_container, weixinFragment);
			ib_weixin.setImageResource(R.drawable.weixin_pressed);
			break;
			
			
		case R.id.ib_contact:
			 
			if (contactfragment == null) {
				contactfragment = new contact_fragment();
			}transaction.replace(R.id.fragment_container, contactfragment);
			ib_contact.setImageResource(R.drawable.contact_list_pressed);
			break;
			
			
		case R.id.ib_find:
						if(findFragment==null){
				
				findFragment = new find_fragment();
			}transaction.replace(R.id.fragment_container, findFragment);
			ib_find.setImageResource(R.drawable.find_pressed);
			break;
			
			
		case R.id.ib_profile:
			 
			if(profileFragment==null){
				profileFragment = new me_fragment();
				
			}transaction.replace(R.id.fragment_container, profileFragment);
			ib_profile.setImageResource(R.drawable.profile_pressed);
			break;

		}
		transaction.commit();
	}
	private void clearIcon(){
		ib_weixin.setImageResource(R.drawable.weixin_normal);
		ib_contact.setImageResource(R.drawable.contact_list_normal);
		ib_find.setImageResource(R.drawable.find_normal);
		ib_profile.setImageResource(R.drawable.profile_normal);

		
	}

}
