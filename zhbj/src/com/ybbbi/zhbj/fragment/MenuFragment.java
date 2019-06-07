package com.ybbbi.zhbj.fragment;

import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ybbbi.zhbj.HomeActivity;
import com.ybbbi.zhbj.R;



public class MenuFragment extends BaseFragment {
	
	private int CurrentPos;
	private List<String> list;
	private ListView listview;
	private Myadapter myadapter;

	@Override
	public View initView() {
	
		view = View.inflate(activity, R.layout.menufragment, null);
		return view;
	}
	public void initList(List<String> list){
		CurrentPos=0;
		this.list=list;
		if(myadapter==null){
			myadapter = new Myadapter();
			listview.setAdapter(myadapter);
			
		}else{
			myadapter.notifyDataSetChanged();
		}
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CurrentPos=position;
				myadapter.notifyDataSetChanged();
				slidingMenu.toggle();
				((HomeActivity)activity).getHomeFragment().getNewsPager().switchPage(position);
			}
		});
	}
	private class Myadapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = View.inflate(activity, R.layout.menu_listview, null);
			
			TextView tv = (TextView) v.findViewById(R.id.item_iv_tv);
			ImageView img = (ImageView) v.findViewById(R.id.item_iv_arrow);
			tv.setText(list.get(position));
			if(CurrentPos==position){
				img.setImageResource(R.drawable.menu_arr_select);
				tv.setTextColor(Color.RED);
			}else{
				img.setImageResource(R.drawable.menu_arr_normal);
				tv.setTextColor(Color.WHITE);
			}
			return v;
		}
		
	}
	
	@Override
	public void initData() {
		
		listview = (ListView) view.findViewById(R.id.menu_listview);
		
		
	}

}
