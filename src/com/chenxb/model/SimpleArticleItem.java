package com.chenxb.model;

/**
 * model/SimpleArticleItem.java 
 * 栏目显示新闻列表
 * 所需要的实体类 只包括 id 标题 图片 发布时间 阅读次数
 * 
 * @author tomchen
 *
 */

public class SimpleArticleItem {

	private int id;
	// 图片资源不是必须的
	private String[] imageUrls;
	private String title;
	private String publishDate;
	private int readTimes;

	public SimpleArticleItem(int id, String title, String publishDate, int readTimes) {
		this.id = id;
		this.title = title;
		this.publishDate = publishDate;
		this.readTimes = readTimes;
	}

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

}