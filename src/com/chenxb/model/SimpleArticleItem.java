package com.chenxb.model;

/**
 * listview 用到的简单实体类
 * 只包括 id，标题，发布日期，阅读次数
 * 没有新闻主体内容等
 * @author tomchen
 *
 */

public class SimpleArticleItem {

	private int id;
	private String[] imageUrls;
	// 图片资源不是必须的
	private String title;
	private String publishDate;
	private int readTimes;

	public SimpleArticleItem(int id, String[] imageUrls, String title, String publishDate, int readTimes) {
		this.id = id;
		this.imageUrls = imageUrls;
		this.title = title;
		this.publishDate = publishDate;
		this.readTimes = readTimes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String[] getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(String[] imageUrls) {
		this.imageUrls = imageUrls;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public int getReadTimes() {
		return readTimes;
	}

	public void setReadTimes(int readTimes) {
		this.readTimes = readTimes;
	}

	@Override
	public String toString() {
		return "SimpleArticleItem [id=" + id + ", title=" + title + ", publishDate=" + publishDate + ", readTimes="
				+ readTimes + "]";
	}
}