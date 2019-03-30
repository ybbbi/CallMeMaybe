package com.ybbbi.safe;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ybbbi.safe.bean.BlackListInfo;
import com.ybbbi.safe.database.dao.BlackListDAO;

public class BlackListActivity extends Activity {

	private static final int REQUESTADDCODE = 40;
	protected static final int UPDATEREQUESCODE = 41;
	protected static final int LIMIT = 30;
	protected static int OFFSET = 0;
	private ArrayList<BlackListInfo> querylist;
	private ImageView image;
	private ListView listview;
	private BlackListDAO blacklistdao;
	private myadapter adapter;
	private LinearLayout loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_black_list);
		init();
		blacklistdao = new BlackListDAO(this);
	}

	private void init() {
		loading = (LinearLayout) findViewById(R.id.linearlayout_blacklist_loading);
		image = (ImageView) findViewById(R.id.imageview_blacklist);
		listview = (ListView) findViewById(R.id.listview_blacklist);
		initListview();

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(BlackListActivity.this,
						Update_blacklist_Activity.class);

				intent.putExtra("number", querylist.get(position).number);
				intent.putExtra("mode", querylist.get(position).mode);
				intent.putExtra("position", position);// 传递索引,再更改时找到索引对应的mode
				startActivityForResult(intent, UPDATEREQUESCODE);

			}
		});
		listview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 翻到最后一个listview,状态改为空闲状态,加载界面
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					if (listview.getLastVisiblePosition() == querylist.size() - 1) {
						OFFSET += LIMIT;
						initListview();
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});

	}

	private void initListview() {
		// 开启子线程,操作数据库
		loading.setVisibility(View.VISIBLE);

		new Thread() {

			public void run() {
				
				if (querylist == null) {
					// 查询最新20条数据
					querylist = blacklistdao.query2(LIMIT, OFFSET);
				}

				else {
					querylist.addAll(blacklistdao.query2(LIMIT, OFFSET));

				}

				runOnUiThread(new Runnable() {
					public void run() {
						if (adapter == null) {

							adapter = new myadapter();
							listview.setAdapter(adapter);
						} else {
							adapter.notifyDataSetChanged();
						}
						
						
						listview.setEmptyView(image);

						loading.setVisibility(View.GONE);

					}
				});

			};
		}.start();
	}

	public void addBlacklist(View v) {
		Intent intent = new Intent(this, Add_blacklist_Activity.class);
		startActivityForResult(intent, REQUESTADDCODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// 判断从那个界面携带数据过来的
		if (requestCode == REQUESTADDCODE) {
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					String number = data.getStringExtra("number");
					int mode = data.getIntExtra("mode", 3);
					// 将携带的数据设置到listview中
					BlackListInfo bInfo = new BlackListInfo(number, mode);
					querylist.add(0, bInfo);
					adapter.notifyDataSetChanged();
				}
			}
		} else if (requestCode == UPDATEREQUESCODE) {
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					int mode = data.getIntExtra("mode", 3);
					int poistion = data.getIntExtra("position", -1);
					querylist.get(poistion).mode = mode;
					adapter.notifyDataSetChanged();// 刷新数据适配器
				}
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	// 数据适配器
	private class myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return querylist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return querylist.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			viewHolder holder;
			if (convertView == null) {
				holder = new viewHolder();
				convertView = View.inflate(getApplicationContext(),
						R.layout.blacklistinfo, null);
				holder.number = (TextView) convertView
						.findViewById(R.id.blacklist_tv_number);
				holder.mode = (TextView) convertView
						.findViewById(R.id.blacklist_tv_mode);
				holder.image = (ImageView) convertView
						.findViewById(R.id.blacklist_iv_image);
				convertView.setTag(holder);

			} else {
				holder = (viewHolder) convertView.getTag();

			}
			// 使用全局变量会造成数据刷新失败,因为他每次执行时都把他复制给了一个新的对象
			final BlackListInfo blackListInfo = querylist.get(position);

			final String mNumber = blackListInfo.number;
			holder.number.setText(mNumber);
			switch (blackListInfo.mode) {
			case 0:
				holder.mode.setText("短信拦截");
				break;
			case 1:
				holder.mode.setText("电话拦截");
				break;
			case 2:
				holder.mode.setText("全部拦截");
				break;

			}
			holder.image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new Builder(
							BlackListActivity.this);
					builder.setTitle("确认删除" + mNumber + "号码吗?");
					builder.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									boolean delete = blacklistdao
											.delete(mNumber);
									if (delete) {

										querylist.remove(blackListInfo);
										Toast.makeText(getApplicationContext(),
												"删除成功", 0).show();
										adapter.notifyDataSetChanged();
										dialog.dismiss();

									} else {

										Toast.makeText(getApplicationContext(),
												"删除失败", 0).show();
										dialog.dismiss();
									}

								}
							});
					builder.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									dialog.dismiss();
								}
							});
					builder.show();

				}
			});

			return convertView;
		}

	}

	static class viewHolder {
		TextView number, mode;
		ImageView image;
	}

}
