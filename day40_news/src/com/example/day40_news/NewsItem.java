package com.example.day40_news;

public class NewsItem {

	public String title;
	public String image;
	public String type;
	public String comment;
	public String description;
	@Override
	public String toString() {
		return "NewsItem [title=" + title + ", image=" + image + ", type="
				+ type + ", comment=" + comment + ", description="
				+ description + "]";
	}

	
}
