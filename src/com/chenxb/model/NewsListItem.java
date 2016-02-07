package com.chenxb.model;


import java.io.Serializable;

public class NewsListItem implements Serializable{
	/**
	 * 是新闻列表上的每个元素的属性
	 */
	private static final long serialVersionUID = 1307062502603255282L;
	/**
	 * 
	 */
	private String url;
	private String date;
	private String title;
	private String click;
	
	
	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public NewsListItem(String url, String date, String title, String click) {
		this.url = url;
		this.date = date;
		this.title = title;
		this.click = click;
	}
	
	@Override
	public String toString() {
		return "NewsListItem [url=" + url + ", title=" + title + ", date="
				+ date + "]";
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	

}