package com.ybbbi.safe.dialog;

import com.ybbbi.safe.R;
import com.ybbbi.safe.utils.Sharedpreferences;
import com.ybbbi.safe.utils.constants;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class myDialog extends Dialog {
	
	private String[] titles = new String[] { "红色", "正常","蓝色", "绿色", "灰色", "白色", "橙色" };
	private int[] icon = new int[] { R.drawable.address_toast_red,
			R.drawable.address_toast_normal, R.drawable.address_toast_blue,
			R.drawable.address_toast_green, R.drawable.address_toast_gray,
			R.drawable.address_toast_white, R.drawable.address_toast_orange };
	private ListView listview;

	public myDialog(Context context) {
		super(context, R.style.AddressStyle);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_address_theme);

		Window window = getWindow();
		LayoutParams params = window.getAttributes();
		params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		window.setAttributes(params);
		listview = (ListView) findViewById(R.id.address_theme_dialog_lv);
		myadapter l = new myadapter();
		listview.setAdapter(l);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				dismiss();
				Sharedpreferences.saveInt(getContext(), constants.THEMEBKG, icon[position]);
				
				
			}
		});
	}

	private class myadapter extends BaseAdapter {


		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return titles[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
				holder h;
			if (convertView == null) {
				h = new holder();
				convertView = View.inflate(getContext(),
						R.layout.address_theme_listview, null);
				h.tv=(TextView) convertView.findViewById(R.id.theme_dialog_tv);
				h.imageicon=(ImageView) convertView.findViewById(R.id.theme_dialog_iv);
				h.select=(ImageView) convertView.findViewById(R.id.theme_dialog_iv_select);
				convertView.setTag(h);
			}else{
				
				h=(holder) convertView.getTag();
			
			}
			
			h.tv.setText(titles[position]);
			h.imageicon.setImageResource(icon[position]);
			int int1 = Sharedpreferences.getInt(getContext(), constants.THEMEBKG, R.drawable.address_toast_normal);
		//设置哪个被选择,哪个显示对勾
				if(icon[position]==int1){
					h.select.setVisibility(View.VISIBLE);
				}else{
					h.select.setVisibility(View.GONE);				}
			

			return convertView;
		}

	}

	static class holder{
		TextView tv;
		ImageView imageicon,select;
	}
}
