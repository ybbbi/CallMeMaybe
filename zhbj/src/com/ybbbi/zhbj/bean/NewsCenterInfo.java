package com.ybbbi.zhbj.bean;

import java.util.List;

public class NewsCenterInfo {
	public List<NewsChildInfo> data;
	public List<String> extend;
	public String retcode;
	public class NewsChildInfo{
		public List<ChildInfo> children;
		public String id;
		public String title;
		public String type;
		public String url;
		public String url1;
		public String dayurl;
		public String excurl;
		public String weekurl;
		
	}
	public class ChildInfo{
		public String id;
		public String title;
		public String type;
		public String url;
	}
}
