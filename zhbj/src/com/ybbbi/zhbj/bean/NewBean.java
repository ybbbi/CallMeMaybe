package com.ybbbi.zhbj.bean;

import java.util.List;

public class NewBean {
	public String retcode;
	public NewBeanData data;
	public class NewBeanData {

		public String countcommenturl;
		public String more;
		public List<News> news;
		public String title;
		public List<toppic> topic;
		public List<topnews> topnews;

	}


	public class News {
		public boolean comment;
		public String commentlist;
		public String commenturl;
		public String id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
		public boolean isRead;

	}

	public class toppic {
		public String description;
		public int id;
		public String listimage;
		public String sort;
		public String title;
		public String url;

	}

	public class topnews {
		public boolean comment;
		public String commentlist;
		public String commenturl;
		public int id;
		public String pubdate;
		public String title;
		public String topimage;
		public String news;
		public String url;

	}
}
