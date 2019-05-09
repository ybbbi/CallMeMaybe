package com.ybbbi.pulltorefreshlistview;

import java.util.ArrayList;
import java.util.List;

import com.ybbbi.pulltorefreshlistview.view.pullListview;
import com.ybbbi.pulltorefreshlistview.view.pullListview.onRefreshlistener;

import android.os.Bundle;
import android.os.Handler;
import android.R.anim;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {
	private int i = 0;
	private int j = 0;
	private pullListview pulllistView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();

	}

	private void init() {
		pulllistView = (pullListview) findViewById(R.id.listview);

		final List<String> list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add("北京" + i + "区");

		}

		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		pulllistView.setAdapter(adapter);
		pulllistView.setonRefreshListener(new onRefreshlistener() {

			@Override
			public void Refresh() {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						i++;
						list.add(0, "北方" + i + "区");
						adapter.notifyDataSetChanged();

						pulllistView.finish();
					}
				}, 3000);
			}

			@Override
			public void load() {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						j++;
						list.add( "上海" + j + "区");
						adapter.notifyDataSetChanged();

						pulllistView.finish();
					}
				}, 3000);
			}
		});
	}

}
