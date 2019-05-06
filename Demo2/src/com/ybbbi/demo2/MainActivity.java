package com.ybbbi.demo2;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

    private EditText number;
	private ImageView iv;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

	private void init() {
		number = (EditText) findViewById(R.id.number);
		iv = (ImageView) findViewById(R.id.iv);
		iv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.iv:
			View convertView=createView();
			popupWindow = new PopupWindow(convertView,number.getWidth()-4,400,true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			
			popupWindow.showAsDropDown(number,2,-5);
			break;
		}
	}

	private View createView() {
		listview = (ListView) View.inflate(getApplicationContext(), R.layout.listview, null);
		for(int i=0;i<20;i++){
			list.add("1000"+i);
		}
		listview.setAdapter(new myadapter());
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				popupWindow.dismiss();
				String text = (String) listview.getItemAtPosition(arg2);
				number.setText(text);
			}
		});
		return listview;
	}
	private List<String> list =new ArrayList<String>();
	private PopupWindow popupWindow;
	private ListView listview;
	
	private class myadapter extends BaseAdapter{
		
		
		
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			holder h;
			if(convertView==null){
				h=new holder();
				convertView = View.inflate(getApplicationContext(), R.layout.applock_listview, null);
				h.img_icon=(ImageView) convertView.findViewById(R.id.image_applock_icon);
				 h.tv2=(TextView) convertView.findViewById(R.id.tv_name_applock);
				  h.img_delete=(ImageView) convertView.findViewById(R.id.image_lock);
				  convertView.setTag(h);
			}else{
				h=(holder) convertView.getTag();
			}
			h.tv2.setText(list.get(position));
			
			h.img_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					list.remove(position);
					notifyDataSetChanged();
				}
			});
			return convertView;
		}
		
	}
	static class holder{
		private ImageView img_icon,img_delete;
		private TextView tv2;
		
	}


 
    
}
