package com.ybbbi.safe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.ybbbi.safe.bean.commNumFatherInfo;
import com.ybbbi.safe.database.dao.commNumDAO;
import com.ybbbi.safe.utils.constants;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CommomNum_searchActivity extends Activity {

	private int position;
	private InputStream open;
	private FileOutputStream fos;
	private ExpandableListView listview;
	private ArrayList<commNumFatherInfo> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commom_num_search);
		init();

	}

	private void init() {
		listview = (ExpandableListView) findViewById(R.id.expandListview_commNum);
		list = commNumDAO.getFather(this);
		System.out.println(list);
		myadapter adapter = new myadapter();
		listview.setAdapter(adapter);
		listview.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// 保存成员变量状态,当再次点击时判断是否相等,不等则关闭
				if (position != groupPosition) {
					listview.collapseGroup(position);
				}
				if (listview.isGroupExpanded(groupPosition)) {// 关闭
					listview.collapseGroup(groupPosition);
					position = -1;
				} else {
					listview.expandGroup(groupPosition);// 展开
					position = groupPosition;
				}
				return true;

			}
		});
		listview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:"
						+ list.get(groupPosition).list.get(childPosition).number));
				startActivity(intent);
				return true;
			}
		});
	}

	private class myadapter extends BaseExpandableListAdapter {

		@Override
		public int getGroupCount() {

			return list.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {

			return list.get(groupPosition).list.size();
		}

		@Override
		public Object getGroup(int groupPosition) {

			return list.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {

			return list.get(groupPosition).list.get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {

			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View v = View.inflate(getApplicationContext(),
					R.layout.commonnum_search_group, null);
			TextView tv = (TextView) v.findViewById(R.id.tv_group);
			tv.setText(list.get(groupPosition).name);
			tv.setTextSize(20);
			tv.setGravity(RelativeLayout.CENTER_VERTICAL);
			return v;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			View v = View.inflate(getApplicationContext(),
					R.layout.commonnum_search_group, null);
			TextView tv = (TextView) v.findViewById(R.id.tv_group);
			tv.setText(list.get(groupPosition).list.get(childPosition).name
					+ "\n"
					+ list.get(groupPosition).list.get(childPosition).number);
			tv.setBackgroundResource(R.drawable.commonnum_bkg);
			tv.setTextColor(Color.GRAY);
			return v;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	}

}
