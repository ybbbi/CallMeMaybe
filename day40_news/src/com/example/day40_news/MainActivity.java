package com.example.day40_news;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import com.loopj.android.image.SmartImageView;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.graphics.Color;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private int GET_DATA=0;
    private ListView lv_list;
    private String path="http://192.168.31.64:8080/news.xml";
    private ArrayList<NewsItem> newslist=new ArrayList<NewsItem>();
	private myAdapter adapter;
	private Handler handler =new Handler(){
		public void handleMessage(android.os.Message msg) {
			lv_list.setAdapter(adapter);
		};
	};

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_list = (ListView) findViewById(R.id.lv_list);
        initData();
        adapter = new myAdapter();
    }


    private void initData() {
		new Thread(){

			public void run() {
				try {
					URL url=new URL(path);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(5000);
					int code = connection.getResponseCode();
					NewsItem newsitem=null;
					if(code==200){
						InputStream inputStream = connection.getInputStream();
						XmlPullParser parser = Xml.newPullParser();
						parser.setInput(inputStream, "utf-8");
						int type = parser.getEventType();
						
						while(type!=XmlPullParser.END_DOCUMENT){
							switch (type) {
							case XmlPullParser.START_TAG:
								if("item".equals(parser.getName())){
									newsitem=new NewsItem();
								}else if("title".equals(parser.getName())){
									newsitem.title=parser.nextText();
								}else if("description".equals(parser.getName())){
									newsitem.description=parser.nextText();
								}else if("image".equals(parser.getName())){
									newsitem.image=parser.nextText();
								}else if("type".equals(parser.getName())){
									newsitem.type=parser.nextText();
								}else if("comment".equals(parser.getName())){
									newsitem.comment=parser.nextText();
								}
								break;

							case XmlPullParser.END_TAG:
								if("item".equals(parser.getName())){
									newslist.add(newsitem);
								}
								break;
							}
							type = parser.next();
						}
						for (NewsItem item : newslist) {
							System.out.println(item);
						}
						
						handler.sendEmptyMessage(GET_DATA);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
		
	}
    private class myAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			
			return newslist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return newslist.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view =null;
			if(convertView ==null){
				view = View.inflate(MainActivity.this, R.layout.item, null);
			}else{
				view=convertView;
			}
			TextView tv_title=(TextView) view.findViewById(R.id.tv_title);
			TextView tv_content=(TextView) view.findViewById(R.id.tv_content);
			TextView tv_comment=(TextView) view.findViewById(R.id.tv_comment);
			SmartImageView iv_icon=(SmartImageView) view.findViewById(R.id.iv_icon);
			NewsItem newsitem=newslist.get(position);
			tv_title.setText(newsitem.title);
			tv_content.setText(newsitem.description);
			iv_icon.setImageUrl(newsitem.image);
			if("1".equals(newsitem.type)){
				tv_comment.setText(newsitem.comment);
				tv_comment.setTextColor(Color.BLACK);
			}else if("2".equals(newsitem.type)){
				tv_comment.setText("¶À¼Ò");
				tv_comment.setTextColor(Color.RED);
				
			}else if("3".equals(newsitem.type)){
				tv_comment.setText("×¨Ìâ");
				tv_comment.setTextColor(Color.BLUE);
				
			}
			return view;
		}
    	
    }


	
    
}
