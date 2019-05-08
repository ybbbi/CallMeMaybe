package com.ybbbi.pulltorefreshlistview;

import java.util.ArrayList;
import java.util.List;

import com.ybbbi.pulltorefreshlistview.view.pullListview;

import android.os.Bundle;
import android.R.anim;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {

	private pullListview pulllistView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();

	}

	private void init() {
		pulllistView = (pullListview) findViewById(R.id.listview);
		
		List<String > list=new ArrayList<String>();
		for(int i=0 ;i<30;i++){
			list.add("北京"+i+"区");
			
		}
		
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
		pulllistView.setAdapter(adapter);
	}

}
