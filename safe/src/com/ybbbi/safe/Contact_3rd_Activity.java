package com.ybbbi.safe;

import java.util.ArrayList;

import com.ybbbi.safe.bean.Contact;
import com.ybbbi.safe.utils.contact;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Contact_3rd_Activity extends Activity {

	private ListView listview;
	private ArrayList<Contact> list_contact;
	private ProgressBar pb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_3rd);
		init();

	}

	private void init() {
		listview = (ListView) findViewById(R.id.listview_contact);
		pb = (ProgressBar) findViewById(R.id.progressbar_contactlist);
		initData();
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent=new Intent();
				intent.putExtra("num", list_contact.get(position).num);
				
				setResult(1, intent);
				finish();
			}
		});
		
	}

	private void initData() {
		pb.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {

				list_contact = contact.getcontact(Contact_3rd_Activity.this);
				// 数据查询完成,在完成加载数据
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						listview.setAdapter(new myada());
						pb.setVisibility(View.GONE);
					}
				});
			};
		}.start();

	}

	private class myada extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_contact.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list_contact.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			findview findview;
			if (convertView == null) {

				view = View.inflate(getApplicationContext(),
						R.layout.contact_listview, null);
				findview = new findview();
				findview.name = (TextView) view.findViewById(R.id.tv_name);
				findview.num = (TextView) view.findViewById(R.id.tv_num);
				findview.image = (ImageView) view
						.findViewById(R.id.image_contact);

				view.setTag(findview);

			} else {
				view = convertView;
				findview = (com.ybbbi.safe.Contact_3rd_Activity.findview) view
						.getTag();

			}

			// 使用复用缓存findid

			/*
			 * ImageView image = (ImageView)
			 * view.findViewById(R.id.image_contact); TextView name = (TextView)
			 * view.findViewById(R.id.tv_name); TextView num = (TextView)
			 * view.findViewById(R.id.tv_num);
			 */

			findview.num.setText(list_contact.get(position).num);
			findview.name.setText(list_contact.get(position).name);
			Bitmap bitmap = contact.getimage(getApplicationContext(),
					list_contact.get(position).id);
			if (bitmap != null) {
				findview.image.setImageBitmap(bitmap);
			} else {
				findview.image.setImageResource(R.drawable.ic_launcher);
			}

			return view;
		}

	}

	static class findview {
		public TextView name;
		public TextView num;
		public ImageView image;
	}

}
