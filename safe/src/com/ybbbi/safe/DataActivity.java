package com.ybbbi.safe;

import java.util.List;

import com.ybbbi.safe.bean.Appinfo;
import com.ybbbi.safe.manager.AppManager;

import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.format.Formatter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DataActivity extends Activity {

	private ListView listview;
	private List<Appinfo> appinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data);
		init();
	}

	private void init() {
		listview = (ListView) findViewById(R.id.lv_data);
		new myAsyncTask().execute();
	}

	private class myAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			appinfo = AppManager.Appinfo(DataActivity.this);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			
			listview.setAdapter(new myAdapter());
			super.onPostExecute(result);
		}

	}

	private class myAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return appinfo.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return appinfo.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder holder;

			if (convertView == null) {
				holder = new Holder();
				convertView = View.inflate(getApplicationContext(),
						R.layout.data_listview, null);
				holder.dataName = (TextView) convertView
						.findViewById(R.id.tv_dataname);
				holder.push = (TextView) convertView
						.findViewById(R.id.tv_datapush);
				holder.pull = (TextView) convertView
						.findViewById(R.id.tv_datapull);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.image_dataicon);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.dataName.setText(appinfo.get(position).label);

			long Txbytes = TrafficStats
					.getUidTxBytes(appinfo.get(position).uid);
			holder.push.setText("上传:"
					+ Formatter
							.formatFileSize(getApplicationContext(), Txbytes));
			long Rxbytes = TrafficStats
					.getUidRxBytes(appinfo.get(position).uid);

			holder.pull.setText("下载:"
					+ Formatter
							.formatFileSize(getApplicationContext(), Rxbytes));
			holder.icon.setImageDrawable(appinfo.get(position).icon);

			return convertView;
		}

	}

	static class Holder {
		private TextView dataName, pull, push;
		private ImageView icon;
	}

}
